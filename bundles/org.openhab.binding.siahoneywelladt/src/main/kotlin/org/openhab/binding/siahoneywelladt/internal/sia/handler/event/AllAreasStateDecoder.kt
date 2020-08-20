package org.openhab.binding.siahoneywelladt.internal.sia.handler.event

import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.SiaStateRequestType
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.Area
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.event.SiaAreaConfigurationEvent
import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.event.SiaConfigurationEvent

class AllAreasStateDecoder<X : Enum<*>>(
    private val toEnum: (Int) -> X?
) : SiaStateRequestDecoder {

    override fun decode(siaBlock: SiaBlock, siaStateRequestType: SiaStateRequestType): SiaConfigurationEvent? {
        val areaStates = siaBlock.message
            .drop(siaStateRequestType.messageHeaderLength)
            .map { it - '0'.toByte() }
            .mapIndexed { index, state ->
                Area(
                    index + 1
                ) to toEnum(state)
            }
            .toMap()

        return SiaAreaConfigurationEvent(
            siaStateRequestType,
            areaStates,
            siaBlock.message,
            siaBlock.needsAcknowledge(),
            siaBlock.getTotalLength()
        )
    }

}

