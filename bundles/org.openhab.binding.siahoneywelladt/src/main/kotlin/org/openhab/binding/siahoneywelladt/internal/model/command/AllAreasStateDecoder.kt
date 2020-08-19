package org.openhab.binding.siahoneywelladt.internal.model.command

import org.openhab.binding.siahoneywelladt.internal.model.Area
import org.openhab.binding.siahoneywelladt.internal.model.SiaAreaConfigurationEvent
import org.openhab.binding.siahoneywelladt.internal.model.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.model.SiaConfigurationEvent

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

