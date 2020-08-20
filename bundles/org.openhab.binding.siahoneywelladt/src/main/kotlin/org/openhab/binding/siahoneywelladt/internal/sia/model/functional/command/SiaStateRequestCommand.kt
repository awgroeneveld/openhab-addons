package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command

import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.state.SiaState

abstract class SiaStateRequestCommand(val type: SiaStateRequestType) : SiaCommand(type.function) {
    protected fun createSiaBlock(state: SiaState) =
        createSiaBlockFromMessage("${type.code}${state}")

    protected fun createSiaBlock(identifier: Int) =
        createSiaBlockFromMessage("${type.code}${identifier}")

    protected fun createSiaBlock() =
        createSiaBlockFromMessage(type.code)
}
