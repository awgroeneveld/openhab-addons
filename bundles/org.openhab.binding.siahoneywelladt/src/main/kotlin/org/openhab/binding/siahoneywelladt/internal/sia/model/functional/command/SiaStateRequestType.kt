package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command

import org.openhab.binding.siahoneywelladt.internal.sia.handler.event.AllAreasStateDecoder
import org.openhab.binding.siahoneywelladt.internal.sia.handler.event.AllZonesStateDecoder
import org.openhab.binding.siahoneywelladt.internal.sia.handler.event.DummyStateRequestDecoder
import org.openhab.binding.siahoneywelladt.internal.sia.handler.event.SiaStateRequestDecoder
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.state.AreaAlarmState
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.state.AreaArmedState
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.state.AreaReadyState
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.state.ZoneReadyState
import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaFunction

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
        decoder = AllAreasStateDecoder<AreaArmedState> { siaValue ->
            AreaArmedState.getState(
                siaValue
            )
        }),
    ALL_AREAS_ALARM_STATE(
        "SA91",
        SiaCommandSubjectType.AREA,
        SiaFunction.CONTROL,
        decoder = AllAreasStateDecoder<AreaAlarmState> { siaValue ->
            AreaAlarmState.getState(
                siaValue
            )
        }),
    ALL_AREAS_READY_STATE(
        "SA92",
        type = SiaCommandSubjectType.AREA,
        function = SiaFunction.CONTROL,
        decoder = AllAreasStateDecoder<AreaReadyState> { siaValue ->
            AreaReadyState.getState(
                siaValue
            )
        }
    ),
    ZONE_OMIT_STATE(
        code = "SB",
        type = SiaCommandSubjectType.ZONE,
        function = SiaFunction.CONTROL
    ),
    ZONE_TECHNICAL_STATE("ZS",
        SiaCommandSubjectType.ZONE,
        SiaFunction.EXTENDED
    ),
    ALL_ZONES_READY_STATE(
        "ZS",
        type = SiaCommandSubjectType.ZONE,
        function = SiaFunction.EXTENDED,
        instances = 2,
        returnStrings = arrayOf("ZS1*", "ZS2*"),
        decoder = AllZonesStateDecoder<ZoneReadyState> { siaValue ->
            ZoneReadyState.getState(
                siaValue
            )
        }
    ),
    ALL_ZONES_ALARM_STATE("ZS10",
        SiaCommandSubjectType.ZONE,
        SiaFunction.EXTENDED, 2),
    ALL_ZONES_OPEN_STATE("ZS20",
        SiaCommandSubjectType.ZONE,
        SiaFunction.EXTENDED, 2),
    ALL_ZONES_TAMPER_STATE("ZS30",
        SiaCommandSubjectType.ZONE,
        SiaFunction.EXTENDED, 2),
    ALL_ZONES_RESISTANCE_STATE("ZS40",
        SiaCommandSubjectType.ZONE,
        SiaFunction.EXTENDED, 2),
    ALL_ZONES_OMITTED_STATE("ZS50",
        SiaCommandSubjectType.ZONE,
        SiaFunction.EXTENDED, 2),
    ALL_ZONES_MASKED_STATE("ZS60",
        SiaCommandSubjectType.ZONE,
        SiaFunction.EXTENDED, 2),
    ALL_ZONES_FAULT_STATE("ZS70",
        SiaCommandSubjectType.ZONE,
        SiaFunction.EXTENDED, 2);

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
