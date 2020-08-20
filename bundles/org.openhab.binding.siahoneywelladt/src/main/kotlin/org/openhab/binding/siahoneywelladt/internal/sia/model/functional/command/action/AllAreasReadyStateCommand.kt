package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.action

import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.SiaStateRequestCommand
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.SiaStateRequestType

class AllAreasReadyStateCommand :
    SiaStateRequestCommand(
        SiaStateRequestType.ALL_AREAS_READY_STATE
    ) {
    override fun getSiaBlocks(): List<SiaBlock> =
        listOf(createSiaBlock())
}
