package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.state

enum class AreaReadyState(override val siaState: Int) :
    SiaState {
    UNSET(0),
    SET(1),
    PART_SET(2),
    READY_TO_SET(3),
    TIME_LOCKED(4);

    companion object {
        private val statesBySiaValue = values().associateBy { it.siaState }
        fun getState(siaValue: Int) = statesBySiaValue[siaValue]
    }
}

