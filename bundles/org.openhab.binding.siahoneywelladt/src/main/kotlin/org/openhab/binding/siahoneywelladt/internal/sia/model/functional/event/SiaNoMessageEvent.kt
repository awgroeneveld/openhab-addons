package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.event

import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaFunction

class SiaNoMessageEvent(val function: SiaFunction, message: ByteArray, needsAcknowledge: Boolean, size: Int) :
    SiaEvent(message, needsAcknowledge, size) {
    override fun toString(): String {
        return "Function: ${function.name}"
    }
}
