package org.openhab.binding.siahoneywelladt.internal.handler

import org.openhab.binding.siahoneywelladt.internal.model.SiaEvent
import org.openhab.binding.siahoneywelladt.internal.model.SiaEventType

class SiaEventFactory private constructor() {
    companion object {
        val instance = SiaEventFactory()
    }

    fun createSiaEvent(eventType: SiaEventType, message: ByteArray, needsAcknowledge: Boolean) =
        SiaEvent(
            eventType,
            message,
            needsAcknowledge
        )
}
