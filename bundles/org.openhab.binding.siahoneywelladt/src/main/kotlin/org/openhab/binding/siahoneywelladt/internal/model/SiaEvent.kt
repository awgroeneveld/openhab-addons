package org.openhab.binding.siahoneywelladt.internal.model

import org.openhab.binding.siahoneywelladt.internal.handler.SharedConfig.CHARACTER_SET

class SiaEvent(val eventType: SiaEventType, val message: ByteArray, val needsAcknowledge: Boolean) {
    override fun toString(): String {
        return "SIA Event: ${eventType.name} (${eventType.code}), message: ${message.toString(CHARACTER_SET)}"
    }
}
