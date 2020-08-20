package org.openhab.binding.siahoneywelladt.internal.model.technical

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaBlock
import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaBlockHeader
import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaFunction

class SiaBlockHeaderTest : StringSpec({

    "Round trips should be OK" {
        forAll(
            row(SiaFunction.CONTROL, 10, false),
            row(SiaFunction.CONTROL, 0, false),
            row(SiaFunction.ENVIRONMENTAL, 60, true)
        ) { siaFunction, messageLength, shouldReverse ->
            val x = SiaBlock(
                siaFunction,
                ByteArray(messageLength),
                shouldReverse
            )
            val y = SiaBlockHeader.fromHeaderByte(x.header.value.toByte())
            x.header.value shouldBe y.value
        }
    }

})
