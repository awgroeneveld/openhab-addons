package org.openhab.binding.siahoneywelladt.internal.model

import org.apache.commons.lang.CharSet
import java.lang.UnsupportedOperationException
import java.nio.charset.Charset

enum class SiaFunction(val value: Int, val needsAcknowledge: Boolean) {
    END_OF_DATA(0x30, true),
    WAIT(0x31, true),
    ABORT(0x32, true),
    RES_3(0x33, true),
    RES_4(0x34, true),
    RES_5(0x35, true),
    ACK_AND_STANDBY(0x36, true),
    ACK_AND_DISCONNECT(0x37, true),
    ACKNOWLEDGE(0x38, false),
    ALT_ACKNOWLEDGE(0x08, false),
    REJECT(0x39, false),
    ALT_REJECT(0x09, false),

    // INFO BLOCKS
    CONTROL(0x43, false),
    ENVIRONMENTAL(0x45, true),
    NEW_EVENT(0x4E, true),
    OLD_EVENT(0x4F, true),
    PROGRAM(0x50, true),

    // SPECIAL BLOCKS
    CONFIGURATION(0x40, false),
    REMOTE_LOGIN(0x3F, true),
    ACCOUNT_ID(0x23, true),
    ORIGIN_ID(0x26, true),
    ASCII(0x41, true),
    EXTENDED(0x58, false),
    LISTEN_IN(0x4C, true),
    VCHN_REQUEST(0x56, true),
    VCHN_FRAME(0x76, true),
    VIDEO(0x49, true)
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

data class Zone(val line: Int, val rio: Int, val connection:Int){
    val identifier: Int

    init{
        require(line in 1..4)
        require(rio in 0..15)
        require(connection in 1..8)
        identifier = 1000*line+rio*10+connection
    }
}

class SiaBlock(val function: SiaFunction, val message: ByteArray, val reverseChannelEnabled: Boolean = false) {
    data class SiaBlockHeader(val messageSize: Int, val needsAcknowledge: Boolean, val reverseChannelEnabled: Boolean){
        val value=(messageSize +
                (if (needsAcknowledge) 64 else 0) +
                (if (reverseChannelEnabled) 128 else 0))
    }
    companion object{
        const val blockOverhead=3
    }
    val header = SiaBlockHeader(message.size, function.needsAcknowledge, reverseChannelEnabled)

    fun checksum():Byte{
        var parity=255 xor header.value xor function.value
        message.forEach { parity=parity xor it.toInt() }
        return parity.toByte()
    }

    fun toByteArray(): ByteArray {
        val size = message.size + blockOverhead
        val result=ByteArray(size)
        result[0]=header.value.toByte()
        result[1]=function.value.toByte()
        message.copyInto(result,2)
        result[size-1]=checksum()
        return result
    }

}

