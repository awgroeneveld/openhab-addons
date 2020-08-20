package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.event

class IllegalSiaCommandException(message: String, val moveByteCount: Int) : Exception(message)
