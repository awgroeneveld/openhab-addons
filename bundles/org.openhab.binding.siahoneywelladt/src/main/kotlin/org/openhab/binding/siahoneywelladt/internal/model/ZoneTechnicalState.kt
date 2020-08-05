package org.openhab.binding.siahoneywelladt.internal.model

enum class ZoneTechnicalState(override val siaState: Int) : SiaState {
    TAMPER_SC(0),
    LOW_RESISTANCE(1),
    CLOSED(2),
    HIGH_RESISTANCE(3),
    OPEN(4),
    TAMPER_OC(5),
    MASKED(6),
    TAMPER_CV(7),
    FAULT(8)
}

