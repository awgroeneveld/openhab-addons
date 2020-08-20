package org.openhab.binding.siahoneywelladt.internal.sia.model.technical

import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.SiaCommand

class RejectCommand private constructor() : SiaCommand(
    SiaFunction.REJECT
) {
    companion object {
        val instance =
            RejectCommand()
    }

    private val siaBlocks: List<SiaBlock> = listOf(createSiaBlockFromMessage(""))

    override fun getSiaBlocks() = siaBlocks
}
