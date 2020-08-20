package org.openhab.binding.siahoneywelladt.internal.handler

import io.kotest.core.spec.style.StringSpec
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.event.SiaEvent
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command.SiaCommand
import org.openhab.binding.siahoneywelladt.internal.sia.handler.comms.BufferHandler
import org.openhab.binding.siahoneywelladt.internal.sia.handler.event.ConfigurationEventDecoder
import org.openhab.binding.siahoneywelladt.internal.sia.handler.event.RegularEventDecoder
import org.openhab.binding.siahoneywelladt.internal.sia.handler.comms.SiaCommandTransmitter
import org.slf4j.LoggerFactory

class EventDecoderTest : StringSpec({
    "Area action command should be able to run for all areas" {
        val logger = LoggerFactory.getLogger(EventDecoderTest::class.java)
        val file = this::class.java.getResourceAsStream("/serialin-3.bin")
        val eventDecoder =
            BufferHandler(eventListener = object :
                    SiaEventListener {
                    override fun handleEvent(siaEvent: SiaEvent) {
                        logger.info("Handling event: $siaEvent")
                    }
                },
                commandTransmitter = object :
                    SiaCommandTransmitter {
                    override fun transmit(command: SiaCommand) {
                        logger.info("Transmitting command: $command")
                    }
                },
                configurationEventDecoder = ConfigurationEventDecoder(),
                regularEventDecoder = RegularEventDecoder()
            )
        val buffer = ByteArray(255)
        var bytesRead = file.read(buffer)
        while (bytesRead > 0) {
            eventDecoder.decode(buffer, bytesRead)
            bytesRead = file.read(buffer)
        }
        file.close()
    }

})

