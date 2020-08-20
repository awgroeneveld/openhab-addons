package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.state

enum class ZoneArmingState(override val siaState: Int) :
    SiaState {
    UNOMIT(0),
    OMIT(1)
}
