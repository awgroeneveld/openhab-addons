package org.openhab.binding.siahoneywelladt.internal.handler

import org.openhab.binding.siahoneywelladt.internal.handler.Constants.EVENT_DECODER_SIZE
import org.openhab.binding.siahoneywelladt.internal.model.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.model.SiaFunction
import org.openhab.binding.siahoneywelladt.internal.support.LoggerDelegate

class SiaEvent()

interface SiaEventListener{
    fun handleEvent(siaEvent: SiaEvent)
}

class SiaEventBlock(){
// ..#0|
//00000040  30 31 32 33 34 1e db 4f  74 69 31 32 3a 35 31 2f  |01234..Oti12:51/|
//00000050  72 69 30 31 2f 69 64 39  39 39 2f 70 69 30 31 37  |ri01/id999/pi017|
//00000060  2f 43 47 4e d1 41 20 44  45 45 4c 42 2e 49 4e 47  |/CGN.A DEELB.ING|
//00000070  20 52 45 4d 4f 54 45 4f  c6 40 41 4c 34 42 31 35  | REMOTE
}

class EventDecoder(val siaEventListener:SiaEventListener) {
    private val logger by LoggerDelegate()
    private val buffer=ByteArray(EVENT_DECODER_SIZE)
    private var size=0


    fun decodeFunctionsAndReturnLastCorrectEndIndex():Int{
        val controlCommandsByIndex= findControlCommandsByIndex()
        var lastCorrectIndex=0
        controlCommandsByIndex.forEach{
            val siaBlock=getSiaBlock(it.key, it.value)
            if (siaBlock!=null) {
                lastCorrectIndex=it.key+siaBlock.header.totalSize
            }
        }
        return lastCorrectIndex
    }

    private fun getSiaBlock(index: Int, value: SiaFunction): SiaBlock? {

    }

    private fun findControlCommandsByIndex(): Map<Int, SiaFunction> {
        return buffer.mapIndexed { index, it ->
            val function = SiaFunction.getFunction(it.toInt())
            if (function != null && index>0)
                index-1 to function
            else null
        }
            .filterNotNull()
            .toMap()
    }

    fun decode(newBytes:ByteArray){
        if (newBytes.isEmpty())
            return
        if(size+newBytes.size<buffer.size){
            logger.error("Buffer overrun")

        }
        newBytes.copyInto(buffer, size)
        size+=newBytes.size

        val dropSize=decodeFunctionsAndReturnLastCorrectEndIndex()
        buffer.drop(dropSize)
        size-=dropSize
    }
}
