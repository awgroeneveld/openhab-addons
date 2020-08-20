package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.state

import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.Zone
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.SiaStateRequestCommand
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.SiaStateRequestType

class ZoneOmitStateCommand(private val zones: List<Zone>) :
    SiaStateRequestCommand(
        SiaStateRequestType.ZONE_OMIT_STATE
    ) {
    override fun getSiaBlocks(): List<SiaBlock> =
        zones.map { createSiaBlock(it.identifier) }
}


