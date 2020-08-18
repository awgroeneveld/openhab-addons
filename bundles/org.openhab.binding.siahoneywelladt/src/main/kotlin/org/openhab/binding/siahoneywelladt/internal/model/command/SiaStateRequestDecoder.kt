package org.openhab.binding.siahoneywelladt.internal.model.command

import org.openhab.binding.siahoneywelladt.internal.model.Area
import org.openhab.binding.siahoneywelladt.internal.model.AreaReadyState
import org.openhab.binding.siahoneywelladt.internal.model.Rio
import org.openhab.binding.siahoneywelladt.internal.model.SiaAreaConfigurationEvent
import org.openhab.binding.siahoneywelladt.internal.model.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.model.SiaConfigurationEvent
import org.openhab.binding.siahoneywelladt.internal.model.SiaZoneConfigurationEvent
import org.openhab.binding.siahoneywelladt.internal.model.Zone
import org.openhab.binding.siahoneywelladt.internal.model.ZoneReadyState
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

class AllZonesReadyStateDecoder private constructor() : SiaStateRequestDecoder {
    companion object {
        val instance = AllZonesReadyStateDecoder()
    }

    fun byteToZoneMessage(byteNumber: Int, value: Int): List<Pair<Zone, ZoneReadyState>> {
        val rio = byteToRio(byteNumber)
        return rio?.zones?.mapIndexed { index, it -> it to ZoneReadyState.getZoneReadyState((value shr index) % 2)!! }
            ?: listOf()
    }

    fun byteToRio(byteNumber: Int): Rio? =
        when (byteNumber) {
            0 -> Rio(1, 0)
            1 -> Rio(1, 1)
            in 4..33 -> {
                val rioSequence = byteNumber - 2
                Rio(1 + rioSequence / 16, rioSequence % 16)
            }
            in 35..66 -> {
                val rioSequence = byteNumber - 3
                Rio(1 + rioSequence / 16, rioSequence % 16)
            }
            else -> null
        }


    override fun decode(siaBlock: SiaBlock): SiaConfigurationEvent? {
        val messageNumber = siaBlock.messageAsString.substring(2, 3)
            .toInt()
        val byteOffset = (messageNumber - 1) * 35
        val zoneStateAssignments = siaBlock.message.drop(4)
            .mapIndexed { index, byte -> byteToZoneMessage(index + byteOffset, byte.toInt() and 0xff) }
            .flatten()
            .toMap()
        return SiaZoneConfigurationEvent<ZoneReadyState>(
            SiaStateRequestType.ALL_ZONES_READY_STATE,
            zoneStateAssignments,
            siaBlock.message,
            siaBlock.needsAcknowledge(),
            siaBlock.getTotalLength()
        )
    }

}
