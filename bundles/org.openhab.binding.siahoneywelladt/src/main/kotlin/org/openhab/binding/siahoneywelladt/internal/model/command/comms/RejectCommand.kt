package org.openhab.binding.siahoneywelladt.internal.model.command.comms

import org.openhab.binding.siahoneywelladt.internal.model.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.model.SiaFunction
import org.openhab.binding.siahoneywelladt.internal.model.command.SiaCommand

class RejectCommand private constructor() : SiaCommand(
    SiaFunction.REJECT
) {
    companion object {
        val instance = RejectCommand()
    }

    private val siaBlocks: List<SiaBlock> = listOf(createSiaBlockFromMessage(""))

    override fun getSiaBlocks() = siaBlocks
}
