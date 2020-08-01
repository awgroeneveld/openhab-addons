package org.openhab.binding.siahoneywelladt.internal.handler

import org.eclipse.jdt.annotation.NonNullByDefault
import org.eclipse.jdt.annotation.Nullable
import org.eclipse.smarthome.core.thing.Bridge
import org.eclipse.smarthome.core.thing.ChannelUID
import org.eclipse.smarthome.core.thing.ThingStatus
import org.eclipse.smarthome.core.thing.ThingStatusDetail
import org.eclipse.smarthome.core.thing.binding.BaseBridgeHandler
import org.eclipse.smarthome.core.types.Command
import org.eclipse.smarthome.io.transport.serial.SerialPort
import org.eclipse.smarthome.io.transport.serial.SerialPortIdentifier
import org.eclipse.smarthome.io.transport.serial.SerialPortManager
import org.openhab.binding.siahoneywelladt.config.SerialBridgeConfig
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter


class SerialHandler(bridge: Bridge, private val serialPortManager: SerialPortManager): BaseBridgeHandler(bridge) {
    val logger=LoggerFactory.getLogger(SerialHandler::class.java)

    private var config = SerialBridgeConfig()
    @NonNullByDefault
    private var portIdentifier: SerialPortIdentifier? = null

    private var serialPort: @Nullable SerialPort? = null
    private var serialPortSpeed = 115200
    private var discovery:Boolean=false

    private var reader: @Nullable BufferedReader? = null
    private var writer: @Nullable BufferedWriter? = null


    override fun initialize() {
        logger.debug("Initializing serial bridge handler")
        config = getConfigAs(SerialBridgeConfig::class.java)
        discovery = config.discovery
        if (config.serialPort.isEmpty()) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, "no serial port configured")
            return
        }
        if (config.bitrate > 0) {
            serialPortSpeed = config.bitrate
        }
        val portIdentifier = serialPortManager.getIdentifier(config.serialPort)
        if (portIdentifier == null) {
            logger.debug("Serial Error: Port {} does not exist.", config.serialPort)
            updateStatus(
                ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                "Configured serial port does not exist"
            )
            return
        }
        connect()
        logger.trace("Finished initializing serial bridge handler")
    }


    @Synchronized
    protected fun connect() {
        disconnect() // make sure we are disconnected
        try {
            var serialPort: SerialPort =
                portIdentifier.open("org.openhab.binding.alarmdecoder", 100)
            serialPort.setSerialPortParams(
                serialPortSpeed,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE
            )
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN or SerialPort.FLOWCONTROL_RTSCTS_OUT)
            // Note: The V1 code called disableReceiveFraming() and disableReceiveThreshold() here
            serialPort = serialPort
            reader = BufferedReader(InputStreamReader(serialPort.inputStream, AD_CHARSET))
            writer = BufferedWriter(OutputStreamWriter(serialPort.outputStream, AD_CHARSET))
            logger.debug("connected to serial port: {}", config.serialPort)
            panelReadyReceived = false
            startMsgReader()
            updateStatus(ThingStatus.ONLINE)
        } catch (e: PortInUseException) {
            logger.debug("Cannot open serial port: {}, it is already in use", config.serialPort)
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, "Serial port already in use")
        } catch (e: UnsupportedCommOperationException) {
            logger.debug("Error connecting to serial port: {}", e.getMessage())
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.getMessage())
        } catch (e: IOException) {
            logger.debug("Error connecting to serial port: {}", e.getMessage())
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.getMessage())
        } catch (e: IllegalStateException) {
            logger.debug("Error connecting to serial port: {}", e.getMessage())
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.getMessage())
        }
    }

    @Synchronized
    protected fun disconnect() {
        logger.trace("Disconnecting")
        val sp: SerialPort = serialPort
        if (sp != null) {
            logger.trace("Closing serial port")
            sp.close()
            serialPort = null
        }
        stopMsgReader()
        val br: BufferedReader = reader
        if (br != null) {
            logger.trace("Closing reader")
            try {
                br.close()
            } catch (e: IOException) {
                logger.info("IO Exception closing reader: {}", e.message)
            } finally {
                reader = null
            }
        }
        val bw: BufferedWriter = writer
        if (bw != null) {
            logger.trace("Closing writer")
            try {
                bw.close()
            } catch (e: IOException) {
                logger.info("IO Exception closing writer: {}", e.message)
            } finally {
                writer = null
            }
        }
    }

    override fun handleCommand(p0: ChannelUID?, p1: Command?) {
        TODO("Not yet implemented")
    }
}
