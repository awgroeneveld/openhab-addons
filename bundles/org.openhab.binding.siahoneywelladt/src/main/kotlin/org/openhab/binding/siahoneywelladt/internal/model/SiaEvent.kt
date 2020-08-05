package org.openhab.binding.siahoneywelladt.internal.model

class SiaEvent(val eventType: SiaEventType, val message: ByteArray, val needsAcknowledge: Boolean)
