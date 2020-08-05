package org.openhab.binding.siahoneywelladt.internal.model.command.state

import org.openhab.binding.siahoneywelladt.internal.model.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.model.command.SiaStateMultiRequestCommand
import org.openhab.binding.siahoneywelladt.internal.model.command.SiaStateRequestType

class AllZonesFaultStateCommand :
    SiaStateMultiRequestCommand(
        SiaStateRequestType.ALL_ZONES_FAULT_STATE
    ) {
    override fun getSiaBlocks(): List<SiaBlock> =
        createSiaBlocks()
}
