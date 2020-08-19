package org.openhab.binding.siahoneywelladt.internal.api

import org.openhab.binding.siahoneywelladt.internal.model.Area
import org.openhab.binding.siahoneywelladt.internal.model.AreaAction
import org.openhab.binding.siahoneywelladt.internal.model.AreaAlarmState
import org.openhab.binding.siahoneywelladt.internal.model.AreaArmedState
import org.openhab.binding.siahoneywelladt.internal.model.AreaReadyState
import org.openhab.binding.siahoneywelladt.internal.model.OutputState
import org.openhab.binding.siahoneywelladt.internal.model.SingleZoneTechnicalState
import org.openhab.binding.siahoneywelladt.internal.model.Zone
import org.openhab.binding.siahoneywelladt.internal.model.ZoneArmingState


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
