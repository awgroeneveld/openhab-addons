package org.openhab.binding.siahoneywelladt.internal.model

class SiaBlockHeader(val messageSize: Int, val needsAcknowledge: Boolean, val reverseChannelEnabled: Boolean) {
    companion object {
        fun fromHeaderByte(byte: Byte): SiaBlockHeader {
            val byteAsInt = byte.toInt() and 0xFF
            val messageSize = byteAsInt and 63
            val needsAcknowledge = (byteAsInt and 64) == 64
            val reverseChannelEnabled = (byteAsInt and 128) == 128
            return SiaBlockHeader(
                messageSize,
                needsAcknowledge,
                reverseChannelEnabled
            )
        }
    }

    val value = (messageSize +
            (if (needsAcknowledge) 64 else 0) +
            (if (reverseChannelEnabled) 128 else 0))
    val totalSize = messageSize + 3
}
