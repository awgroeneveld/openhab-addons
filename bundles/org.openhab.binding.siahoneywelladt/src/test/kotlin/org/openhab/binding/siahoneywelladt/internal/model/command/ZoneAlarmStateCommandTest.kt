package org.openhab.binding.siahoneywelladt.internal.model.command

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SharedConfig
import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaFunction
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.state.AllZonesAlarmStateCommand

class ZoneAlarmStateCommandTest : StringSpec({

    "Zone alarm state command should not be able to run for all zones" {
        val command =
            AllZonesAlarmStateCommand()
        val siaBlocks = command.getSiaBlocks()
        siaBlocks.size shouldBe 2
        siaBlocks[0].let {
            it.function shouldBe SiaFunction.EXTENDED
            it.message.toString(SharedConfig.CHARACTER_SET) shouldBe "ZS101"
            it.header.messageSize shouldBe 5
            it.toByteArray().size shouldBe 8
        }
        siaBlocks[1].let {
            it.function shouldBe SiaFunction.EXTENDED
            it.message.toString(SharedConfig.CHARACTER_SET) shouldBe "ZS102"
            it.header.messageSize shouldBe 5
            it.toByteArray().size shouldBe 8
        }
    }
})

