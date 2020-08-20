package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.state

enum class AreaArmedState(override val siaState: Int) :
    SiaState {
    UNSET(0),
    SET(1),
    PART_SET(2);

    companion object {
        private val statesBySiaValue = values()
            .associateBy { it.siaState }

        fun getState(siaValue: Int) = statesBySiaValue[siaValue]
    }
}
