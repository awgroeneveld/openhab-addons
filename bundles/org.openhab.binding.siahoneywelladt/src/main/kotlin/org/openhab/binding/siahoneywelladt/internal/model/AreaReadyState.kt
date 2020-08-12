package org.openhab.binding.siahoneywelladt.internal.model

enum class AreaReadyState(val siaValue: Int) {
    UNSET(0),
    SET(1),
    PART_SET(2),
    READY_TO_SET(3),
    TIME_LOCKED(4);

    companion object {
        private val statesBySiaValue = values().associateBy { it.siaValue }
        fun getAreaReadyState(siaValue: Int) = statesBySiaValue[siaValue]
    }
}
