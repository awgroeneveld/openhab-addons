package org.openhab.binding.siahoneywelladt.internal.handler

import io.kotest.core.spec.style.StringSpec
import org.openhab.binding.siahoneywelladt.internal.model.SiaEvent
import org.openhab.binding.siahoneywelladt.internal.model.command.SiaCommand
import org.slf4j.LoggerFactory

class EventDecoderTest : StringSpec({
    "Area action command should be able to run for all areas" {
        val logger = LoggerFactory.getLogger(EventDecoderTest::class.java)
        val file = this::class.java.getResourceAsStream("/serialin-1.dat")
        val eventDecoder = EventDecoder(eventListener = object : SiaEventListener {
            override fun handleEvent(siaEvent: SiaEvent) {
                logger.info("Handling event: $siaEvent")
            }
        }, commandTransmitter = object : SiaCommandTransmitter {
            override fun transmit(command: SiaCommand) {
                logger.info("Transmitting command: $command")
            }
        })
        val buffer = ByteArray(255)
        var bytesRead = file.read(buffer)
        while (bytesRead > 0) {
            eventDecoder.decode(buffer, bytesRead)
            bytesRead = file.read(buffer)
        }
        file.close()
    }

})

