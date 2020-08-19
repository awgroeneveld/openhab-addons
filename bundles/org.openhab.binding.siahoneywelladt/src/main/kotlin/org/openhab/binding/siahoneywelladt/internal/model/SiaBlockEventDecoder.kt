package org.openhab.binding.siahoneywelladt.internal.model

import org.openhab.binding.siahoneywelladt.internal.handler.IllegalSiaCommandException

class SiaBlockEventDecoder private constructor() {
    companion object {
        val instance = SiaBlockEventDecoder()
    }

    fun getSiaEventCodeAndType(siaBlock: SiaBlock): Pair<String, SiaEventType?>? {
        val eventCode = getEventCode(siaBlock)
        return eventCode?.let {
            it to SiaEventType.getSiaEvent(
                eventCode
            )
        }
    }

    private fun getEventCode(siaBlock: SiaBlock): String? {
        val eventType = siaBlock.function.eventType
        return when (eventType) {
            SiaEventHandlerType.ASCII -> null
            SiaEventHandlerType.CONFIGURATION -> getConfigurationEventCode(siaBlock)
            SiaEventHandlerType.REGULAR -> getRegularEventCode(siaBlock)
            else -> throw IllegalSiaCommandException("Unsupported event type $eventType", siaBlock.getTotalLength())
        }
    }

    private fun getRegularEventCode(siaBlock: SiaBlock): String? {
        val message = siaBlock.messageAsString
        if (message.length < 2)
            return null
        if (message.contains("/")) {
            val item = message.split("/")
                .last()
            return if (item.length >= 2) item.substring(0, 2) else null
        }

        return message.substring(message.length - 2, message.length)
    }

    private fun getConfigurationEventCode(siaBlock: SiaBlock) =
        if (siaBlock.messageAsString.length < 2)
            null
        else
            siaBlock.messageAsString.substring(0, 2)

}
