package org.openhab.binding.siahoneywelladt.internal.model

enum class AreaArmedState(override val siaState: Int):SiaState {
    UNSET(0),
    SET(1),
    PART_SET(2)
}
