package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command

import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaFunction


abstract class SiaCommand(private val function: SiaFunction) {
    companion object {
        val charset = Charsets.ISO_8859_1
    }

    abstract fun getSiaBlocks(): List<SiaBlock>
    protected fun createSiaBlockFromMessage(message: String) =
        SiaBlock(
            function,
            message.toByteArray(charset)
        )
}


