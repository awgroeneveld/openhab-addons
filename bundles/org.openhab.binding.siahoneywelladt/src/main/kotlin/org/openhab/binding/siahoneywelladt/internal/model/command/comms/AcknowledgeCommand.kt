package org.openhab.binding.siahoneywelladt.internal.model.command.comms

import org.openhab.binding.siahoneywelladt.internal.model.SiaFunction
import org.openhab.binding.siahoneywelladt.internal.model.command.SiaCommand

class AcknowledgeCommand private constructor() : SiaCommand(
    SiaFunction.ACKNOWLEDGE
) {
    companion object {
        val instance = AcknowledgeCommand()
    }

    private val siaBlocks = listOf(createSiaBlockFromMessage(""))

    override fun getSiaBlocks() = siaBlocks
}
