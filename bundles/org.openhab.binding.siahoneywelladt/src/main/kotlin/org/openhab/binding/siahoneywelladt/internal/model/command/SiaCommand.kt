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
    val function: SiaFunction
) {
    AREA_ARMED_STATE("SA", SiaCommandSubjectType.AREA, SiaFunction.CONTROL),
    AREA_ALARM_STATE("SA91", SiaCommandSubjectType.AREA, SiaFunction.CONTROL),
    AREA_READY_STATE("SA92", SiaCommandSubjectType.AREA, SiaFunction.CONTROL),
    ZONE_OMIT_STATE("SB", SiaCommandSubjectType.ZONE, SiaFunction.CONTROL)
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

//
//class LoginCommand(val password: String):SiaCommand(){
//
//}


