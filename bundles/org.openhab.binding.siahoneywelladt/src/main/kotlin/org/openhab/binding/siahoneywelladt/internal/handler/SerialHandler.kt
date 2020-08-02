package org.openhab.binding.siahoneywelladt.internal.handler

import org.eclipse.jdt.annotation.NonNullByDefault
import org.eclipse.jdt.annotation.Nullable
import org.eclipse.smarthome.core.thing.Bridge
import org.eclipse.smarthome.core.thing.ChannelUID
import org.eclipse.smarthome.core.thing.ThingStatus
import org.eclipse.smarthome.core.thing.ThingStatusDetail
import org.eclipse.smarthome.core.thing.binding.BaseBridgeHandler
import org.eclipse.smarthome.core.types.Command
import org.eclipse.smarthome.io.transport.serial.PortInUseException
import org.eclipse.smarthome.io.transport.serial.SerialPort
import org.eclipse.smarthome.io.transport.serial.SerialPortEvent
import org.eclipse.smarthome.io.transport.serial.SerialPortEventListener
import org.eclipse.smarthome.io.transport.serial.SerialPortIdentifier
import org.eclipse.smarthome.io.transport.serial.SerialPortManager
import org.eclipse.smarthome.io.transport.serial.UnsupportedCommOperationException
import org.openhab.binding.siahoneywelladt.config.SerialBridgeConfig
import org.openhab.binding.siahoneywelladt.internal.support.LoggerDelegate
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.nio.charset.Charset


class SerialHandler(bridge: Bridge, private val serialPortManager: SerialPortManager): BaseBridgeHandler(bridge),
    SerialPortEventListener {
    companion object {
        val charset= Charset.forName("ISO-8859-1")
    }
    val logger by LoggerDelegate()

    private var config = SerialBridgeConfig()
    @NonNullByDefault
    private var portIdentifier: SerialPortIdentifier? = null
    private var serialPort: @Nullable SerialPort? = null
    private var serialPortSpeed = 115200
    private var discovery:Boolean=false

    private var reader: @Nullable BufferedReader? = null
    private var writer: @Nullable BufferedWriter? = null

    private var msgReaderThread: @Nullable Thread? = null
    private val msgReaderThreadLock = Any()
    private var panelReadyReceived=false

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
                portIdentifier!!.open("org.openhab.binding.siahoneywelladt", 100)
            serialPort.setSerialPortParams(
                serialPortSpeed,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE
            )
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN or SerialPort.FLOWCONTROL_RTSCTS_OUT)
            serialPort = serialPort
            reader = BufferedReader(InputStreamReader(serialPort.inputStream, charset ))
            writer = BufferedWriter(OutputStreamWriter(serialPort.outputStream, charset))
            logger.debug("connected to serial port: {}", config.serialPort)
            panelReadyReceived = false
            startMsgReader()
            updateStatus(ThingStatus.ONLINE)
        } catch (e: PortInUseException) {
            logger.debug("Cannot open serial port: {}, it is already in use", config.serialPort)
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, "Serial port already in use")
        } catch (e: UnsupportedCommOperationException) {
            logger.debug("Error connecting to serial port: {}", e.message)
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.message)
        } catch (e: IOException) {
            logger.debug("Error connecting to serial port: {}", e.message)
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.message)
        } catch (e: IllegalStateException) {
            logger.debug("Error connecting to serial port: {}", e.message)
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.message)
        }
    }

    protected fun startMsgReader() {
        synchronized(msgReaderThreadLock) {
            val mrt = Thread(Runnable { readerThread() }, "AD Reader")
            mrt.isDaemon = true
            mrt.start()
            msgReaderThread = mrt
        }
    }

    protected fun stopMsgReader() {
        synchronized(msgReaderThreadLock) {
            val mrt: Thread? = msgReaderThread
            if (mrt != null) {
                logger.trace("Stopping reader thread.")
                mrt.interrupt()
                msgReaderThread = null
            }
        }
    }

    @Synchronized
    protected fun disconnect() {
        logger.trace("Disconnecting")
        val sp: SerialPort = serialPort!!
        if (sp != null) {
            logger.trace("Closing serial port")
            sp.close()
            serialPort = null
        }
        stopMsgReader()
        val br: BufferedReader? = reader
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
        val bw: BufferedWriter? = writer
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

    private fun readerThread() {
        logger.debug("Message reader thread started")
        var message: String? = null
        try {
            // Send version command to get device to respond with VER message.
//            sendADCommand(ADCommand.getVersion())
            val reader = reader
            while (!Thread.interrupted() && reader != null && reader.readLine()
                    .also { message = it } != null
            ) {
                logger.trace("Received msg: {}", message)

            }
            if (message == null) {
                logger.info("End of input stream detected")
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, "Connection lost")
            }
        } catch (e: IOException) {
            logger.debug("I/O error while reading from stream: {}", e.message)
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.message)
        } catch (e: RuntimeException) {
            logger.warn("Runtime exception in reader thread", e)
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.message)
        } finally {
            logger.debug("Message reader thread exiting")
        }
    }

    override fun serialEvent(event: SerialPortEvent) {
        val eventType=event.eventType
    }
}
