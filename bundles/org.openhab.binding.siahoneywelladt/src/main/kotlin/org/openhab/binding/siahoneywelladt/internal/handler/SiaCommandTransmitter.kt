package org.openhab.binding.siahoneywelladt.internal.handler

import org.openhab.binding.siahoneywelladt.internal.model.command.SiaCommand

interface SiaCommandTransmitter {
    fun transmit(command: SiaCommand)
}
