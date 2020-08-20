package org.openhab.binding.siahoneywelladt.internal.sia.handler.event

import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.SiaStateRequestType
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.event.SiaConfigurationEvent
import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaBlock

interface SiaStateRequestDecoder {
    fun decode(siaBlock: SiaBlock, siaStateRequestType: SiaStateRequestType): SiaConfigurationEvent?
}

