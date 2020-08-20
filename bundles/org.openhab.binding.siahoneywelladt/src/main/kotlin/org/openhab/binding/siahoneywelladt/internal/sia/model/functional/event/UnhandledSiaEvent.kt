package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.event

class UnhandledSiaEvent(message: ByteArray, needsAcknowledge: Boolean, size: Int) :
    SiaEvent(message, needsAcknowledge, size)
