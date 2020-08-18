package org.openhab.binding.siahoneywelladt.internal.model

import org.openhab.binding.siahoneywelladt.internal.handler.SharedConfig.CHARACTER_SET
import org.openhab.binding.siahoneywelladt.internal.model.command.SiaStateRequestType

abstract class SiaEvent(val message: ByteArray, val needsAcknowledge: Boolean, val size: Int)

class SiaRegularEvent(
    val eventType: SiaEventType,
    val metaData: SiaMetaData?,
    message: ByteArray,
    needsAcknowledge: Boolean,
    size: Int,
    val messageText: String? = null
) :
    SiaEvent(message, needsAcknowledge, size) {

    override fun toString(): String {
        return "SIA Event: ${eventType.name} (${eventType.code}), message: ${message.toString(CHARACTER_SET)}, messageText: $messageText, metaData:$metaData"
    }
}

abstract class SiaConfigurationEvent(message: ByteArray, needsAcknowledge: Boolean, size: Int) :
    SiaEvent(message, needsAcknowledge, size)


class PseudoSiaWaitForMoreEvent(val function: SiaFunction, message: ByteArray, needsAcknowledge: Boolean, size: Int) :
    SiaEvent(message, needsAcknowledge, size) {
    override fun toString() =
        "${this::class.simpleName} with function $function and message: ${message.toString(CHARACTER_SET)}"
}

class UnhandledSiaEvent(message: ByteArray, needsAcknowledge: Boolean, size: Int) :
    SiaEvent(message, needsAcknowledge, size)

class SiaNoMessageEvent(val function: SiaFunction, message: ByteArray, needsAcknowledge: Boolean, size: Int) :
    SiaConfigurationEvent(message, needsAcknowledge, size) {
    override fun toString(): String {
        return "Function: ${function.name}"
    }
}


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
