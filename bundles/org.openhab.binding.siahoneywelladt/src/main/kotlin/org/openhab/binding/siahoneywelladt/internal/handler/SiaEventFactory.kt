package org.openhab.binding.siahoneywelladt.internal.handler

import org.openhab.binding.siahoneywelladt.internal.model.SiaEventType
import org.openhab.binding.siahoneywelladt.internal.model.SiaRegularEvent

class SiaEventFactory private constructor() {
    companion object {
        val instance = SiaEventFactory()
    }

    fun createSiaEvent(eventType: SiaEventType, message: ByteArray, needsAcknowledge: Boolean) =
        SiaRegularEvent(
            eventType,
            message,
            needsAcknowledge
        )
}
