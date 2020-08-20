package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.state

import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.Area
import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.SiaStateRequestCommand
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.SiaStateRequestType

class AreaArmedStateCommand(private val areas: List<Area> = listOf()) :
    SiaStateRequestCommand(
        SiaStateRequestType.ALL_AREAS_ARMED_STATE
    ) {
    override fun getSiaBlocks(): List<SiaBlock> {
        return if (areas.isEmpty())
            listOf(createSiaBlock())
        else
            areas.map { createSiaBlock(it.identifier) }
    }
}
