package org.openhab.binding.siahoneywelladt.internal.model.transport

import org.openhab.binding.siahoneywelladt.internal.model.SiaFunction

class SiaBlock(val function: SiaFunction, val payload: ByteArray)
