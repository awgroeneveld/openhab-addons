package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.action

import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.Area
import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.SiaActionCommand
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.SiaCommandType
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.action.AreaAction

class AreaActionCommand(private val action: AreaAction, private val areas: List<Area> = listOf()) :
    SiaActionCommand(
        SiaCommandType.AREA_ACTION) {

    override fun getSiaBlocks(): List<SiaBlock> {
        return if (areas.isEmpty())
            listOf(createSiaBlock(action))
        else
            areas.map { createSiaBlock(it.identifier, action) }
    }
}
