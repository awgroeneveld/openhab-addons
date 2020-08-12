package org.openhab.binding.siahoneywelladt.internal.model

import org.openhab.binding.siahoneywelladt.internal.handler.SharedConfig.CHARACTER_SET

abstract class SiaEvent(val message: ByteArray, val needsAcknowledge: Boolean)

class SiaRegularEvent(val eventType: SiaEventType, message: ByteArray, needsAcknowledge: Boolean) :
    SiaEvent(message, needsAcknowledge) {
    override fun toString(): String {
        return "SIA Event: ${eventType.name} (${eventType.code}), message: ${message.toString(CHARACTER_SET)}"
    }
}

abstract class SiaConfigurationEvent(val eventType: SiaEventType, message: ByteArray, needsAcknowledge: Boolean) :
    SiaEvent(message, needsAcknowledge) {
    override fun toString(): String {
        return "SIA Event: ${eventType.name} (${eventType.code}), message: ${message.toString(CHARACTER_SET)}"
    }
}

class AllAreasReadyStateEvent(eventType: SiaEventType, message: ByteArray, needsAcknowledge: Boolean) :
    SiaConfigurationEvent(eventType, message, needsAcknowledge)

class SiaWaitForMoreEvent(message: ByteArray, needsAcknowledge: Boolean) : SiaEvent(message, needsAcknowledge)
class UnhandledSiaEvent(message: ByteArray, needsAcknowledge: Boolean) : SiaEvent(message, needsAcknowledge)
