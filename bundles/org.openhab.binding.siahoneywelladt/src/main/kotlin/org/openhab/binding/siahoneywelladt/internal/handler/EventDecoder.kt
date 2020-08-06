package org.openhab.binding.siahoneywelladt.internal.handler

import org.openhab.binding.siahoneywelladt.internal.handler.Constants.EVENT_DECODER_SIZE
import org.openhab.binding.siahoneywelladt.internal.model.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.model.SiaBlockHeader
import org.openhab.binding.siahoneywelladt.internal.model.SiaEvent
import org.openhab.binding.siahoneywelladt.internal.model.SiaFunction
import org.openhab.binding.siahoneywelladt.internal.model.command.comms.AcknowledgeCommand
import org.openhab.binding.siahoneywelladt.internal.model.command.comms.RejectCommand
import org.openhab.binding.siahoneywelladt.internal.support.LoggerDelegate

class EventDecoder(private val eventListener: SiaEventListener, private val commandTransmitter: SiaCommandTransmitter) {
    private val logger by LoggerDelegate()
    private val buffer = ByteArray(EVENT_DECODER_SIZE)
    private var size = 0


    private fun decodeFunctionsAndReturnLastCorrectEndIndex() {
        var indexToFunction = findFirstControlCommand()
        while (indexToFunction != null) {
            val (index, function) = indexToFunction
            try {
                decodeFunction(index, function)?.let {
                    handleEvent(it)
                    shiftBytes(index, it.message.size + SiaBlock.blockOverhead)
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
        val eventType = siaBlock.getSiaEventType()
            ?: throw IllegalSiaCommandException(
                "Could not determine event type from SIA block $siaBlock",
                siaBlock.getTotalLength()
            )
        return SiaEventFactory.instance.createSiaEvent(
            eventType,
            siaBlock.message,
            function.needsAcknowledge
        )
    }

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
