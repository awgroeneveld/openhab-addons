package org.openhab.binding.siahoneywelladt.internal.model.command

import org.openhab.binding.siahoneywelladt.internal.model.Area
import org.openhab.binding.siahoneywelladt.internal.model.AreaReadyState
import org.openhab.binding.siahoneywelladt.internal.model.SiaAreaConfigurationEvent
import org.openhab.binding.siahoneywelladt.internal.model.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.model.SiaConfigurationEvent
import org.openhab.binding.siahoneywelladt.internal.support.LoggerDelegate

interface SiaStateRequestDecoder {
    fun decode(siaBlock: SiaBlock): SiaConfigurationEvent?
}

class DummyStateRequestDecoder private constructor() : SiaStateRequestDecoder {
    val logger by LoggerDelegate()

    companion object {
        val instance = DummyStateRequestDecoder()
    }

    override fun decode(siaBlock: SiaBlock): SiaConfigurationEvent? {
        logger.info("Dummy decoder")
        return null
    }


}

class AllAreasReadyStateDecoder private constructor() : SiaStateRequestDecoder {
    companion object {
        val instance = AllAreasReadyStateDecoder()
    }

    override fun decode(siaBlock: SiaBlock): SiaConfigurationEvent? {
        val areaStates = siaBlock.message
            .drop(5)
            .map { it - '0'.toByte() }
            .mapIndexed { index, state -> Area(index + 1) to AreaReadyState.getAreaReadyState(state) }
            .toMap()


        return SiaAreaConfigurationEvent(
            SiaStateRequestType.ALL_AREAS_READY_STATE,
            areaStates,
            siaBlock.message,
            siaBlock.needsAcknowledge(),
            siaBlock.getTotalLength()
        )
    }

}
