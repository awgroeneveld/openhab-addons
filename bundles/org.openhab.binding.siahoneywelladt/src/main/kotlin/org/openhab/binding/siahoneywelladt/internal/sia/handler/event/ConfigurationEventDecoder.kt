package org.openhab.binding.siahoneywelladt.internal.sia.handler.event

import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.event.SiaEvent
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.SiaStateRequestType
import org.openhab.binding.siahoneywelladt.internal.support.LoggerDelegate

class ConfigurationEventDecoder : EventDecoder() {
    private val logger by LoggerDelegate()
    fun decode(siaBlock: SiaBlock, siaLevel: Int): SiaEvent? {
        decodeAcknowledgeRejectEvent(siaBlock)?.let { return it }
        val requestType = getSiaStateRequestType(siaBlock)
        if (requestType != null) {
            return requestType.decoder.decode(siaBlock, requestType)
        }
        logger.info("Configuration event {} not yet handled.",siaBlock.messageAsString )
        return null
    }

    private fun getSiaStateRequestType(siaBlock: SiaBlock): SiaStateRequestType? =
        SiaStateRequestType.getRequestTypeByReturnString(
            siaBlock.messageAsString
        )
}
