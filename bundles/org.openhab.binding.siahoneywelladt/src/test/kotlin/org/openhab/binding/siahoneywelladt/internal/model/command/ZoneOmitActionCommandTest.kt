package org.openhab.binding.siahoneywelladt.internal.model.command

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.openhab.binding.siahoneywelladt.internal.handler.SharedConfig
import org.openhab.binding.siahoneywelladt.internal.model.SiaFunction
import org.openhab.binding.siahoneywelladt.internal.model.Zone
import org.openhab.binding.siahoneywelladt.internal.model.ZoneAction
import org.openhab.binding.siahoneywelladt.internal.model.command.action.ZoneOmitActionCommand

class ZoneOmitActionCommandTest : StringSpec({
    "Zone action command should be able to run on single areas" {
        val command=
            ZoneOmitActionCommand(
                ZoneAction.OMIT,
                listOf(Zone(1, 10, 2))
            )
        val siaBlocks=command.getSiaBlocks()
        siaBlocks.size shouldBe 1
        siaBlocks[0].let{
            it.function shouldBe SiaFunction.CONTROL
            it.message.toString(SharedConfig.CHARACTER_SET) shouldBe "SB1102*1"
            it.header.messageSize shouldBe 8
            it.toByteArray().size shouldBe 11
        }
    }
})

