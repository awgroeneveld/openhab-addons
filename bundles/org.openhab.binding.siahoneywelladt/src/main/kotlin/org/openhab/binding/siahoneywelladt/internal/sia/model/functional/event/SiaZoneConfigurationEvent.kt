package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.event

import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.SiaStateRequestType
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.Zone

class SiaZoneConfigurationEvent<T>(
    val requestType: SiaStateRequestType,
    val valuesByZone: Map<Zone, T>,
    message: ByteArray,
    needsAcknowledge: Boolean,
    size: Int
) :
    SiaConfigurationEvent(message, needsAcknowledge, size) {
    override fun toString(): String {
        val zoneInfo = valuesByZone.map { "\t${it.key}: ${it.value}" }
            .joinToString("\n")
        return "Zone Configuration event: ${requestType.name}(${requestType.code}), valuesByArea:\n$zoneInfo"
    }
}
