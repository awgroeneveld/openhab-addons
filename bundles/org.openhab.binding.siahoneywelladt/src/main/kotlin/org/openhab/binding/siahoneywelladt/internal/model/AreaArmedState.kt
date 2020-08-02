package org.openhab.binding.siahoneywelladt.internal.model

enum class AreaArmedState(val siaValue: Byte) {
    UNSET(0),
    SET(1),
    PART_SET(2)
}
