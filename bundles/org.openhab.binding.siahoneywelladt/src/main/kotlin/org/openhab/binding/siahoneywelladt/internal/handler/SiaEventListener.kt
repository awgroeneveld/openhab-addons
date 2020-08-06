package org.openhab.binding.siahoneywelladt.internal.handler

import org.openhab.binding.siahoneywelladt.internal.model.SiaEvent

interface SiaEventListener {
    fun handleEvent(siaEvent: SiaEvent)
}
