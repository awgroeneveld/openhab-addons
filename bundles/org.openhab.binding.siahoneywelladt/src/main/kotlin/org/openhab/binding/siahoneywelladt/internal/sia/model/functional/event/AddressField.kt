package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.event

enum class AddressField(val siaValue: Byte) {
    UNUSED(0),
    ZONE(1),
    AREA(2),
    USER(3),
    DOOR(4),
    DEALER_ID(5),
    EXPANDER(6),
    LINE(7),
    RELAY(8),
    POINT(9),
    PRINTER(10),
    MFR_DEFINED(11)
}
