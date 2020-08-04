package org.openhab.binding.siahoneywelladt.internal.model.command

import org.openhab.binding.siahoneywelladt.internal.model.Area
import org.openhab.binding.siahoneywelladt.internal.model.AreaAction
import org.openhab.binding.siahoneywelladt.internal.model.AreaArmedState
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
    AREA_ACTION("SA",  SiaCommandSubjectType.AREA, SiaFunction.CONTROL)

}
enum class SiaStateRequestType(
    val value: String,
    val type: SiaCommandSubjectType,
    val function: SiaFunction
) {
    AREA_ARMED_STATE("SA", SiaCommandSubjectType.AREA, SiaFunction.CONTROL),
    AREA_ALARM_STATE("SA91", SiaCommandSubjectType.AREA, SiaFunction.CONTROL),
    AREA_READY_STATE("SA92", SiaCommandSubjectType.AREA, SiaFunction.CONTROL)
}


abstract class SiaCommand(private val function: SiaFunction) {
    companion object{
        val charset=Charsets.ISO_8859_1
    }
    abstract fun getSiaBlocks(): List<SiaBlock>
    protected fun createSiaBlockFromMessage(message: String)= SiaBlock(function, message.toByteArray(charset))


}

abstract class SiaActionCommand(val type: SiaCommandType):SiaCommand(type.function){
    protected fun createSiaBlock(identifier: Int, action: SiaAction)=
        createSiaBlockFromMessage("${type.value}${identifier}*${action.siaAction}")
    protected fun createSiaBlock( action: SiaAction)=
        createSiaBlockFromMessage("${type.value}*${action.siaAction}")

}

abstract class SiaStateRequestCommand(val type: SiaStateRequestType):SiaCommand(type.function){
    protected fun createSiaBlock(state: SiaState)=
        createSiaBlockFromMessage("${type.value}${state}")
    protected fun createSiaBlock(identifier: Int)=
        createSiaBlockFromMessage("${type.value}${identifier}")
    protected fun createSiaBlock()=
        createSiaBlockFromMessage(type.value)
}

//
//class LoginCommand(val password: String):SiaCommand(){
//
//}

class AreaActionCommand(private val action: AreaAction, private val areas:List<Area> = listOf()) :
    SiaActionCommand(SiaCommandType.AREA_ACTION) {

    override fun getSiaBlocks(): List<SiaBlock> {
        return if (areas.isEmpty())
            listOf(createSiaBlock(action))
        else
            areas.map{createSiaBlock(it.identifier,action)}
    }
}

class AreaArmedStateCommand(private val areas:List<Area> = listOf()):
    SiaStateRequestCommand(SiaStateRequestType.AREA_ARMED_STATE){
    override fun getSiaBlocks(): List<SiaBlock> {
        return if (areas.isEmpty())
            listOf(createSiaBlock())
        else
            areas.map{createSiaBlock(it.identifier)}
    }
}

class AreaAlarmStateCommand:
    SiaStateRequestCommand(SiaStateRequestType.AREA_ALARM_STATE){
    override fun getSiaBlocks(): List<SiaBlock> =
        listOf(createSiaBlock())
}

class AreaReadyStateCommand:
    SiaStateRequestCommand(SiaStateRequestType.AREA_READY_STATE){
    override fun getSiaBlocks(): List<SiaBlock> =
        listOf(createSiaBlock())
}


/*
class AreaAlarmStateCommand():
        SiaCommand(SiaFunction.CONTROL, SiaCommandType.AREA){
    override fun getSiaBlocks(): List<SiaBlock> =
            listOf(createSiaBlock("SA91"))
}

class AreaReadyStateCommmand(): SiaCommand(SiaFunction.CONTROL, SiaCommandType.AREA){
    override fun getSiaBlocks(): List<SiaBlock> =
        listOf(createSiaBlock("SA92"))
}

class ZoneActionCommand(private val action: AreaAction, private val areas:List<Zone> = listOf()) :
    SiaCommand(SiaFunction.CONTROL, SiaCommandType.ZONE) {

    override fun getSiaBlocks(): List<SiaBlock> {
        return if (areas.isEmpty())
            listOf(createSiaBlock("SA*${action.siaValue}"))
        else
            areas.map{createSiaBlock("SA${it.identifier}*${action.siaValue}")}
    }
}


class SiaBlockFactory(){
    companion object{
//        fun createSiaBlocks(siaFunction: SiaFunction, siaCommandType: SiaCommandType, commandCode: String){
//            return SiaBlock(siaFunction, )
//        }
    }
}
*/
