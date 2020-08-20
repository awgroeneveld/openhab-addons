package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.event

abstract class SiaEvent(val message: ByteArray, val needsAcknowledge: Boolean, val size: Int)
