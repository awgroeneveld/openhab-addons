package org.openhab.binding.siahoneywelladt.internal.model

class Rio(val lineNumber: Int, val rioNumber: Int) {
    val zones = (1..8).map { Zone(lineNumber, rioNumber, it) }

    init {
        require(lineNumber in 0..4) { "Line number should be between 0 and 4." }
        require(rioNumber in 0..15) { "Rio number should be between 0 and 15." }
    }

    constructor(identifier: Int) : this(identifier / 100, (identifier) % 100)


}
