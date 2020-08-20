package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.state

import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.SiaStateMultiRequestCommand
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.SiaStateRequestType

class AllZonesAlarmStateCommand :
    SiaStateMultiRequestCommand(
        SiaStateRequestType.ALL_ZONES_ALARM_STATE
    ) {
    override fun getSiaBlocks(): List<SiaBlock> =
        createSiaBlocks()
}

