package org.openhab.binding.siahoneywelladt.internal.model.command

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SharedConfig
import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaFunction
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.state.AllAreasAlarmStateCommand


class AreaAlarmStateCommandTest : StringSpec({

    "Area alarm state command should be able to run for all areas" {
        val command=
            AllAreasAlarmStateCommand()
        val siaBlocks=command.getSiaBlocks()
        siaBlocks.size shouldBe 1
        siaBlocks[0].let{
            it.function shouldBe SiaFunction.CONTROL
            it.message.toString(SharedConfig.CHARACTER_SET) shouldBe "SA91"
            it.header.messageSize shouldBe 4
            it.toByteArray().size shouldBe 7
        }
    }
})

