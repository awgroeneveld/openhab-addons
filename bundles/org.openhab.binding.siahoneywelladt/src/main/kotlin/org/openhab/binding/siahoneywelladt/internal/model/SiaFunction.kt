package org.openhab.binding.siahoneywelladt.internal.model

import org.openhab.binding.siahoneywelladt.internal.handler.SharedConfig.CHARACTER_SET

enum class SiaFunction(val value: Int, val needsAcknowledge: Boolean, text: String) {
    END_OF_DATA(0x30, true, "End of data"),
    WAIT(0x31, true, "Wait"),
    ABORT(0x32, true, "Abort"),
    RES_3(0x33, true, "Reserved"),
    RES_4(0x34, true, "Reserved"),
    RES_5(0x35, true, "Reserved"),
    ACK_AND_STANDBY(0x36, true, "Acknowledge and standby"),
    ACK_AND_DISCONNECT(0x37, true, "Acknowledge and disconnect"),
    ACKNOWLEDGE(0x38, false, "Acknowledge"),
    ALT_ACKNOWLEDGE(0x08, false, "Acknowledge"),
    REJECT(0x39, false, "Reject"),
    ALT_REJECT(0x09, false, "Reject"),

    // INFO BLOCKS
    CONTROL(0x43, false, "Control"),
    ENVIRONMENTAL(0x45, true, "Environmental"),
    NEW_EVENT(0x4E, true, "New event"),
    OLD_EVENT(0x4F, true, "Old event"),
    PROGRAM(0x50, true, "Program"),

    // SPECIAL BLOCKS
    CONFIGURATION(0x40, false, "Configuration"),
    REMOTE_LOGIN(0x3F, true, "Remote login"),
    ACCOUNT_ID(0x23, true, "Account ID"),
    ORIGIN_ID(0x26, true, "Origin ID"),
    ASCII(0x41, true, "ASCII"),
    EXTENDED(0x58, false, "Extended"),
    LISTEN_IN(0x4C, true, "Listen-In"),
    VCHN_REQUEST(0x56, true, "Video channel request"),
    VCHN_FRAME(0x76, true, "Video channel frame data"),
    VIDEO(0x49, true, "Video");

    companion object {
        private val functionsByCode = values().map { it.value to it }
            .toMap()

        fun getFunction(value: Int): SiaFunction? = functionsByCode[value]
    }


}

data class Area(val group: Char, val numberInGroup: Int) {
    val identifier: Int

    init {
        require(group in 'A'..'D')
        val maxAreasInGroup = 8
        require(numberInGroup in 1..maxAreasInGroup)
        identifier = (maxAreasInGroup * (group - 'A') + numberInGroup)
    }
}

data class Zone(val line: Int, val rio: Int, val connection: Int) {
    val identifier: Int

    init {
        require(line in 1..4)
        require(rio in 0..15)
        require(connection in 1..8)
        identifier = 1000 * line + rio * 10 + connection
    }
}

class SiaBlockHeader(val messageSize: Int, val needsAcknowledge: Boolean, val reverseChannelEnabled: Boolean) {
    companion object {
        fun fromHeaderByte(byte: Byte): SiaBlockHeader {
            val byteAsInt = byte.toInt() and 0xFF
            val messageSize = byteAsInt and 63
            val needsAcknowledge = (byteAsInt and 64) == 64
            val reverseChannelEnabled = (byteAsInt and 128) == 128
            return SiaBlockHeader(messageSize, needsAcknowledge, reverseChannelEnabled)
        }
    }

    val value = (messageSize +
            (if (needsAcknowledge) 64 else 0) +
            (if (reverseChannelEnabled) 128 else 0))
    val totalSize = messageSize + 3
}

class SiaBlock(val function: SiaFunction, val header: SiaBlockHeader, val message: ByteArray) {
    companion object {
        const val blockOverhead = 3
    }

    constructor(function: SiaFunction, message: ByteArray, reverseChannelEnabled: Boolean = false) :
            this(function, SiaBlockHeader(message.size, function.needsAcknowledge, reverseChannelEnabled), message)


    val eventCode =
        if (message.size < 2)
            null
        else message.copyOfRange(0, 2)
            .toString(CHARACTER_SET)

    fun checksum(): Byte {
        var parity = 0xFF xor header.value xor function.value
        message.forEach { parity = parity xor (it.toInt() and 0xFF) }
        return parity.toByte()
    }

    fun toByteArray(): ByteArray {
        val size = message.size + blockOverhead
        val result = ByteArray(size)
        result[0] = header.value.toByte()
        result[1] = function.value.toByte()
        message.copyInto(result, 2)
        result[size - 1] = checksum()
        return result
    }

    fun getSiaEventType() = eventCode?.let { SiaEventType.getSiaEvent(eventCode) }

    fun getSiaMetaDataType() = eventCode?.let { SiaMetaDataType.getMetaDataType(eventCode) }

    fun getTotalLength() = message.size + blockOverhead

    fun getMessageAsString() = message.toString(CHARACTER_SET)

    override fun toString(): String {
        return "SIA Block, function: ${function.name}, message: ${message.toString(CHARACTER_SET)}"
    }
}

