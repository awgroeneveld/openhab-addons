package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command

import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.action.SiaAction

abstract class SiaActionCommand(val type: SiaCommandType) : SiaCommand(type.function) {
    protected fun createSiaBlock(identifier: Int, action: SiaAction) =
        createSiaBlockFromMessage("${type.value}${identifier}*${action.siaAction}")

    protected fun createSiaBlock(action: SiaAction) =
        createSiaBlockFromMessage("${type.value}*${action.siaAction}")
}
