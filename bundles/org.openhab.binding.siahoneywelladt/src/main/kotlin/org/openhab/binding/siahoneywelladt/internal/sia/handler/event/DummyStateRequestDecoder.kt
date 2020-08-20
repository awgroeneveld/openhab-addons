package org.openhab.binding.siahoneywelladt.internal.sia.handler.event

import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.SiaStateRequestType
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.event.SiaConfigurationEvent
import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.support.LoggerDelegate

class DummyStateRequestDecoder private constructor() :
    SiaStateRequestDecoder {
    val logger by LoggerDelegate()

    companion object {
        val instance =
            DummyStateRequestDecoder()
    }

    override fun decode(siaBlock: SiaBlock, siaStateRequestType: SiaStateRequestType): SiaConfigurationEvent? {
        logger.error("Dummy decoder for sia state request: $siaStateRequestType, message ${siaBlock.messageAsString}")
        return null
    }
}
