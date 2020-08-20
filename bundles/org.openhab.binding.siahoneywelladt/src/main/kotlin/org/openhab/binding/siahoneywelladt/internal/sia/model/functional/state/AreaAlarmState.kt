package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.state

enum class AreaAlarmState(override val siaState: Int) :
    SiaState {
    NORMAL(0),
    ALARM(1),
    RESET_REQUIRED(2);

    companion object {
        private val statesBySiaValue = values()
            .associateBy { it.siaState }

        fun getState(siaValue: Int) = statesBySiaValue[siaValue]
    }
}
