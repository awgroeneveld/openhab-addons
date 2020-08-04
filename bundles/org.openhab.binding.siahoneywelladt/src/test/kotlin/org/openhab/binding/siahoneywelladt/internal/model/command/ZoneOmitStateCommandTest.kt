package org.openhab.binding.siahoneywelladt.internal.model.command

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.openhab.binding.siahoneywelladt.internal.model.SiaFunction
import org.openhab.binding.siahoneywelladt.internal.model.Zone
import org.openhab.binding.siahoneywelladt.internal.model.command.state.ZoneOmitStateCommand

class ZoneOmitStateCommandTest : StringSpec({

    "Area ready state command should be able to run for all areas" {
        val command=
            ZoneOmitStateCommand(
                listOf(Zone(1, 10, 2))
            )
        val siaBlocks=command.getSiaBlocks()
        siaBlocks.size shouldBe 1
        siaBlocks[0].let{
            it.function shouldBe SiaFunction.CONTROL
            it.message.toString(SharedConfig.CHARACTER_SET) shouldBe "SB1102"
            it.header.messageSize shouldBe 6
            it.toByteArray().size shouldBe 9
        }
    }
})
