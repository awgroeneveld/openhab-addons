package org.openhab.binding.siahoneywelladt.internal.handler

import org.openhab.binding.siahoneywelladt.internal.model.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.model.SiaEvent
import org.openhab.binding.siahoneywelladt.internal.model.command.SiaStateRequestType
import org.openhab.binding.siahoneywelladt.internal.support.LoggerDelegate

class ConfigurationEventDecoder : EventDecoder() {
    private val logger by LoggerDelegate()
    fun decode(siaBlock: SiaBlock, siaLevel: Int): SiaEvent? {
        decodeAcknowledgeRejectEvent(siaBlock)?.let { return it }
        var requestType = getSiaStateRequestType(siaBlock)
        if (requestType != null) {
            return requestType.decoder.decode(siaBlock, requestType)
        }
        logger.info("Configuration event ${siaBlock.messageAsString} not yet handled.")
        return null
    }

    private fun getSiaStateRequestType(siaBlock: SiaBlock): SiaStateRequestType? =
        SiaStateRequestType.getRequestTypeByReturnString(
            siaBlock.messageAsString
        )
}
