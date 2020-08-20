package org.openhab.binding.siahoneywelladt.internal.api

import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.Area
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.action.AreaAction
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.state.AreaAlarmState
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.state.AreaArmedState
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.state.AreaReadyState
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.state.OutputState
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.state.SingleZoneTechnicalState
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.Zone
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.state.ZoneArmingState


interface Galaxy {
    fun isValidZoneNumber(zoneId: Int)
    fun isValidOutputNumber(outputId: Int)

    fun performAreaAction(action: AreaAction, area: Area): Boolean
    fun performAreaActionOnAll(action: AreaAction)
    fun getArmedState(area: Area): AreaArmedState
    fun getArmedStateByArea(): Map<Area, AreaArmedState>
    fun getAlarmStateByArea(): Map<Area, AreaAlarmState>
    fun getReadyStateByArea(): Map<Area, AreaReadyState>
    fun setZoneArmingState(zone: Zone, state: ZoneArmingState): Boolean
    fun getZoneArmingState(zone: Zone): ZoneArmingState
    fun getZoneArmingStatesByZone(): Map<Zone, ZoneArmingState>
    fun getZoneState(zone: Zone): SingleZoneTechnicalState
    fun getAllZonesWithState(state: SingleZoneTechnicalState): List<Zone>
    fun getAllOutputStatesByOutput(): Map<Int, OutputState>
    fun setOutputState(outputId: Int, state: OutputState): Boolean
    fun setAllOutputStates(state: OutputState): Boolean

}
