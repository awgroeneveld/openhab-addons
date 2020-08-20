package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command

import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaFunction

class LoginCommand(val password: String) : SiaCommand(
    SiaFunction.REMOTE_LOGIN
) {
    override fun getSiaBlocks(): List<SiaBlock> {
        return listOf(createSiaBlockFromMessage(password))
    }
}
