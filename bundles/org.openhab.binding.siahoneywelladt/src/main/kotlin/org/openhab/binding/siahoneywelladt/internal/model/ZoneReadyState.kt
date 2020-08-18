package org.openhab.binding.siahoneywelladt.internal.model

enum class ZoneReadyState(override val siaState: Int) : SiaState {
    READY(0), NOT_READY(1);

    companion object {
        private val zoneReadyBySiaState = values().associateBy { it.siaState }
        fun getZoneReadyState(siaState: Int) = zoneReadyBySiaState[siaState]
    }
}
