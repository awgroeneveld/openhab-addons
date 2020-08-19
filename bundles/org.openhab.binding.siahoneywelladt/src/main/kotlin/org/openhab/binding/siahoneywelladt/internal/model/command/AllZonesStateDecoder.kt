package org.openhab.binding.siahoneywelladt.internal.model.command

import org.openhab.binding.siahoneywelladt.internal.model.Rio
import org.openhab.binding.siahoneywelladt.internal.model.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.model.SiaConfigurationEvent
import org.openhab.binding.siahoneywelladt.internal.model.SiaZoneConfigurationEvent
import org.openhab.binding.siahoneywelladt.internal.model.Zone

class AllZonesStateDecoder<X : Enum<*>>(private val toEnum: (Int) -> X?) :
    SiaStateRequestDecoder {

    fun byteToZoneMessage(byteNumber: Int, value: Int): List<Pair<Zone, X>> {
        val rio = byteToRio(byteNumber)
        return rio?.zones?.mapIndexed { index, it -> it to toEnum((value shr index) % 2)!! }
            ?: listOf()
    }

    fun byteToRio(byteNumber: Int): Rio? =
        when (byteNumber) {
            0 -> Rio(1, 0)
            1 -> Rio(1, 1)
            in 4..33 -> {
                val rioSequence = byteNumber - 2
                Rio(
                    1 + rioSequence / 16,
                    rioSequence % 16
                )
            }
            in 35..66 -> {
                val rioSequence = byteNumber - 3
                Rio(
                    1 + rioSequence / 16,
                    rioSequence % 16
                )
            }
            else -> null
        }

    override fun decode(siaBlock: SiaBlock, siaStateRequestType: SiaStateRequestType): SiaConfigurationEvent? {
        val messageNumber = siaBlock.messageAsString.substring(
            siaStateRequestType.messageHeaderLength - 2,
            siaStateRequestType.messageHeaderLength - 1
        )
            .toInt()
        val byteOffset = (messageNumber - 1) * 35
        val zoneStateAssignments = siaBlock.message.drop(siaStateRequestType.messageHeaderLength)
            .mapIndexed { index, byte -> byteToZoneMessage(index + byteOffset, byte.toInt() and 0xff) }
            .flatten()
            .toMap()
        return SiaZoneConfigurationEvent(
            siaStateRequestType,
            zoneStateAssignments,
            siaBlock.message,
            siaBlock.needsAcknowledge(),
            siaBlock.getTotalLength()
        )
    }

}
