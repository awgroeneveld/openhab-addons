package org.openhab.binding.siahoneywelladt.internal.model

enum class ZoneArmingState(override val siaState: Int) : SiaState {
    UNOMIT(0),
    OMIT(1)
}
