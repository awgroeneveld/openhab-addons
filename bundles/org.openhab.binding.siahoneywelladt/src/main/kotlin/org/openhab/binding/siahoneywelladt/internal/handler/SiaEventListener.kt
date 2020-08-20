package org.openhab.binding.siahoneywelladt.internal.handler

import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.event.SiaEvent

interface SiaEventListener {
    fun handleEvent(siaEvent: SiaEvent)
}
