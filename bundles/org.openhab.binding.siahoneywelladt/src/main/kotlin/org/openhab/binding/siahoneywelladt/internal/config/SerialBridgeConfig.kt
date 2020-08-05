package org.openhab.binding.siahoneywelladt.internal.config

import org.eclipse.jdt.annotation.NonNullByDefault
import org.eclipse.smarthome.core.thing.ThingStatusDetail
import org.openhab.binding.siahoneywelladt.internal.handler.ThingStatusException


@NonNullByDefault
class SerialBridgeConfig {
    companion object {
        val allowedBitRates = listOf(300, 600, 1200, 2400, 4800, 9600, 19200, 38400, 57600)
    }

    var serialPort = ""
    var bitrate = 57600
    var discovery = false
    var password = ""
    var dipSwitch8 = false
    var pollPeriod = 5

    fun isValidBitRate() = allowedBitRates.contains(bitrate)
    fun isValidSerialPort() = serialPort.isNotBlank()
    fun isValidPassword() = password.isNotBlank()

    fun validate() {
        fun throwInvalidConfiguration(message: String) {
            throw ThingStatusException(ThingStatusDetail.CONFIGURATION_ERROR, message, Throwable())
        }
        if (!isValidSerialPort())
            throwInvalidConfiguration("no serial port configured")

        if (!isValidBitRate())
            throwInvalidConfiguration("bit rate is not valid")

        if (!isValidPassword()) {
            throwInvalidConfiguration("password not set")
        }
    }

}

