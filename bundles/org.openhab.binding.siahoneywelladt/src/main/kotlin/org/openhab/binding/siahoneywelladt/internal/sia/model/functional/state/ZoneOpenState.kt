package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.state

enum class ZoneOpenState(override val siaState: Int) :
    SiaState {
    CLOSED(0), OPEN(1);

    companion object {
        private val zoneReadyBySiaState = values().associateBy { it.siaState }
        fun getState(siaState: Int) = zoneReadyBySiaState[siaState]
    }
}
