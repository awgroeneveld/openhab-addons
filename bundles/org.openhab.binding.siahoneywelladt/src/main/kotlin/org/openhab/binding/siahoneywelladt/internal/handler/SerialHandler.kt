package org.openhab.binding.siahoneywelladt.internal.handler

import org.eclipse.smarthome.core.thing.ThingStatus
import org.eclipse.smarthome.core.thing.ThingStatusDetail

class ThingStatusException(val detail: ThingStatusDetail, message: String, throwable: Throwable) :
    Exception(message, throwable)

interface StatusUpdateListener {
    fun handleStatusChange(status: ThingStatus, detail: ThingStatusDetail, message: String?)
}

