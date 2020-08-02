package org.openhab.binding.siahoneywelladt.internal.model

enum class AreaAction(val siaValue: Byte) {
    UNSET(0),
    SET(1),
    PART_SET(2),
    RESET(3),
    ABORT_SET(4),
    FORCE_SET(5)
}
