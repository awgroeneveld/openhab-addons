package org.openhab.binding.siahoneywelladt.internal.model

enum class SiaMetaDataType(val code: String) {
    DATE("da"),
    TIME("ti"),
    SUBSCRIBER_ID("id"),
    AREA_ID("ri"),
    PERIPHERAL_ID("pi"),
    AUTOMATED_ID("ai"),
    TELEPHONE("ph"),
    PACKET_LEVEL("lv"),
    VALUE("va"),
    PATH("pt"),
    ROUTE_GROUP("rg"),
    SUB_SUBSCRIBER("ss");

    companion object {
        private val metaDataTypesByCode = values()
            .map { it.code to it }
            .toMap()

        fun getMetaDataType(code: String) = metaDataTypesByCode[code]
    }
}


