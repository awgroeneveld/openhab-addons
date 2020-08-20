package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.state

enum class ZoneReadyState(override val siaState: Int) :
    SiaState {
    READY(0), NOT_READY(1);

    companion object {
        private val zoneReadyBySiaState = values().associateBy { it.siaState }
        fun getState(siaState: Int) = zoneReadyBySiaState[siaState]
    }
}
