package org.openhab.binding.siahoneywelladt.internal.model

enum class AreaAlarmState(val siaValue: Byte) {
    NORMAL(0),
    ALARM(1),
    RESET_REQUIRED(2)
}
