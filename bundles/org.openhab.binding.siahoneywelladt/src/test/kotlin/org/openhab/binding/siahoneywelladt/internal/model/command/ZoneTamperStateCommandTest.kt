package org.openhab.binding.siahoneywelladt.internal.model.command

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.openhab.binding.siahoneywelladt.internal.model.SiaFunction
import org.openhab.binding.siahoneywelladt.internal.model.command.state.AllZonesTamperStateCommand

class ZoneTamperStateCommandTest : StringSpec({

    "Zone tamper state command should not be able to run for all zones" {
        val command =
            AllZonesTamperStateCommand()
        val siaBlocks = command.getSiaBlocks()
        siaBlocks.size shouldBe 2
        siaBlocks[0].let {
            it.function shouldBe SiaFunction.EXTENDED
            it.message.toString(SharedConfig.CHARACTER_SET) shouldBe "ZS301"
            it.header.messageSize shouldBe 5
            it.toByteArray().size shouldBe 8
        }
        siaBlocks[1].let {
            it.function shouldBe SiaFunction.EXTENDED
            it.message.toString(SharedConfig.CHARACTER_SET) shouldBe "ZS302"
            it.header.messageSize shouldBe 5
            it.toByteArray().size shouldBe 8
        }
    }
})
