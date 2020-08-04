package org.openhab.binding.siahoneywelladt.internal.model.command.action

import org.openhab.binding.siahoneywelladt.internal.model.Area
import org.openhab.binding.siahoneywelladt.internal.model.AreaAction
import org.openhab.binding.siahoneywelladt.internal.model.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.model.command.SiaActionCommand
import org.openhab.binding.siahoneywelladt.internal.model.command.SiaCommandType

class AreaActionCommand(private val action: AreaAction, private val areas: List<Area> = listOf()) :
    SiaActionCommand(SiaCommandType.AREA_ACTION) {

    override fun getSiaBlocks(): List<SiaBlock> {
        return if (areas.isEmpty())
            listOf(createSiaBlock(action))
        else
            areas.map { createSiaBlock(it.identifier, action) }
    }
}
