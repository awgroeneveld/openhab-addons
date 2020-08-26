package org.openhab.binding.siahoneywelladt.internal.config

import org.eclipse.jdt.annotation.NonNullByDefault
import org.eclipse.smarthome.core.thing.ThingStatusDetail
import org.openhab.binding.siahoneywelladt.internal.handler.ThingStatusException

@NonNullByDefault
class AreaConfig {
    var letter: String=""
    set(value){
        if (value.isBlank() || value.length!=1 || !('A'..'D').contains(value[0]))
            throwInvalidConfiguration("Invalid letter $value for area, valid values are A, B, C, D.")
        field=value
    }

    var number: Int = 0
    set(value){
        if (value !in (1..8))
            throwInvalidConfiguration("Invalid number $value for area, valid values are 1..8.")
        field=value
    }

    private fun throwInvalidConfiguration(message: String) {
        throw ThingStatusException(ThingStatusDetail.CONFIGURATION_ERROR, message, Throwable())
    }

}



