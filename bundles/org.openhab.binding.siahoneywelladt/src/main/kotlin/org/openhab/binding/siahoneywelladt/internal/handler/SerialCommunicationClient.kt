package org.openhab.binding.siahoneywelladt.internal.handler

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.eclipse.jdt.annotation.Nullable
import org.eclipse.smarthome.core.thing.ThingStatus
import org.eclipse.smarthome.core.thing.ThingStatusDetail
import org.eclipse.smarthome.io.transport.serial.PortInUseException
import org.eclipse.smarthome.io.transport.serial.SerialPort
import org.eclipse.smarthome.io.transport.serial.SerialPortManager
import org.openhab.binding.siahoneywelladt.internal.config.SerialBridgeConfig
import org.openhab.binding.siahoneywelladt.internal.support.LoggerDelegate
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.nio.charset.Charset

class SerialCommunicationClient(
    private val serialPortManager: SerialPortManager,
    private val config: SerialBridgeConfig,
    private val statusUpdateListener: StatusUpdateListener
){
    private val logger by LoggerDelegate()
    private var readerJob: Job?=null
    private var serialPort: SerialPort?=null
    private val portIdentifier = serialPortManager.getIdentifier(config.serialPort)
    private var inputStream: InputStream?=null
    private var outputStream:OutputStream?=null

    @Synchronized
    fun connect() {
        disconnect() // make sure we are disconnected
        try {
            this.serialPort = createSerialPort()
            this.inputStream=serialPort!!.inputStream!!
            this.outputStream=serialPort!!.outputStream!!
            logger.debug("connected to serial port: {}", config.serialPort)
            this.readerJob=GlobalScope.launch(Dispatchers.IO){
                readSerialPortData()
            }
        } catch (e: PortInUseException) {
            throw Exception("Cannot open serial port: ${config.serialPort}, it is already in use")
        } catch (e: Exception) {
            throw(e)
        }
    }

    private fun createSerialPort(): SerialPort {
        if (portIdentifier==null)
            throw Exception("Configured serial port does not exist")
        val createdSerialPort = portIdentifier.open("org.openhab.binding.siahoneywelladt", 100)
        createdSerialPort.setSerialPortParams(
            config.bitrate,
            SerialPort.DATABITS_8,
            SerialPort.STOPBITS_1,
            SerialPort.PARITY_NONE
        )
        createdSerialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN or SerialPort.FLOWCONTROL_RTSCTS_OUT)
        return createdSerialPort
    }


    fun disconnect() {
        logger.trace("Disconnecting")
        this.readerJob!!.cancel()
        if (this.serialPort != null) {
            logger.trace("Closing serial port")
            this.serialPort!!.close()
            this.serialPort = null
        }
        if (this.inputStream != null) {
            logger.trace("Closing reader")
            try {
                this.inputStream!!.close()
            } catch (e: IOException) {
                logger.info("IO Exception closing reader: {}", e.message)
            } finally {
                this.inputStream = null
            }
        }
        if (this.outputStream != null) {
            logger.trace("Closing writer")
            try {
                this.outputStream!!.close()
            } catch (e: IOException) {
                logger.info("IO Exception closing writer: {}", e.message)
            } finally {
                this.outputStream = null
            }
        }
    }

    private suspend fun readSerialPortData() {
        try {
            // Send version command to get device to respond with VER message.
//            sendADCommand(ADCommand.getVersion())
            val inputStream = this.inputStream!!
            var ready=false
            while (inputStream != null && !ready){
                //TODO: read something
                val x=inputStream.read()
                delay(50)
            }
        } catch (e: IOException) {
            logger.error("I/O error while reading from stream: {}", e.message)
            statusUpdateListener.handleStatusChange(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.message)
        } catch (e: RuntimeException) {
            logger.error("Runtime exception in reader thread", e)
            statusUpdateListener.handleStatusChange(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.message)
        } finally {
            logger.error("Message reader thread exiting")
        }
    }



}
