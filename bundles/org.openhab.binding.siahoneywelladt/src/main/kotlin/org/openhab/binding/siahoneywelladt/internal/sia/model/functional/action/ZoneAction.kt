package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.action

enum class ZoneAction(override val siaAction: Int) :
    SiaAction {
    UNOMIT(0),
    OMIT(1)
}
