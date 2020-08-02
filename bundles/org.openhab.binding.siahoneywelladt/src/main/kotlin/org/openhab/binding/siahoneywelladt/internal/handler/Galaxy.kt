package org.openhab.binding.siahoneywelladt.internal.handler

enum class SiaFunction(val value: Byte) {
    END_OF_DATA(0x30),
    WAIT(0x31),
    ABORT(0x32),
    RES_3(0x33),
    RES_4(0x34),
    RES_5(0x35),
    ACK_AND_STANDBY(0x36),
    ACK_AND_DISCONNECT(0x37),
    ACKNOWLEDGE(0x38),
    ALT_ACKNOWLEDGE(0x08),
    REJECT(0x39),
    ALT_REJECT(0x09),

    // INFO BLOCKS
    CONTROL(0x43),
    ENVIRONMENTAL(0x45),
    NEW_EVENT(0x4E),
    OLD_EVENT(0x4F),
    PROGRAM(0x50),

    // SPECIAL BLOCKS
    CONFIGURATION(0x40),
    REMOTE_LOGIN(0x3F),
    ACCOUNT_ID(0x23),
    ORIGIN_ID(0x26),
    ASCII(0x41),
    EXTENDED(0x58),
    LISTEN_IN(0x4C),
    VCHN_REQUEST(0x56),
    VCHN_FRAME(0x76),
    VIDEO(0x49)
}

class SiaBlock(val function: SiaFunction, val payload: ByteArray)

enum class AddressField(val siaValue: Byte) {
    UNUSED(0),
    ZONE(1),
    AREA(2),
    USER(3),
    DOOR(4),
    DEALER_ID(5),
    EXPANDER(6),
    LINE(7),
    RELAY(8),
    POINT(9),
    PRINTER(10),
    MFR_DEFINED(11)
};

enum class AreaAction(val siaValue: Byte) {
    UNSET(0),
    SET(1),
    PART_SET(2),
    RESET(3),
    ABORT_SET(4),
    FORCE_SET(5)
}

enum class AreaArmedState(val siaValue: Byte) {
    UNSET(0),
    SET(1),
    PART_SET(2)
}

enum class AreaAlarmState(val siaValue: Byte) {
    NORMAL(0),
    ALARM(1),
    RESET_REQUIRED(2)
}

enum class AreaReadyState(val siaValue: Byte) {
    UNSET(0),
    SET(1),
    PART_SET(2),
    READY_TO_SET(3),
    TIME_LOCKED(4)
}

enum class ZoneArmingState(val siaValue: Byte) {
    UNOMIT(0),
    OMIT(1)
}

enum class OutputState(val siaValue: Byte) {
    OFF(0),
    ON(1)
}

enum class ZoneState(val siaValue: Byte) {
    TAMPER_SC(0),
    LOW_RESISTANCE(1),
    CLOSED(2),
    HIGH_RESISTANCE(3),
    OPEN(4),
    TAMPER_OC(5),
    MASKED(6),
    TAMPER_CV(7),
    FAULT(8)
}


interface Galaxy {
    fun isValidZoneNumber(zoneId: Int)
    fun isValidOutputNumber(outputId: Int)

    fun performAreaAction(action: AreaAction, blockNumber: Int): Boolean
    fun performAreaActionOnAll(action: AreaAction) = performAreaAction(action, 0)
    fun getArmedState(blockNumber: Int): AreaArmedState
    fun getArmedStateByArea(): Map<Int, AreaArmedState>
    fun getAlarmStateByArea(): Map<Int, AreaAlarmState>
    fun getReadyStateByArea(): Map<Int, AreaReadyState>
    fun setZoneArmingState(zoneId: Int, state: ZoneArmingState): Boolean
    fun getZoneArmingState(zoneId: Int): ZoneArmingState
    fun getZoneArmingStatesByZone()
    fun getZoneState(zoneId: Int, state: ZoneState)
    fun getAllZonesWithState(state: ZoneState): List<Int>
    fun getAllOutputStatesByOutput(): Map<Int, OutputState>
    fun setOutputState(outputId: Int, state: OutputState): Boolean
    fun setAllOutputStates(state: OutputState): Boolean = setOutputState(0, state)

}
