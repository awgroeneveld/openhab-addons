package org.openhab.binding.siahoneywelladt.internal.model.command

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.openhab.binding.siahoneywelladt.internal.model.SiaFunction
import org.openhab.binding.siahoneywelladt.internal.model.command.state.AllZonesReadyStateCommand

class ZoneReadyStateCommandTest : StringSpec({

    "Zone ready state command should not be able to run for all zones" {
        val command =
            AllZonesReadyStateCommand()
        val siaBlocks = command.getSiaBlocks()
        siaBlocks.size shouldBe 2
        siaBlocks[0].let {
            it.function shouldBe SiaFunction.EXTENDED
            it.message.toString(SharedConfig.CHARACTER_SET) shouldBe "ZS1"
            it.header.messageSize shouldBe 3
            it.toByteArray().size shouldBe 6
        }
        siaBlocks[1].let {
            it.function shouldBe SiaFunction.EXTENDED
            it.message.toString(SharedConfig.CHARACTER_SET) shouldBe "ZS2"
            it.header.messageSize shouldBe 3
            it.toByteArray().size shouldBe 6
        }
    }
})

