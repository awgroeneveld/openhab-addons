package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command

import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.state.SiaState

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
