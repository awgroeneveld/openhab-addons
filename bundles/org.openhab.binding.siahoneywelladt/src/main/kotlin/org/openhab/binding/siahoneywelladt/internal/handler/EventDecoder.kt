package org.openhab.binding.siahoneywelladt.internal.handler

import org.openhab.binding.siahoneywelladt.internal.model.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.model.SiaEvent
import org.openhab.binding.siahoneywelladt.internal.model.SiaFunction
import org.openhab.binding.siahoneywelladt.internal.model.SiaNoMessageEvent

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
