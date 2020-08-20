package org.openhab.binding.siahoneywelladt.internal.sia.handler.comms

import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.SiaCommand

interface SiaCommandTransmitter {
    fun transmit(command: SiaCommand)
}
