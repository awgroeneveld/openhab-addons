package org.openhab.binding.siahoneywelladt.config

import org.eclipse.jdt.annotation.NonNullByDefault


@NonNullByDefault
class SerialBridgeConfig {
    var serialPort = ""
    var bitrate = 115200
    var discovery = false
}
