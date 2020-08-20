package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.action

import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.Zone
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.SiaActionCommand
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.SiaCommandType

class ZoneOmitActionCommand(private val action: ZoneAction, private val zones: List<Zone>) :
    SiaActionCommand(
        SiaCommandType.ZONE_ACTION) {

    override fun getSiaBlocks(): List<SiaBlock> =
        zones.map { createSiaBlock(it.identifier, action) }

}
