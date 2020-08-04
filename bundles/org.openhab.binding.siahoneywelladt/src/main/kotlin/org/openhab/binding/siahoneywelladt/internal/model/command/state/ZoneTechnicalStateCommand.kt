package org.openhab.binding.siahoneywelladt.internal.model.command.state

import org.openhab.binding.siahoneywelladt.internal.model.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.model.Zone
import org.openhab.binding.siahoneywelladt.internal.model.command.SiaStateRequestCommand
import org.openhab.binding.siahoneywelladt.internal.model.command.SiaStateRequestType

class ZoneTechnicalStateCommand(private val zones: List<Zone>) :
    SiaStateRequestCommand(
        SiaStateRequestType.ZONE_TECHNICAL_STATE
    ) {
    override fun getSiaBlocks(): List<SiaBlock> =
        zones.map { createSiaBlock(it.identifier) }
}

