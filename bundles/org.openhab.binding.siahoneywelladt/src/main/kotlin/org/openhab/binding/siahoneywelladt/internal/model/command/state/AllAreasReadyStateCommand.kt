package org.openhab.binding.siahoneywelladt.internal.model.command.state

import org.openhab.binding.siahoneywelladt.internal.model.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.model.command.SiaStateRequestCommand
import org.openhab.binding.siahoneywelladt.internal.model.command.SiaStateRequestType

class AllAreasReadyStateCommand :
    SiaStateRequestCommand(
        SiaStateRequestType.ALL_AREAS_READY_STATE
    ) {
    override fun getSiaBlocks(): List<SiaBlock> =
        listOf(createSiaBlock())
}
