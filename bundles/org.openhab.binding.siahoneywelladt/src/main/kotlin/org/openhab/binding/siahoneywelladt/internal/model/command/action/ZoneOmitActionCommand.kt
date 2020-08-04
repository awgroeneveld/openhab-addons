package org.openhab.binding.siahoneywelladt.internal.model.command.action

import org.openhab.binding.siahoneywelladt.internal.model.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.model.Zone
import org.openhab.binding.siahoneywelladt.internal.model.ZoneAction
import org.openhab.binding.siahoneywelladt.internal.model.command.SiaActionCommand
import org.openhab.binding.siahoneywelladt.internal.model.command.SiaCommandType

class ZoneOmitActionCommand(private val action: ZoneAction, private val zones: List<Zone>) :
    SiaActionCommand(SiaCommandType.ZONE_ACTION) {

    override fun getSiaBlocks(): List<SiaBlock> =
        zones.map { createSiaBlock(it.identifier, action) }

}
