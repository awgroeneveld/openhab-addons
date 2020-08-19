package org.openhab.binding.siahoneywelladt.internal.model.command

import org.openhab.binding.siahoneywelladt.internal.model.AreaAlarmState
import org.openhab.binding.siahoneywelladt.internal.model.AreaArmedState
import org.openhab.binding.siahoneywelladt.internal.model.AreaReadyState
import org.openhab.binding.siahoneywelladt.internal.model.SiaAction
import org.openhab.binding.siahoneywelladt.internal.model.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.model.SiaFunction
import org.openhab.binding.siahoneywelladt.internal.model.SiaState
import org.openhab.binding.siahoneywelladt.internal.model.ZoneReadyState


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
    val code: String,
    val type: SiaCommandSubjectType,
    val function: SiaFunction,
    val instances: Int = 1,
    val returnStrings: Array<String> = arrayOf("$code*"),
    val decoder: SiaStateRequestDecoder = DummyStateRequestDecoder.instance
) {
    ALL_AREAS_ARMED_STATE("SA",
        type = SiaCommandSubjectType.AREA,
        function = SiaFunction.CONTROL,
        decoder = AllAreasStateDecoder<AreaArmedState> { siaValue -> AreaArmedState.getState(siaValue) }),
    ALL_AREAS_ALARM_STATE(
        "SA91",
        SiaCommandSubjectType.AREA,
        SiaFunction.CONTROL,
        decoder = AllAreasStateDecoder<AreaAlarmState> { siaValue -> AreaAlarmState.getState(siaValue) }),
    ALL_AREAS_READY_STATE(
        "SA92",
        type = SiaCommandSubjectType.AREA,
        function = SiaFunction.CONTROL,
        decoder = AllAreasStateDecoder<AreaReadyState> { siaValue -> AreaReadyState.getState(siaValue) }
    ),
    ZONE_OMIT_STATE(
        code = "SB",
        type = SiaCommandSubjectType.ZONE,
        function = SiaFunction.CONTROL
    ),
    ZONE_TECHNICAL_STATE("ZS", SiaCommandSubjectType.ZONE, SiaFunction.EXTENDED),
    ALL_ZONES_READY_STATE(
        "ZS",
        type = SiaCommandSubjectType.ZONE,
        function = SiaFunction.EXTENDED,
        instances = 2,
        returnStrings = arrayOf("ZS1*", "ZS2*"),
        decoder = AllZonesStateDecoder<ZoneReadyState> { siaValue -> ZoneReadyState.getState(siaValue) }
    ),
    ALL_ZONES_ALARM_STATE("ZS10", SiaCommandSubjectType.ZONE, SiaFunction.EXTENDED, 2),
    ALL_ZONES_OPEN_STATE("ZS20", SiaCommandSubjectType.ZONE, SiaFunction.EXTENDED, 2),
    ALL_ZONES_TAMPER_STATE("ZS30", SiaCommandSubjectType.ZONE, SiaFunction.EXTENDED, 2),
    ALL_ZONES_RESISTANCE_STATE("ZS40", SiaCommandSubjectType.ZONE, SiaFunction.EXTENDED, 2),
    ALL_ZONES_OMITTED_STATE("ZS50", SiaCommandSubjectType.ZONE, SiaFunction.EXTENDED, 2),
    ALL_ZONES_MASKED_STATE("ZS60", SiaCommandSubjectType.ZONE, SiaFunction.EXTENDED, 2),
    ALL_ZONES_FAULT_STATE("ZS70", SiaCommandSubjectType.ZONE, SiaFunction.EXTENDED, 2);

    companion object {
        private val requestsByCode = values().associateBy { it.code }
        private val requestsByReturnString =
            values().flatMap { requestType -> requestType.returnStrings.map { it to requestType } }
                .toMap()

        fun getRequest(code: String) = requestsByCode[code]
        fun getRequestTypeByReturnString(returnString: String) =
            requestsByReturnString.entries.sortedByDescending { it.key.length }
                .firstOrNull { returnString.startsWith(it.key) }?.value

    }

    val messageHeaderLength = returnStrings.first().length
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
        createSiaBlockFromMessage("${type.code}${state}")

    protected fun createSiaBlock(identifier: Int) =
        createSiaBlockFromMessage("${type.code}${identifier}")

    protected fun createSiaBlock() =
        createSiaBlockFromMessage(type.code)
}

abstract class SiaStateMultiRequestCommand(val type: SiaStateRequestType) : SiaCommand(type.function) {
    init {
        require(type.instances > 1) { "Should be at least two instances" }
    }

    private fun instances() = (1..type.instances)

    protected fun createSiaBlocks(state: SiaState) =
        instances().map { createSiaBlockFromMessage("${type.code}$it${state}") }

    protected fun createSiaBlocks(identifier: Int) =
        instances().map { createSiaBlockFromMessage("${type.code}$it${identifier}") }

    protected fun createSiaBlocks() =
        instances().map { createSiaBlockFromMessage("${type.code}$it") }
}


class LoginCommand(val password: String) : SiaCommand(SiaFunction.REMOTE_LOGIN) {
    override fun getSiaBlocks(): List<SiaBlock> {
        return listOf(createSiaBlockFromMessage(password))
    }
}

