package org.openhab.binding.siahoneywelladt.internal.model.command

import org.openhab.binding.siahoneywelladt.internal.model.AreaReadyState
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

    val logger by LoggerDelegate()
    override fun decode(siaBlock: SiaBlock): SiaConfigurationEvent? {
        val areaStates = siaBlock.message
            .drop(5)
            .map { it - '0'.toByte() }
            .mapIndexed { index, state -> getArea(index) to AreaReadyState.getAreaReadyState(state) }
            .toMap()
        return null
    }

    private fun getArea(index: Int): Any {
        val letter = 'A' + index / 8
        val nr = index % 8
        return "$letter$nr"
    }

}
