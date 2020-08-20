package org.openhab.binding.siahoneywelladt.internal.sia.handler.event

import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.event.SiaEvent
import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaFunction
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.event.SiaNoMessageEvent

abstract class
EventDecoder {
    fun decodeAcknowledgeRejectEvent(siaBlock: SiaBlock): SiaEvent? {
        if (SiaFunction.messageLessValues.contains(siaBlock.function))
            return SiaNoMessageEvent(
                siaBlock.function,
                siaBlock.message,
                siaBlock.needsAcknowledge(),
                siaBlock.getTotalLength()
            )
        return null
    }
}
