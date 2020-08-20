package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.event

import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SharedConfig.CHARACTER_SET

class SiaRegularEvent(
    val eventType: SiaEventType,
    val metaData: SiaMetaData?,
    message: ByteArray,
    needsAcknowledge: Boolean,
    size: Int,
    val messageText: String? = null
) :
    SiaEvent(message, needsAcknowledge, size) {

    override fun toString(): String {
        return "SIA Event: ${eventType.name} (${eventType.code}), message: ${message.toString(CHARACTER_SET)}, messageText: $messageText, metaData:$metaData"
    }
}


