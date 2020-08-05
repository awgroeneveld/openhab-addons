package org.openhab.binding.siahoneywelladt.internal.oldmodel

// Enum to refer to an area action through an index value
enum class AreaAction(i: Int) {
    UNSET(0),
    SET(0),
    PARTSET(0),
    RESET(0),
    ABORTSET(0),
    FORCESET(0),
    STATE(0),
    ALARM(0),
    READY(0),
    COUNT(0)
}

enum class ZoneAction {
    UNOMIT,
    OMIT,
    ISOMIT,
    ZONE_STATE,
    PARAMETER,
    SET,
    COUNT
}


// Enum to refer to a zone parameter option through an index value
enum class ZoneParameterOption {
    SOAK_TEST,
    PART_SET,
    COUNT
}

// Enum to refer to a zone parameter flag through an index value
enum class ZoneParameterFlag {
    ON,
    OFF
}

// Enum to refer to a zone set state command through an index value
enum class ZoneSetState {
    OPEN,
    CLOSED,
    OPEN_AND_CLOSE,
    TAMPER
}

// Enum to refer to a zones state action through an index value
enum class ZoneStateAction {
    READY,
    ALARM,
    OPEN,
    TAMPER,
    RSTATE,
    OMITTED,
    COUNT
}

// Enum to refer to an output action through an index value
enum class OutputAction {
    OFF,
    ON
}

// Enum to refer to a poll action through an index value
enum class PollAction {
    OFF,
    ON,
    ADD,
    REMOVE,
    ONE_SHOT,
    COUNT
}

// Enum to refer to a poll item through an index value
enum class PollItem {
    AREAS,
    ZONES,
    OUTPUTS,
    EVERYTHING
}

enum class CodeAlarmModule {
    TELECOM,
    RS232,
    MONITOR,
    ALL
}
