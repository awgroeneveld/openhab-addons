package org.openhab.binding.siahoneywelladt.internal.sia.model.functional

import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaFunction
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.SiaCommand

class AcknowledgeCommand private constructor() : SiaCommand(
    SiaFunction.ACKNOWLEDGE
) {
    companion object {
        val instance =
            AcknowledgeCommand()
    }

    private val siaBlocks = listOf(createSiaBlockFromMessage(""))

    override fun getSiaBlocks() = siaBlocks
}
