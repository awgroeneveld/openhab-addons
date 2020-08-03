package org.openhab.binding.siahoneywelladt.internal.model

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
        require(numberInGroup in 1..8)
        identifier = (10 * (group - 'A') + numberInGroup)
    }
}

class SiaBlock(val function: SiaFunction, val message: ByteArray, val reverseChannelEnabled: Boolean = false) {
    companion object{
        const val blockOverhead=3
    }
    val header = (message.size +
            (if (function.needsAcknowledge) 64 else 0) +
            (if (reverseChannelEnabled) 128 else 0))

    fun getParity():Byte{
        var parity=255 xor header xor function.value
        message.forEach { parity=parity xor it.toInt() }
        return parity.toByte()
    }

    fun toByteArray(){
        val size = message.size + blockOverhead
        val result=ByteArray(size)
        result[0]=header.toByte()
        result[1]=function.value.toByte()
        message.copyInto(result,2)
        result[size-1]=getParity()
    }

}

enum class SiaCommandSubjectType {
    AREA, ZONE
}

enum class SiaCommandType(val value: String, val type: SiaCommandSubjectType) {
    AREA("SA", SiaCommandSubjectType.AREA)
}


abstract class SiaCommand(val function: SiaFunction, val type: SiaCommandType) {
    abstract fun getSiaBlocks(): List<SiaBlock>
}


abstract class AllAreaSiaCommand(function: SiaFunction, type: SiaCommandType) :
    SiaCommand(function, type) {

}

abstract class SingleAreaSiaCommand(val area: Area, function: SiaFunction, type: SiaCommandType) :
    SiaCommand(function, type) {

}

class LoginCommand(val password: String)

class AreaActionCommand(area: Area, val action: AreaAction) :
    SingleAreaSiaCommand(area, SiaFunction.CONTROL, SiaCommandType.AREA) {

}

class AllAreaActionCommand(val action: AreaAction) : AllAreaSiaCommand(SiaFunction.CONTROL, SiaCommandType.AREA)
