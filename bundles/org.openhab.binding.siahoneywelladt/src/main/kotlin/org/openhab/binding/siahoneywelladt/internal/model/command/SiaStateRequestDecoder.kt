package org.openhab.binding.siahoneywelladt.internal.model.command

import org.openhab.binding.siahoneywelladt.internal.model.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.model.SiaConfigurationEvent
import org.openhab.binding.siahoneywelladt.internal.support.LoggerDelegate

interface SiaStateRequestDecoder {
    fun decode(siaBlock: SiaBlock, siaStateRequestType: SiaStateRequestType): SiaConfigurationEvent?
}

class DummyStateRequestDecoder private constructor() : SiaStateRequestDecoder {
    val logger by LoggerDelegate()

    companion object {
        val instance = DummyStateRequestDecoder()
    }

    override fun decode(siaBlock: SiaBlock, siaStateRequestType: SiaStateRequestType): SiaConfigurationEvent? {
        logger.error("Dummy decoder for sia state request: $siaStateRequestType, message ${siaBlock.messageAsString}")
        return null
    }
}

