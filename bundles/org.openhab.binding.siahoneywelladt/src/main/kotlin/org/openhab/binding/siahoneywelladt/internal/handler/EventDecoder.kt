package org.openhab.binding.siahoneywelladt.internal.handler

import org.openhab.binding.siahoneywelladt.internal.handler.Constants.EVENT_DECODER_SIZE
import org.openhab.binding.siahoneywelladt.internal.model.PseudoSiaWaitForMoreEvent
import org.openhab.binding.siahoneywelladt.internal.model.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.model.SiaBlockEventDecoder
import org.openhab.binding.siahoneywelladt.internal.model.SiaBlockHeader
import org.openhab.binding.siahoneywelladt.internal.model.SiaConfigurationEvent
import org.openhab.binding.siahoneywelladt.internal.model.SiaEvent
import org.openhab.binding.siahoneywelladt.internal.model.SiaEventType
import org.openhab.binding.siahoneywelladt.internal.model.SiaFunction
import org.openhab.binding.siahoneywelladt.internal.model.SiaMetaData
import org.openhab.binding.siahoneywelladt.internal.model.SiaRegularEvent
import org.openhab.binding.siahoneywelladt.internal.model.UnhandledSiaEvent
import org.openhab.binding.siahoneywelladt.internal.model.command.SiaStateRequestType
import org.openhab.binding.siahoneywelladt.internal.model.command.comms.AcknowledgeCommand
import org.openhab.binding.siahoneywelladt.internal.model.command.comms.RejectCommand
import org.openhab.binding.siahoneywelladt.internal.support.LoggerDelegate

class RegularSiaEventDetails {
    var rawMessage: ByteArray? = null
    var eventType: SiaEventType? = null
    var metaData: SiaMetaData? = null
    var accountId: String? = null
}

class EventDecoder(
    private val eventListener: SiaEventListener,
    private val commandTransmitter: SiaCommandTransmitter,
    private val configurationEventDecoder: ConfigurationEventDecoder,
    private val regularEventDecoder: RegularEventDecoder
) {
    enum class State {
        AWAITING_NEW_EVENT,
        HANDLE_CONFIGURATION_EVENT,
        HANDLE_REGULAR_EVENT
    }

    private val logger by LoggerDelegate()
    private val buffer = ByteArray(EVENT_DECODER_SIZE)
    private var size = 0
    private var siaLevel = 2
    private var state = State.AWAITING_NEW_EVENT
    private var regularSiaEventDetails = RegularSiaEventDetails()

    private fun decodeFunctionsAndReturnLastCorrectEndIndex() {
        var indexToFunction = findFirstControlCommand()
        while (indexToFunction != null) {
            val (index, function) = indexToFunction
            try {
                decodeFunction(index, function)?.let {
                    handleEvent(it)
                    shiftBytes(index, it.size)
                }
            } catch (e: IllegalSiaCommandException) {
                logger.error(e.message, e)
                if (function.needsAcknowledge)
                    commandTransmitter.transmit(RejectCommand.instance)
                shiftBytes(index, e.moveByteCount)
            }
            indexToFunction = findFirstControlCommand()
        }
    }

    private fun shiftBytes(index: Int, count: Int) {
        val startIndex = index + count
        buffer.copyInto(buffer, 0, startIndex, size)
        size -= startIndex
    }

    private fun handleEvent(event: SiaEvent) {
        eventListener.handleEvent(event)
        if (event.needsAcknowledge)
            commandTransmitter.transmit(AcknowledgeCommand.instance)
    }

    private fun decodeFunction(index: Int, function: SiaFunction): SiaEvent? {
        val siaBlock = getSiaBlock(index, function) ?: return null

        return when (state) {
            State.AWAITING_NEW_EVENT -> handleAwaitEvent(siaBlock)
            State.HANDLE_REGULAR_EVENT -> handleRegularEvent(siaBlock)
            State.HANDLE_CONFIGURATION_EVENT -> handleConfigurationEvent(siaBlock)
        }
    }

    private fun handleRegularEvent(siaBlock: SiaBlock): SiaEvent? {
        val event = regularEventDecoder.decode(siaBlock, siaLevel)
        if (siaBlock.function != SiaFunction.ASCII) {
            if (event == null) return null
            this.regularSiaEventDetails.metaData = event.metaData
            this.regularSiaEventDetails.eventType = event.eventType
            this.regularSiaEventDetails.rawMessage = event.message
            return PseudoSiaWaitForMoreEvent(
                siaBlock.message,
                siaBlock.function.needsAcknowledge,
                siaBlock.getTotalLength()
            )
        }
        val result = SiaRegularEvent(
            regularSiaEventDetails.eventType!!,
            regularSiaEventDetails.metaData!!,
            regularSiaEventDetails.rawMessage!!,
            siaBlock.function.needsAcknowledge,
            siaBlock.getTotalLength(),
            siaBlock.messageAsString
        )
        resetState()
        return result
    }

    private fun handleAwaitEvent(siaBlock: SiaBlock): SiaEvent? {
        return when (siaBlock.function) {
            SiaFunction.ACCOUNT_ID -> {
                this.regularSiaEventDetails.accountId = siaBlock.messageAsString
                    .substring(2)
                this.state = State.HANDLE_REGULAR_EVENT
                PseudoSiaWaitForMoreEvent(
                    siaBlock.message,
                    siaBlock.function.needsAcknowledge,
                    siaBlock.getTotalLength()
                )
            }
            SiaFunction.CONFIGURATION -> {
                this.siaLevel = getSiaLevelFromConfigurationBlock(siaBlock)
                this.state = State.HANDLE_CONFIGURATION_EVENT
                PseudoSiaWaitForMoreEvent(
                    siaBlock.message,
                    siaBlock.function.needsAcknowledge,
                    siaBlock.getTotalLength()
                )
            }
            else -> throw IllegalArgumentException("Unsupported SIA function ${siaBlock.function} for message ${siaBlock.messageAsString}")
        }
    }

    private fun handleConfigurationEvent(siaBlock: SiaBlock): SiaEvent? {
        this.state = State.HANDLE_CONFIGURATION_EVENT
        val event = configurationEventDecoder.decode(siaBlock, siaLevel)
        if (event != null) {
            resetState()
        } else {
            logger.info("Unhandled configuration message: ${siaBlock.messageAsString}")
            return UnhandledSiaEvent(siaBlock.message, siaBlock.function.needsAcknowledge, siaBlock.getTotalLength())
        }

        return event
    }

    private fun resetState() {
        this.state = State.AWAITING_NEW_EVENT
        this.regularSiaEventDetails = RegularSiaEventDetails()
    }

    private fun getSiaLevelFromConfigurationBlock(siaBlock: SiaBlock) =
        siaBlock.message[2] - '0'.toByte()

    private fun getSiaBlock(index: Int, function: SiaFunction): SiaBlock? {
        val header = SiaBlockHeader.fromHeaderByte(buffer[index])
        return if (notEnoughBytesInBuffer(index, header))
            null
        else createSiaBlock(header, function, index)
    }

    private fun createSiaBlock(
        header: SiaBlockHeader,
        function: SiaFunction,
        index: Int
    ): SiaBlock {
        val messageSize = header.messageSize
        val siaBlock = SiaBlock(function, header, extractMessage(index, messageSize))
        val inputChecksum = buffer[index + messageSize + 2]
        if (siaBlock.checksum() != inputChecksum) {
            throw IllegalSiaCommandException(
                "Checksum of SiaBlock ${inputChecksum.toInt() and 0xFF} not equal " +
                        "to calculated checksum ${siaBlock.checksum()
                            .toInt() and 0xFF} for sia block $siaBlock", 2
            )
        }
        return siaBlock
    }

    private fun extractMessage(index: Int, messageSize: Int): ByteArray {
        val message = ByteArray(messageSize)
        val messageStartIndex = index + 2
        val messageEndIndex = messageStartIndex + messageSize
        buffer.copyInto(message, 0, messageStartIndex, messageEndIndex)
        return message
    }

    private fun notEnoughBytesInBuffer(
        index: Int,
        header: SiaBlockHeader
    ) = index + header.messageSize + 1 > buffer.size

    private fun findFirstControlCommand(): Pair<Int, SiaFunction>? {
        var function: SiaFunction? = null
        val index = buffer.drop(1)
            .indexOfFirst {
                function = SiaFunction.getFunction(it.toInt())
                function != null
            }

        return if (function != null && index < size)
            Pair(index, function!!)
        else null
    }

    fun decode(newBytes: ByteArray, bytesRead: Int) {
        if (bytesRead == 0)
            return
        if (size + bytesRead > buffer.size) {
            logger.error("Buffer overrun, dropping old buffer of size $size.")
            size = 0
        }
        newBytes.copyInto(buffer, size, 0, bytesRead)
        size += newBytes.size

        decodeFunctionsAndReturnLastCorrectEndIndex()
    }

}

class RegularEventDecoder {
    private val logger by LoggerDelegate()
    fun decode(siaBlock: SiaBlock, siaLevel: Int): SiaRegularEvent? {
        val eventInfo = SiaBlockEventDecoder.instance.getSiaEventCodeAndType(siaBlock) ?: return null
        if (eventInfo.second == null)
            throw IllegalArgumentException("Unknown event code: ${eventInfo.first} for $siaBlock")
        return SiaRegularEvent(
            eventInfo.second!!,
            SiaMetaData.fromMessage(siaBlock.messageAsString),
            siaBlock.message,
            siaBlock.function.needsAcknowledge,
            siaBlock.getTotalLength()
        )
    }
}

class ConfigurationEventDecoder {
    private val logger by LoggerDelegate()
    fun decode(siaBlock: SiaBlock, siaLevel: Int): SiaConfigurationEvent? {
        var requestType = getSiaStateRequestType(siaBlock)
        if (requestType != null) {
            return requestType.decoder.decode(siaBlock)
        }
        logger.info("Configuration event ${siaBlock.messageAsString} not yet handled.")
        return null
    }

    private fun getSiaStateRequestType(siaBlock: SiaBlock): SiaStateRequestType? {
        val message = siaBlock.messageAsString
        var requestType = if (siaBlock.message.size >= 4)
            SiaStateRequestType.getRequest(message.substring(0, 4))
        else null
        if (requestType == null)
            requestType = SiaStateRequestType.getRequest(message.substring(0, 4))
        return requestType
    }
}
