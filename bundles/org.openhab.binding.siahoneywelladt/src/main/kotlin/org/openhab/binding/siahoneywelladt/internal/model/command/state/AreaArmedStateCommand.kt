package org.openhab.binding.siahoneywelladt.internal.model.command.state

import org.openhab.binding.siahoneywelladt.internal.model.Area
import org.openhab.binding.siahoneywelladt.internal.model.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.model.command.SiaStateRequestCommand
import org.openhab.binding.siahoneywelladt.internal.model.command.SiaStateRequestType

class AreaArmedStateCommand(private val areas: List<Area> = listOf()) :
    SiaStateRequestCommand(
        SiaStateRequestType.AREA_ARMED_STATE
    ) {
    override fun getSiaBlocks(): List<SiaBlock> {
        return if (areas.isEmpty())
            listOf(createSiaBlock())
        else
            areas.map { createSiaBlock(it.identifier) }
    }
}
