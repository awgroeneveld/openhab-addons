package org.openhab.binding.siahoneywelladt.internal.model.command

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SharedConfig
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.Area
import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaFunction
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.state.AreaArmedStateCommand

class AreaArmedStateCommandTest : StringSpec({

    "Area armed state command should be able to run for all areas" {
        val command=
            AreaArmedStateCommand()
        val siaBlocks=command.getSiaBlocks()
        siaBlocks.size shouldBe 1
        siaBlocks[0].let{
            it.function shouldBe SiaFunction.CONTROL
            it.message.toString(SharedConfig.CHARACTER_SET) shouldBe "SA"
            it.header.messageSize shouldBe 2
            it.toByteArray().size shouldBe 5
        }
    }

    "Area armed state command should be able to run on single areas" {
        val command=
            AreaArmedStateCommand(
                listOf(
                    Area(
                        'C',
                        1
                    )
                )
            )
        val siaBlocks=command.getSiaBlocks()
        siaBlocks.size shouldBe 1
        siaBlocks[0].let{
            it.function shouldBe SiaFunction.CONTROL
            it.message.toString(SharedConfig.CHARACTER_SET) shouldBe "SA17"
            it.header.messageSize shouldBe 4
            it.toByteArray().size shouldBe 7
        }
    }
})
