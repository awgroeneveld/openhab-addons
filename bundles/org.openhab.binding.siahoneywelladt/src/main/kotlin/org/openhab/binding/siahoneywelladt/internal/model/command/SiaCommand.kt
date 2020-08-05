package org.openhab.binding.siahoneywelladt.internal.model.command

import org.openhab.binding.siahoneywelladt.internal.model.SiaAction
import org.openhab.binding.siahoneywelladt.internal.model.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.model.SiaFunction
import org.openhab.binding.siahoneywelladt.internal.model.SiaState


enum class SiaCommandSubjectType {
    AREA, ZONE
}

enum class SiaCommandType(
    val value: String,
    val type: SiaCommandSubjectType,
    val function: SiaFunction
) {
    AREA_ACTION("SA", SiaCommandSubjectType.AREA, SiaFunction.CONTROL),
    ZONE_ACTION("SB", SiaCommandSubjectType.ZONE, SiaFunction.CONTROL)
}

enum class SiaStateRequestType(
    val value: String,
    val type: SiaCommandSubjectType,
    val function: SiaFunction,
    val instances: Int = 1
) {
    AREA_ARMED_STATE("SA", SiaCommandSubjectType.AREA, SiaFunction.CONTROL),
    ALL_AREAS_ALARM_STATE("SA91", SiaCommandSubjectType.AREA, SiaFunction.CONTROL),
    ALL_AREAS_READY_STATE("SA92", SiaCommandSubjectType.AREA, SiaFunction.CONTROL),
    ZONE_OMIT_STATE("SB", SiaCommandSubjectType.ZONE, SiaFunction.CONTROL),
    ZONE_TECHNICAL_STATE("ZS", SiaCommandSubjectType.ZONE, SiaFunction.EXTENDED),
    ALL_ZONES_READY_STATE("ZS", SiaCommandSubjectType.ZONE, SiaFunction.EXTENDED, 2),
    ALL_ZONES_ALARM_STATE("ZS10", SiaCommandSubjectType.ZONE, SiaFunction.EXTENDED, 2),
    ALL_ZONES_OPEN_STATE("ZS20", SiaCommandSubjectType.ZONE, SiaFunction.EXTENDED, 2),
    ALL_ZONES_TAMPER_STATE("ZS30", SiaCommandSubjectType.ZONE, SiaFunction.EXTENDED, 2),
    ALL_ZONES_RESISTANCE_STATE("ZS40", SiaCommandSubjectType.ZONE, SiaFunction.EXTENDED, 2),
    ALL_ZONES_OMITTED_STATE("ZS50", SiaCommandSubjectType.ZONE, SiaFunction.EXTENDED, 2),
    ALL_ZONES_MASKED_STATE("ZS60", SiaCommandSubjectType.ZONE, SiaFunction.EXTENDED, 2),
    ALL_ZONES_FAULT_STATE("ZS70", SiaCommandSubjectType.ZONE, SiaFunction.EXTENDED, 2)
}


abstract class SiaCommand(private val function: SiaFunction) {
    companion object {
        val charset = Charsets.ISO_8859_1
    }

    abstract fun getSiaBlocks(): List<SiaBlock>
    protected fun createSiaBlockFromMessage(message: String) = SiaBlock(function, message.toByteArray(charset))
}

abstract class SiaActionCommand(val type: SiaCommandType) : SiaCommand(type.function) {
    protected fun createSiaBlock(identifier: Int, action: SiaAction) =
        createSiaBlockFromMessage("${type.value}${identifier}*${action.siaAction}")

    protected fun createSiaBlock(action: SiaAction) =
        createSiaBlockFromMessage("${type.value}*${action.siaAction}")
}

abstract class SiaStateRequestCommand(val type: SiaStateRequestType) : SiaCommand(type.function) {
    protected fun createSiaBlock(state: SiaState) =
        createSiaBlockFromMessage("${type.value}${state}")

    protected fun createSiaBlock(identifier: Int) =
        createSiaBlockFromMessage("${type.value}${identifier}")

    protected fun createSiaBlock() =
        createSiaBlockFromMessage(type.value)
}

abstract class SiaStateMultiRequestCommand(val type: SiaStateRequestType) : SiaCommand(type.function) {
    init {
        require(type.instances > 1) { "Should be at least two instances" }
    }

    private fun instances() = (1..type.instances)

    protected fun createSiaBlocks(state: SiaState) =
        instances().map { createSiaBlockFromMessage("${type.value}$it${state}") }

    protected fun createSiaBlocks(identifier: Int) =
        instances().map { createSiaBlockFromMessage("${type.value}$it${identifier}") }

    protected fun createSiaBlocks() =
        instances().map { createSiaBlockFromMessage("${type.value}$it") }
}


class LoginCommand(val password: String) : SiaCommand(SiaFunction.REMOTE_LOGIN) {
    override fun getSiaBlocks(): List<SiaBlock> {
        return listOf(createSiaBlockFromMessage(password))
    }
}

