package org.openhab.binding.siahoneywelladt.internal.api

import org.openhab.binding.siahoneywelladt.internal.model.AreaAction
import org.openhab.binding.siahoneywelladt.internal.model.AreaAlarmState
import org.openhab.binding.siahoneywelladt.internal.model.AreaArmedState
import org.openhab.binding.siahoneywelladt.internal.model.AreaReadyState
import org.openhab.binding.siahoneywelladt.internal.model.OutputState
import org.openhab.binding.siahoneywelladt.internal.model.ZoneArmingState
import org.openhab.binding.siahoneywelladt.internal.model.ZoneTechnicalState


interface Galaxy {
    fun isValidZoneNumber(zoneId: Int)
    fun isValidOutputNumber(outputId: Int)

    fun performAreaAction(action: AreaAction, blockNumber: Int): Boolean
    fun performAreaActionOnAll(action: AreaAction) = performAreaAction(action, 0)
    fun getArmedState(blockNumber: Int): AreaArmedState
    fun getArmedStateByArea(): Map<Int, AreaArmedState>
    fun getAlarmStateByArea(): Map<Int, AreaAlarmState>
    fun getReadyStateByArea(): Map<Int, AreaReadyState>
    fun setZoneArmingState(zoneId: Int, state: ZoneArmingState): Boolean
    fun getZoneArmingState(zoneId: Int): ZoneArmingState
    fun getZoneArmingStatesByZone()
    fun getZoneState(zoneId: Int, state: ZoneTechnicalState)
    fun getAllZonesWithState(state: ZoneTechnicalState): List<Int>
    fun getAllOutputStatesByOutput(): Map<Int, OutputState>
    fun setOutputState(outputId: Int, state: OutputState): Boolean
    fun setAllOutputStates(state: OutputState): Boolean = setOutputState(0, state)

}
