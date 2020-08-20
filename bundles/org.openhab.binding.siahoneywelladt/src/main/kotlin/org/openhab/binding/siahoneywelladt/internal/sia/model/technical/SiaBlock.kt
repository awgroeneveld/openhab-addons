package org.openhab.binding.siahoneywelladt.internal.sia.model.technical

class SiaBlock(val function: SiaFunction, val header: SiaBlockHeader, val message: ByteArray) {
    companion object {
        const val blockOverhead = 3
    }

    constructor(function: SiaFunction, message: ByteArray, reverseChannelEnabled: Boolean = false) :
            this(
                function,
                SiaBlockHeader(
                    message.size,
                    function.needsAcknowledge,
                    reverseChannelEnabled
                ), message
            )


    val messageAsString = message.toString(SharedConfig.CHARACTER_SET)

    fun checksum(): Byte {
        var parity = 0xFF xor header.value xor function.code
        message.forEach { parity = parity xor (it.toInt() and 0xFF) }
        return parity.toByte()
    }

    fun toByteArray(): ByteArray {
        val size = message.size + blockOverhead
        val result = ByteArray(size)
        result[0] = header.value.toByte()
        result[1] = function.code.toByte()
        message.copyInto(result, 2)
        result[size - 1] = checksum()
        return result
    }

    fun getTotalLength() = message.size + blockOverhead

    fun needsAcknowledge() = function.needsAcknowledge

    override fun toString(): String {
        return "SIA Block, function: ${function.name}, message: ${message.toString(SharedConfig.CHARACTER_SET)}"
    }
}
