package org.openhab.binding.siahoneywelladt.internal.handler

class IllegalSiaCommandException(message: String, val moveByteCount: Int) : Exception(message)
