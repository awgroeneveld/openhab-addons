package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.event

import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.SiaStateRequestType
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.Area

class SiaAreaConfigurationEvent<T>(
    val requestType: SiaStateRequestType,
    val valuesByArea: Map<Area, T>,
    message: ByteArray,
    needsAcknowledge: Boolean,
    size: Int
) :
    SiaConfigurationEvent(message, needsAcknowledge, size) {
    override fun toString(): String {
        val areaInfo = valuesByArea.map { "\t${it.key}: ${it.value}" }
            .joinToString("\n")
        return "Area Configuration event: ${requestType.name}(${requestType.code}), valuesByArea:\n$areaInfo"
    }
}
