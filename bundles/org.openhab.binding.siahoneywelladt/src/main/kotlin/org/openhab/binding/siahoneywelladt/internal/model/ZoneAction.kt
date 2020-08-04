package org.openhab.binding.siahoneywelladt.internal.model

enum class ZoneAction(override val siaAction:Int):
    SiaAction {
    UNOMIT(0),
    OMIT(1)
}
