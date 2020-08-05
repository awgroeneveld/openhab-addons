package org.openhab.binding.siahoneywelladt.internal.model.command

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.openhab.binding.siahoneywelladt.internal.handler.SharedConfig
import org.openhab.binding.siahoneywelladt.internal.model.SiaFunction
import org.openhab.binding.siahoneywelladt.internal.model.Zone
import org.openhab.binding.siahoneywelladt.internal.model.command.state.ZoneTechnicalStateCommand

class ZoneTechnicalStateCommandTest : StringSpec({

    "Zone technical state command should not be able to run for all zones" {
        val command=
            ZoneTechnicalStateCommand(
                listOf(Zone(3, 2, 5))
            )
        val siaBlocks=command.getSiaBlocks()
        siaBlocks.size shouldBe 1
        siaBlocks[0].let{
            it.function shouldBe SiaFunction.EXTENDED
            it.message.toString(SharedConfig.CHARACTER_SET) shouldBe "ZS3025"
            it.header.messageSize shouldBe 6
            it.toByteArray().size shouldBe 9
        }
    }
})


