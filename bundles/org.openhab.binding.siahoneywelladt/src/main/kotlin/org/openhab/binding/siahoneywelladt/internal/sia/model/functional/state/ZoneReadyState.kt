package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.state

enum class ZoneReadyState(override val siaState: Int) :
    SiaState {
    READY(0), NOT_READY(1);

    companion object {
        private val zoneReadyBySiaState = values().associateBy { it.siaState }
        fun getState(siaState: Int) = zoneReadyBySiaState[siaState]
    }
}

enum class ZoneTamperState(override val siaState: Int) :
    SiaState {
    NOT_TAMPERED(0), TAMPERED(1);

    companion object {
        private val zoneReadyBySiaState = values().associateBy { it.siaState }
        fun getState(siaState: Int) = zoneReadyBySiaState[siaState]
    }
}

enum class ZoneFaultState(override val siaState: Int) :
    SiaState {
    NO_FAULT(0), FAULT(1);

    companion object {
        private val zoneReadyBySiaState = values().associateBy { it.siaState }
        fun getState(siaState: Int) = zoneReadyBySiaState[siaState]
    }
}
