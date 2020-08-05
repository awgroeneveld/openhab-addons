package org.openhab.binding.siahoneywelladt.internal.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SiaBlockHeaderTest : StringSpec({

    "Round trips should be OK" {
        forAll(
            row(SiaFunction.CONTROL, 10, false),
            row(SiaFunction.CONTROL, 0, false),
            row(SiaFunction.ENVIRONMENTAL, 60, true)
        ) { siaFunction, messageLength, shouldReverse ->
            val x = SiaBlock(siaFunction, ByteArray(messageLength), shouldReverse)
            val y = SiaBlockHeader.fromHeaderByte(x.header.value.toByte())
            x.header.value shouldBe y.value
        }
    }

})
