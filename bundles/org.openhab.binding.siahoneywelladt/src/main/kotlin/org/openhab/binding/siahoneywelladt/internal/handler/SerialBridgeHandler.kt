package org.openhab.binding.siahoneywelladt.internal.handler

import org.eclipse.smarthome.core.thing.Bridge
import org.eclipse.smarthome.core.thing.ChannelUID
import org.eclipse.smarthome.core.thing.ThingStatus
import org.eclipse.smarthome.core.thing.ThingStatusDetail
import org.eclipse.smarthome.core.thing.binding.BaseBridgeHandler
import org.eclipse.smarthome.core.types.Command
import org.eclipse.smarthome.io.transport.serial.SerialPortManager
import org.openhab.binding.siahoneywelladt.internal.config.SerialBridgeConfig
import org.openhab.binding.siahoneywelladt.internal.support.LoggerDelegate

class SerialBridgeHandler(bridge: Bridge, private val serialPortManager: SerialPortManager) : BaseBridgeHandler(bridge),
    StatusUpdateListener {

    private val logger by LoggerDelegate()

    private var client: SerialCommunicationClient? = null
    private var discovery: Boolean = false

    override fun initialize() {
        logger.debug("Initializing serial bridge handler")
        val config = getConfigAs(SerialBridgeConfig::class.java)
        this.discovery = config.discovery

        try {
            config.validate()
            client = SerialCommunicationClient(serialPortManager, config, this)
            client!!.connect()
            updateStatus(ThingStatus.ONLINE)
        } catch (e: ThingStatusException) {
            logger.warn("Unknown Thing Status Exception {}.",e.message, e)
            updateStatus(ThingStatus.OFFLINE, e.detail, e.message)
        } catch (e: Exception) {
            logger.warn("Unexpected Exception {}.",e.message, e)
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.message)
            return
        }
    }


    override fun handleCommand(p0: ChannelUID?, p1: Command?) {
        TODO("Not yet implemented")
    }

    override fun handleStatusChange(status: ThingStatus, detail: ThingStatusDetail, message: String?) {
        super.updateStatus(status, detail, message)
    }


}
