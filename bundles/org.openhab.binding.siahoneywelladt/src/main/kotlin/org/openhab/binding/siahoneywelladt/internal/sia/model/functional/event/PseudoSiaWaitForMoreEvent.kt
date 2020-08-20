package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.event

import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SharedConfig
import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaFunction

class PseudoSiaWaitForMoreEvent(val function: SiaFunction, message: ByteArray, needsAcknowledge: Boolean, size: Int) :
    SiaEvent(message, needsAcknowledge, size) {
    override fun toString() =
        "${this::class.simpleName} with function $function and message: ${message.toString(SharedConfig.CHARACTER_SET)}"
}
