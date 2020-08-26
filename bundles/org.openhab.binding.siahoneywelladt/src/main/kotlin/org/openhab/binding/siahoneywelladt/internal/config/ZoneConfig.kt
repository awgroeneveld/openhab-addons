package org.openhab.binding.siahoneywelladt.internal.config

import org.eclipse.jdt.annotation.NonNullByDefault
import org.eclipse.smarthome.core.thing.ThingStatusDetail
import org.openhab.binding.siahoneywelladt.internal.handler.ThingStatusException

@NonNullByDefault
class ZoneConfig {
    var areaLetter: String=""
        set(value){
            if (value.isBlank() || value.length!=1 || !('A'..'D').contains(value[0]))
                throwInvalidConfiguration("Invalid letter $value for area, valid values are A, B, C, D.")
            field=value
        }

    var areaNumber: Int = 0
        set(value){
            if (value !in (1..8))
                throwInvalidConfiguration("Invalid number $value for area, valid values are 1..8.")
            field=value
        }

    var rioLineNumber: Int=0
        set(value){
            if (value !in (1..4))
                throwInvalidConfiguration("Invalid number $value for rio line, valid values are 1..4.")
            field=value
        }

    var rioNumber: Int=0
        set(value){
            if (value !in (0..15))
                throwInvalidConfiguration("Invalid number $value for rio number, valid values are 0..15.")
            field=value
        }

    var zoneNumber: Int=0
        set(value){
            if (value !in (1..8))
                throwInvalidConfiguration("Invalid number $value for zone number, valid values are 1..8.")
            field=value
        }

    var type: ZoneType=ZoneType.UNSPECIFIED

    private fun throwInvalidConfiguration(message: String) {
        throw ThingStatusException(ThingStatusDetail.CONFIGURATION_ERROR, message, Throwable())
    }

}
