package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.event

abstract class SiaConfigurationEvent(message: ByteArray, needsAcknowledge: Boolean, size: Int) :
    SiaEvent(message, needsAcknowledge, size)
