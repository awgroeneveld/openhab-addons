package org.openhab.binding.siahoneywelladt.internal.handler

import org.openhab.binding.siahoneywelladt.internal.model.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.model.SiaBlockEventDecoder
import org.openhab.binding.siahoneywelladt.internal.model.SiaEvent
import org.openhab.binding.siahoneywelladt.internal.model.SiaMetaData
import org.openhab.binding.siahoneywelladt.internal.model.SiaRegularEvent
import org.openhab.binding.siahoneywelladt.internal.support.LoggerDelegate

class RegularEventDecoder : EventDecoder() {
    private val logger by LoggerDelegate()
    fun decode(siaBlock: SiaBlock, siaLevel: Int): SiaEvent? {
        decodeAcknowledgeRejectEvent(siaBlock)?.let { return it }
        val eventInfo = SiaBlockEventDecoder.instance.getSiaEventCodeAndType(siaBlock) ?: return null
        if (eventInfo.second == null)
            throw IllegalArgumentException("Unknown event code: ${eventInfo.first} for $siaBlock")
        return SiaRegularEvent(
            eventInfo.second!!,
            SiaMetaData.fromMessage(
                siaBlock.messageAsString
            ),
            siaBlock.message,
            siaBlock.function.needsAcknowledge,
            siaBlock.getTotalLength()
        )
    }
}
