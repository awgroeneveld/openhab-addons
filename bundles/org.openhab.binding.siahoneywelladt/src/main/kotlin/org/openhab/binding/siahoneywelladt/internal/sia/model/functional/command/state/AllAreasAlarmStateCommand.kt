package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.state

import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.SiaStateRequestCommand
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.SiaStateRequestType

class AllAreasAlarmStateCommand :
    SiaStateRequestCommand(
        SiaStateRequestType.ALL_AREAS_ALARM_STATE
    ) {
    override fun getSiaBlocks(): List<SiaBlock> =
        listOf(createSiaBlock())
}
