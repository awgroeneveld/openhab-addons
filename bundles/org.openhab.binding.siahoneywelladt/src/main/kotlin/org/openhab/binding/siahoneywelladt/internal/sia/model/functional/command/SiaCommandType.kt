package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.command

import org.openhab.binding.siahoneywelladt.internal.sia.model.technical.SiaFunction

enum class SiaCommandType(
    val value: String,
    val type: SiaCommandSubjectType,
    val function: SiaFunction
) {
    AREA_ACTION("SA",
        SiaCommandSubjectType.AREA,
        SiaFunction.CONTROL
    ),
    ZONE_ACTION("SB",
        SiaCommandSubjectType.ZONE,
        SiaFunction.CONTROL
    )
}
