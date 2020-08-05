package org.openhab.binding.siahoneywelladt.internal.model.command

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.openhab.binding.siahoneywelladt.internal.handler.SharedConfig
import org.openhab.binding.siahoneywelladt.internal.model.Area
import org.openhab.binding.siahoneywelladt.internal.model.AreaAction
import org.openhab.binding.siahoneywelladt.internal.model.SiaFunction
import org.openhab.binding.siahoneywelladt.internal.model.command.action.AreaActionCommand

class AreaActionCommandTest : StringSpec({

    "Area action command should be able to run for all areas" {
        val command=
            AreaActionCommand(
                AreaAction.SET
            )
        val siaBlocks=command.getSiaBlocks()
        siaBlocks.size shouldBe 1
        siaBlocks[0].let{
            it.function shouldBe SiaFunction.CONTROL
            it.message.toString(SharedConfig.CHARACTER_SET) shouldBe "SA*1"
            it.header.messageSize shouldBe 4
            it.toByteArray().size shouldBe 7
        }
    }

    "Area action command should be able to run on single areas" {
        val command=
            AreaActionCommand(
                AreaAction.SET,
                listOf(Area('B', 1))
            )
        val siaBlocks=command.getSiaBlocks()
        siaBlocks.size shouldBe 1
        siaBlocks[0].let{
            it.function shouldBe SiaFunction.CONTROL
            it.message.toString(SharedConfig.CHARACTER_SET) shouldBe "SA9*1"
            it.header.messageSize shouldBe 5
            it.toByteArray().size shouldBe 8
        }
    }
})

