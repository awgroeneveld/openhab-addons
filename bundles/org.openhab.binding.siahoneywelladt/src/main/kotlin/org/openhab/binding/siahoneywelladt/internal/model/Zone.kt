package org.openhab.binding.siahoneywelladt.internal.model

data class Zone(val line: Int, val rio: Int, val connection: Int) {
    val identifier: Int

    init {
        require(line in 1..4)
        require(rio in 0..15)
        require(connection in 1..8)
        identifier = 1000 * line + rio * 10 + connection
    }
}
