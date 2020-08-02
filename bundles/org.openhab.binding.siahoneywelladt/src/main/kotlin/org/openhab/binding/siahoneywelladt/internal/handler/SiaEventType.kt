package org.openhab.binding.siahoneywelladt.internal.handler

enum class SiaEventType(
    val code: String,
    val eventName: String,
    val description: String,
    val addressField: AddressField
) {
    AC_RESTORAL(
        "AR",
        "AC restoral",
        "AC power has been restored",
        AddressField.UNUSED
    ),

    AC_TROUBLE(
        "AT",
        "AC trouble",
        "AC power has failed",
        AddressField.UNUSED
    ),

    BURGLARY_ALARM(
        "BA",
        "Burglary alarm",
        "Burglary zone has been violated while armed",
        AddressField.ZONE
    ),

    BURGLARY_BYPASS(
        "BB",
        "Burglary bypass",
        "Burglary zone has been bypassed",
        AddressField.ZONE
    ),

    BURGLARY_CANCEL(
        "BC",
        "Burglary cancel",
        "Alarm has been canceled",
        AddressField.USER
    ),

    BURGLARY_ALARM_RESTORE(
        "BH",
        "Burglary alarm restore",
        "Alarm condition eliminated",
        AddressField.ZONE
    ),

    BURGLARY_TROUBLE_RESTORE(
        "BJ",
        "Burglary trouble restore",
        "Trouble condition eliminated",
        AddressField.ZONE
    ),

    BURGLARY_RESTORE(
        "BR",
        "Burglary restoral",
        "Alarm/trouble condition eliminated",
        AddressField.ZONE
    ),

    BURGLARY_SUPERVISORY(
        "BS",
        "Burglary supervisory",
        "Unsafe intrusion detection system condition",
        AddressField.ZONE
    ),

    BURGLARY_TROUBLE(
        "BT",
        "Burglary trouble",
        "Burglary trouble condition was activated",
        AddressField.ZONE
    ),

    BURGLARY_UNBYPASS(
        "BU",
        "Burglary unbypass",
        "Zone bypass has been removed",
        AddressField.ZONE
    ),

    BURGLARY_VERIFIED(
        "BV",
        "Burglary verified",
        "More than 3 Burglary zones has been triggered",
        AddressField.ZONE
    ),

    BURGLARY_TEST(
        "BX",
        "Burglary test",
        "Burglary zone activated during testing",
        AddressField.ZONE
    ),

    CLOSING_AUTOMATICALLY(
        "CA",
        "Automatic closing",
        "System armed automatically",
        AddressField.AREA
    ),

    CLOSING_EXTEND(
        "CE",
        "Closing extend",
        "Extended closing time",
        AddressField.USER
    ),

    CLOSING_FORCED_START(
        "CF",
        "Forced closing",
        "System armed, zones not ready",
        AddressField.USER
    ),

    CLOSING_AREA(
        "CG",
        "Close area",
        "System has been partially armed",
        AddressField.USER
    ),

    CLOSING_FAIL(
        "CI",
        "Fail to close",
        "An area has not been armed at the end of the closing window",
        AddressField.USER
    ),

    CLOSING_LATE(
        "CJ",
        "Late close",
        "An area was armed after the closing window",
        AddressField.USER
    ),

    CLOSING_EARLY(
        "CK",
        "Early close",
        "An area was armed before the closing window",
        AddressField.USER
    ),

    CLOSING_REPORT(
        "CL",
        "Closing report",
        "System armed",
        AddressField.USER
    ),

    CLOSING_AUTOMATIC(
        "CP",
        "Automatic closing",
        "System armed automatically",
        AddressField.USER
    ),

    CLOSING_RECENT(
        "CR",
        "Recent closing",
        "An alarm occurred within 5 minutes after the system was armed",
        AddressField.UNUSED
    ),

    CLOSING_SWITCH(
        "CS",
        "Closing switch",
        "System was armed by keyswitch",
        AddressField.ZONE
    ),

    OPEN_LATE(
        "CT",
        "Late to open",
        "System was not disarmed on time",
        AddressField.AREA
    ),

    CLOSING_FORCED_READY(
        "CW",
        "Force armed",
        "Header for force armed sesssion, force point msg. may follow",
        AddressField.AREA
    ),

    CLOSING_POINT(
        "CZ",
        "Point closing",
        "A point (a opposed to a whole area or account) has closed/armed",
        AddressField.ZONE
    ),

    DOOR_ACCESS_CLOSED(
        "DC",
        "Access closed",
        "Access to all users prohibited",
        AddressField.DOOR
    ),

    DOOR_ACCESS_DENIED(
        "DD",
        "Access denied",
        "Access denied, unknown code",
        AddressField.DOOR
    ),

    DOOR_FORCED_OPEN(
        "DF",
        "Door forced",
        "Door opened without access request",
        AddressField.DOOR
    ),

    DOOR_ACCESS_GRANTED(
        "DG",
        "Access granted",
        "Door access granted",
        AddressField.DOOR
    ),

    DOOR_ACCESS_LOCKOUT(
        "DK",
        "Access lockout",
        "Door access denied, known code",
        AddressField.DOOR
    ),

    DOOR_ACCESS_OPEN(
        "DO",
        "Access open",
        "Door access to authorised users allowed",
        AddressField.DOOR
    ),

    DOOR_RESTORE(
        "DR",
        "Door restore",
        "Door access alarm/trouble condition eliminated",
        AddressField.DOOR
    ),

    DOOR_STATION(
        "DS",
        "Door station",
        "Identifies door for next report",
        AddressField.DOOR
    ),

    DOOR_TROUBLE(
        "DT",
        "Access trouble",
        "Access system trouble",
        AddressField.UNUSED
    ),

    DEALER_ID(
        "DU",
        "Dealer ID",
        "Zone description gives dealer ID #",
        AddressField.DEALER_ID
    ),
    EXIT_ALARM(
        "EA",
        "Exit alarm",
        "An exit zone remained violated at the end of the exit delay period",
        AddressField.ZONE
    ),

    EXIT_ERROR(
        "EE",
        "Exit error",
        "An exit zone remained violated at the end of the exit delay period",
        AddressField.USER
    ),


    EXPANSION_RESTORE(
        "ER",
        "Expansion restoral",
        "Expansion device trouble eliminated",
        AddressField.EXPANDER
    ),

    EXPANSION_TROUBLE(
        "ET",
        "Expansion trouble",
        "Expansion device trouble",
        AddressField.EXPANDER

    )


    ,
    FIRE_ALARM(
        "FA",
        "Fire alarm",
        "Fire condition detected",
        AddressField.ZONE
    ),
    FIRE_BYPASS(
        "FB",
        "Fire bypass",
        "Zone has been bypassed",
        AddressField.ZONE
    ),
    FIRE_ALARM_RESTORE(
        "FH",
        "Fire alarm restore",
        "Alarm condition eliminated",
        AddressField.ZONE
    ),
    FIRE_TEST_BEGIN(
        "FI",
        "Fire test begin",
        "The transmitter area's fire test has begun",
        AddressField.ZONE
    ),
    FIRE_TROUBLE_RESTORE(
        "FJ",
        "Fire trouble restore",
        "Trouble condition eliminated",
        AddressField.ZONE
    ),
    FIRE_TEST_END(
        "FK",
        "Fire test end",
        "The transmitter area's fire test has ended",
        AddressField.ZONE
    ),
    FIRE_RESTORAL(
        "FR",
        "Fire restoral",
        "Alarm/trouble condition has been eliminated",
        AddressField.ZONE
    ),
    FIRE_SUPERVISORY(
        "FS",
        "Fire supervisory",
        "Unsafe fire detection system condition",
        AddressField.ZONE
    ),
    FIRE_TROUBLE(
        "FT",
        "Fire trouble",
        "Zone disabled by fault",
        AddressField.ZONE
    ),
    FIRE_UNBYPASS(
        "FU",
        "Fire unbypass",
        "Bypass has been removed",
        AddressField.ZONE
    ),
    FIRE_TEST(
        "FX",
        "Fire test",
        "Fire zone activated during test",
        AddressField.ZONE
    ),
    MISSING_FIRE_TROUBLE(
        "FY",
        "Missing fire trouble",
        "A fire point is now logically missing",
        AddressField.ZONE
    ),
    GAS_ALARM(
        "GA",
        "Gas alarm",
        "Gas alarm condition detected",
        AddressField.ZONE
    ),
    GAS_BYPASS(
        "GB",
        "Gas bypass",
        "Zone has been bypassed",
        AddressField.ZONE
    ),
    GAS_ALARM_RESTORE(
        "GH",
        "Gas alarm restore",
        "Alarm condition eliminated",
        AddressField.ZONE
    ),
    GAS_TROUBLE_RESTORE(
        "GJ",
        "Gas trouble restore",
        "Trouble condition eliminated",
        AddressField.ZONE
    ),
    GAS_ALARM_TROUBLE_RESTORE(
        "GR",
        "Gas alarm/trouble restore",
        "Alarm/trouble condition has been eliminated",
        AddressField.ZONE
    ),
    GAS_SUPERVISORY(
        "GS",
        "Gas supervisory",
        "Unsafe gas detection system condition",
        AddressField.ZONE
    ),
    GAS_TROUBLE(
        "GT",
        "Gas trouble",
        "Zone disabled by fault",
        AddressField.ZONE
    ),
    GAS_UNBYPASS(
        "GU",
        "Gas unbypass",
        "Bypass has been removed",
        AddressField.ZONE
    ),
    GAS_TEST_GAS(
        "GX",
        "Gas test gas",
        "Zone activated during test",
        AddressField.ZONE
    ),
    HOLDUP_ALARM(
        "HA",
        "Hold-up alarm",
        "Silent alarm, user under duress",
        AddressField.ZONE
    ),
    HOLDUP_BYPASS(
        "HB",
        "Hold-up bypass",
        "Zone has been bypassed",
        AddressField.ZONE
    ),
    HOLDUP_ALARM_RESTORAL(
        "HH",
        "Hold-up alarm restoral",
        "Alarm condition eliminated",
        AddressField.ZONE
    ),
    HOLDUP_TROUBLE_RESTORAL(
        "HJ",
        "Hold-up trouble restoral",
        "Trouble condition eliminated",
        AddressField.ZONE
    ),
    HOLDUP_RESTORAL(
        "HR",
        "Hold-up restoral",
        "Alarm/trouble condition eliminated",
        AddressField.ZONE
    ),
    HOLDUP_SUPERVISORY(
        "HS",
        "Hold-up supervisory",
        "Unsafe hold-up system condition",
        AddressField.ZONE
    ),
    HOLDUP_TROUBLE(
        "HT",
        "Hold-up trouble",
        "Zone disable by fault",
        AddressField.ZONE
    ),
    HOLDUP_UNBYPASS(
        "HU",
        "Hold-up unbypass",
        "Bypass has been removed",
        AddressField.ZONE
    ),
    USER_CODE_TAMPER(
        "JA",
        "User code tamper",
        "Too many unsuccessfull attempts made to enter a user ID",
        AddressField.ZONE
    ),
    DATE_CHANGED(
        "JD",
        "Date changed",
        "The date was changed in the transmitter/receiver",
        AddressField.ZONE
    ),
    HOLIDAY_CHANGED(
        "JH",
        "Holiday changed",
        "The transmitters holiday schedule has been changed",
        AddressField.ZONE
    ),
    LOG_THRESHOLD(
        "JL",
        "Log threshold",
        "The transmitters log memory has reached its threshold level",
        AddressField.ZONE
    ),
    LOG_OVERFLOW(
        "JO",
        "Log overflow",
        "The transmitters log memory has overflowed",
        AddressField.ZONE
    ),
    SCHEDULE_EXECUTE(
        "JR",
        "Schedule execute",
        "An automatic scheduled event was executed",
        AddressField.ZONE
    ),
    SCHEDULE_CHANGE(
        "JS",
        "Schedule change",
        "An automatic schedule was changed",
        AddressField.ZONE
    ),
    TIME_CHANGED(
        "JT",
        "Time changed",
        "The time was changed in the tranmitter/receiver",
        AddressField.ZONE
    ),
    USER_CODE_CHANGE(
        "JV",
        "User code change",
        "A user\'s code has been changed",
        AddressField.ZONE
    ),
    USER_CODE_DELETE(
        "JX",
        "User code delete",
        "A user\'s code has been removed",
        AddressField.ZONE
    ),
    HEAT_ALARM(
        "KA",
        "Heat alarm",
        "High temperature detected on premise",
        AddressField.ZONE
    ),
    HEAT_BYPASS(
        "KB",
        "Heat bypass",
        "Zone has been bypassed",
        AddressField.ZONE
    ),
    HEAT_ALARM_RESTORE(
        "KH",
        "Heat alarm restore",
        "Alarm condition eliminated",
        AddressField.ZONE
    ),
    HEAT_TROUBLE_RESTORE(
        "KJ",
        "Heat trouble restore",
        "Trouble condition eliminated",
        AddressField.ZONE
    ),
    HEAT_RESTORAL(
        "KR",
        "Heat restoral",
        "Alarm/trouble condition eliminated",
        AddressField.ZONE
    ),
    HEAT_SUPERVISORY(
        "KS",
        "Heat supervisory",
        "Unsafe heat detection system condition",
        AddressField.ZONE
    ),
    HEAT_TROUBLE(
        "KT",
        "Heat trouble",
        "Zone disabled by fault",
        AddressField.ZONE
    ),
    HEAT_UNBYPASS(
        "KU",
        "Heat unbypass",
        "Bypass has been removed",
        AddressField.ZONE
    ),
    LOCAL_PROGRAM_START(
        "LB",
        "Local program",
        "Begin local programming",
        AddressField.ZONE
    ),
    LOCAL_PROGRAM_DENIED(
        "LD",
        "Local program denied",
        "Access code incorrect",
        AddressField.ZONE
    ),
    LISTEN_IN_ENDED(
        "LE",
        "Listen-in ended",
        "The listen-in session has been terminated",
        AddressField.ZONE
    ),
    LISTEN_IN_BEGIN(
        "LF",
        "Listen-in begin",
        "The listen-in session with the receiver has begun",
        AddressField.ZONE
    ),
    PHONE_LINE_RESTORAL(
        "LR",
        "Phone line restoral",
        "Phone line restored to service",
        AddressField.ZONE
    ),
    LOCAL_PROGRAM_SUCCESS(
        "LS",
        "Local program",
        "Local programming successfull",
        AddressField.ZONE
    ),
    PHONE_LINE_TROUBLE(
        "LT",
        "Phone line trouble",
        "Phone line report",
        AddressField.ZONE
    ),
    LOCAL_PROGRAM_FAIL(
        "LU",
        "Local program fail",
        "Local programming unsuccessfull",
        AddressField.ZONE
    ),
    LOCAL_PROGRAM_ENDED(
        "LX",
        "Local program ended",
        "A local programming session has been terminated",
        AddressField.ZONE
    ),
    MEDICAL_ALARM(
        "MA",
        "Medical alarm",
        "Emergency assistance request",
        AddressField.ZONE
    ),
    MEDICAL_BYPASS(
        "MB",
        "Medical bypass",
        "Zone has been bypassed",
        AddressField.ZONE
    ),
    MEDICAL_ALARM_RESTORE(
        "MH",
        "Medical alarm restore",
        "Alarm condition eliminated",
        AddressField.ZONE
    ),
    MEDICAL_TROUBLE_RESTORE(
        "MJ",
        "Medical trouble restore",
        "Trouble condition eliminated",
        AddressField.ZONE
    ),
    MEDICAL_RESTORAL(
        "MR",
        "Medical restoral",
        "Alarm/trouble condition eliminated",
        AddressField.ZONE
    ),
    MEDICAL_SUPERVISORY(
        "MS",
        "Medical supervisory",
        "Unsafe system condition exists",
        AddressField.ZONE
    ),
    MEDICAL_TROUBLE(
        "MT",
        "Medical trouble",
        "Zone disabled by fault",
        AddressField.ZONE
    ),
    MEDICAL_UNBYPASS(
        "MU",
        "Medical unbypass",
        "Bypass has been removed",
        AddressField.ZONE
    ),
    NO_ACTIVITY(
        "NA",
        "No activity",
        "There has been no activity for a programmed amount of time",
        AddressField.ZONE
    ),
    FORCE_PERIMETER_ARM(
        "NF",
        "Force perimeter arm",
        "Some zones/points not ready",
        AddressField.ZONE
    ),
    PERIMETER_ARMED(
        "NL",
        "Perimeter armed",
        "An area has been perimeter armed",
        AddressField.ZONE
    ),
    AUTOMATIC_OPENING(
        "OA",
        "Automatic opening",
        "System has disarmed automatically",
        AddressField.ZONE
    ),
    CANCEL_REPORT(
        "OC",
        "Cancel report",
        "Untyped zone cancel",
        AddressField.ZONE
    ),
    OPEN_AREA(
        "OG",
        "Open area",
        "System has been partially disarmed",
        AddressField.ZONE
    ),
    FAIL_TO_OPEN(
        "OI",
        "Fail to open",
        "An area has not been armed at the end of the opening window",
        AddressField.ZONE
    ),
    LATE_OPEN(
        "OJ",
        "Late open",
        "An area was disarmed after the opening window",
        AddressField.ZONE
    ),
    EARLY_OPEN(
        "OK",
        "Early open",
        "An area was disarmed before the opening window",
        AddressField.ZONE
    ),
    OPENING_REPORT(
        "OP",
        "Opening report",
        "Account was disarmed",
        AddressField.ZONE
    ),
    DISARM_FROM_ALARM(
        "OR",
        "Disarm from alarm",
        "Account in alarm was reset/disarmed",
        AddressField.ZONE
    ),
    OPENING_KEYSWITCH(
        "OS",
        "Opening keyswitch",
        "Account has been disarmed by keyswitch zone",
        AddressField.ZONE
    ),
    LATE_TO_CLOSE(
        "OT",
        "Late to close",
        "System was not armed on time",
        AddressField.ZONE
    ),
    POINT_OPENING(
        "OZ",
        "Point opening",
        "A point, rather then a full area or account was disarmed",
        AddressField.ZONE
    ),
    PANIC_ALARM(
        "PA",
        "Panic alarm",
        "Emergency assistance request, manually activated",
        AddressField.ZONE
    ),
    PANIC_BYPASS(
        "PB",
        "Panic bypass",
        "Panic zone has been bypassed",
        AddressField.ZONE
    ),
    PANIC_ALARM_RESTORE(
        "PH",
        "Panic alarm restore",
        "Alarm condition eliminated",
        AddressField.ZONE
    ),
    PANIC_TROUBLE_RESTORE(
        "PJ",
        "Panic trouble restore",
        "Trouble condition eliminated",
        AddressField.ZONE
    ),
    PANIC_RESTORAL(
        "PR",
        "Panic restoral",
        "Alarm/trouble condition eliminated",
        AddressField.ZONE
    ),
    PANIC_SUPERVISORY(
        "PS",
        "Panic Supervisory",
        "Unsafe system condition exists",
        AddressField.ZONE
    ),
    PANIC_TROUBLE(
        "PT",
        "Panic trouble",
        "Zone disabled by fault",
        AddressField.ZONE
    ),
    PANIC_UNBYPASS(
        "PU",
        "Panic unbypass",
        "Panic zone bypass has been removed",
        AddressField.ZONE
    ),
    EMERGENCY_ALARM(
        "QA",
        "Emergency alarm",
        "Emergency assistance request, manually activated",
        AddressField.ZONE
    ),
    EMERGENCY_BYPASS(
        "QB",
        "Emergency bypass",
        "Zone has been bypassed",
        AddressField.ZONE
    ),
    EMERGENCY_ALARM_RESTORE(
        "QH",
        "Emergency alarm restore",
        "Alarm condition eliminated",
        AddressField.ZONE
    ),
    EMERGENCY_TROUBLE_RESTORE(
        "QJ",
        "Emergency trouble restore",
        "Trouble condition eliminated",
        AddressField.ZONE
    ),
    EMERGENCY_RESTORAL(
        "QR",
        "Emergency restoral",
        "Alarm/trouble condition eliminated",
        AddressField.ZONE
    ),
    EMERGENCY_SUPERVISORY(
        "QS",
        "Emergency Supervisory",
        "Unsafe system condition exists",
        AddressField.ZONE
    ),
    EMERGENCY_TROUBLE(
        "QT",
        "Emergency trouble",
        "Zone disabled by fault",
        AddressField.ZONE
    ),
    EMERGENCY_UNBYPASS(
        "QU",
        "Emergency unbypass",
        "Zone bypass has been removed",
        AddressField.ZONE
    ),
    REMOTE_PROGRAMMER_CALL_FAILED(
        "RA",
        "Remote programmer call failed",
        "Transmitter failed to communicate with the remote programmer",
        AddressField.ZONE
    ),
    REMOTE_PROGRAM_BEGIN(
        "RB",
        "Remote program begin",
        "Remote programming session initiated",
        AddressField.ZONE
    ),
    RELAY_CLOSE(
        "RC",
        "Relay close",
        "The relay specified in the address field (optional) has energized",
        AddressField.ZONE
    ),
    REMOTE_PROGRAM_DENIED(
        "RD",
        "Remote program denied",
        "Access passcode incorrect",
        AddressField.ZONE
    ),
    REMOTE_RESET(
        "RN",
        "Remote reset",
        "Transmitter was reset via a remote programmer",
        AddressField.ZONE
    ),
    RELAY_OPEN(
        "RO",
        "Relay open",
        "The relay specified in the address field (optional) has de-energized",
        AddressField.ZONE
    ),
    AUTOMATIC_TEST(
        "RP",
        "Automatic test",
        "Automatic communication test report",
        AddressField.ZONE
    ),
    POWER_UP(
        "RR",
        "Power up",
        "System lost power, is now restored",
        AddressField.ZONE
    ),
    REMOTE_PROGRAM_SUCCESS(
        "RS",
        "Remote program success",
        "Remote programming successful",
        AddressField.ZONE
    ),
    DATA_LOST(
        "RT",
        "Data lost",
        "Dailer data lost, transmission error",
        AddressField.ZONE
    ),
    REMOTE_PROGRAM_FAIL(
        "RU",
        "Remote program fail",
        "Remote programming unsuccessful",
        AddressField.ZONE
    ),
    MANUAL_TEST(
        "RX",
        "Manual test",
        "Manual communication test report",
        AddressField.ZONE
    ),
    SPRINKLER_ALARM(
        "SA",
        "Sprinkler alarm",
        "Sprinkler flow condition exists",
        AddressField.ZONE
    ),
    SPRINKLER_BYPASS(
        "SB",
        "Sprinkler bypass",
        "Sprinkler zone has been bypassed",
        AddressField.ZONE
    ),
    SPRINKLER_ALARM_RESTORE(
        "SH",
        "Sprinkler alarm restore",
        "Alarm condition eliminated",
        AddressField.ZONE
    ),
    SPRINKLER_TROUBLE_RESTORE(
        "SJ",
        "Sprinkler trouble restore",
        "Trouble condition eliminated",
        AddressField.ZONE
    ),
    SPRINKLER_RESTORAL(
        "SR",
        "Sprinkler restoral",
        "Alarm/trouble condition eliminated",
        AddressField.ZONE
    ),
    SPRINKLER_SUPERVISORY(
        "SS",
        "Sprinkler Supervisory",
        "Unsafe sprinkler system condition exists",
        AddressField.ZONE
    ),
    SPRINKLER_TROUBLE(
        "ST",
        "Sprinkler trouble",
        "Zone disabled by fault",
        AddressField.ZONE
    ),
    SPRINKLER_UNBYPASS(
        "SU",
        "Sprinkler unbypass",
        "Sprinkler zone bypass has been removed",
        AddressField.ZONE
    ),
    TAMPER_ALARM(
        "TA",
        "Tamper Alarm",
        "Alarm equipment enclosure opened",
        AddressField.ZONE
    ),
    TAMPER_BYPASS(
        "TB",
        "Tamper bypass",
        "Tamper detection has been bypassed",
        AddressField.ZONE
    ),
    TEST_END(
        "TE",
        "Test end",
        "Communicator restored to normal operation",
        AddressField.ZONE
    ),
    TAMPER_RESTORAL(
        "TR",
        "Tamper restoral",
        "Alarm equipment enclosure has been closed",
        AddressField.ZONE
    ),
    TEST_START(
        "TS",
        "Test start",
        "Communicator taken out of operation",
        AddressField.ZONE
    ),
    TAMPER_UNBYPASS(
        "TU",
        "Tamper unbypass",
        "Tamper detection bypass has been removed",
        AddressField.ZONE
    ),
    TEST_REPORT(
        "TX",
        "Test report",
        "An unspecified (manual or automatic) communicator test",
        AddressField.ZONE
    ),
    UNTYPED_ZONE_ALARM(
        "UA",
        "Untyped zone alarm",
        "Alarm condition from zone of unknown type ",
        AddressField.ZONE
    ),
    UNTYPED_ZONE_BYPASS(
        "UB",
        "Untyped zone bypass",
        "Zone of unknown type has been bypassed",
        AddressField.ZONE
    ),
    UNTYPED_ALARM_RESTORAL(
        "UH",
        "Untyped alarm restoral",
        "Alarm condition eliminated",
        AddressField.ZONE
    ),
    UNTYPED_TROUBLE_RESTORAL(
        "UJ",
        "Untyped trouble restoral",
        "Trouble condition eliminated",
        AddressField.ZONE
    ),
    UNTYPED_ALARM_TROUBLE_RESTORAL(
        "UR",
        "Untyped alarm/trouble restoral",
        "Alarm/trouble condition eliminated",
        AddressField.ZONE
    ),
    UNTYPED_ZONE_SUPERVISORY(
        "US",
        "Untyped zone supervisory",
        "Unsafe condition from zone of unknown type",
        AddressField.ZONE
    ),
    UNTYPED_ZONE_TROUBLE(
        "UT",
        "Untyped zone trouble",
        "Trouble condition from zone of unknown type",
        AddressField.ZONE
    ),
    UNTYPED_ZONE_UNBYPASS(
        "UU",
        "Untyped zone unbypass",
        "Bypass of unknown zone has been removed",
        AddressField.ZONE
    ),
    UNDEFINED_ALARM(
        "UX",
        "Undefined alarm",
        "An undefined alarm condition has occured",
        AddressField.ZONE
    ),
    UNTYPED_MISSING_TROUBLE(
        "UY",
        "Untyped missing trouble",
        "A point which was not armed is now logically missing",
        AddressField.ZONE
    ),
    UNTYPED_MISSING_ALARM(
        "UZ",
        "Untyped missing alarm",
        "A point which was armed is now logically missing",
        AddressField.ZONE
    ),
    PRINTER_PAPER_IN(
        "VI",
        "Printer paper in",
        "Transmitter or receiver paper in, printer X",
        AddressField.ZONE
    ),
    PRINTER_PAPER_OUT(
        "VO",
        "Printer paper out",
        "Transmitter or receiver paper out, printer X",
        AddressField.ZONE
    ),
    PRINTER_RESTORE(
        "VR",
        "Printer restore",
        "Transmitter or receiver trouble restored, printer X",
        AddressField.ZONE
    ),
    PRINTER_TROUBLE(
        "VT",
        "Printer trouble",
        "Transmitter or receiver trouble, printer X",
        AddressField.ZONE
    ),
    PRINTER_TEST(
        "VX",
        "Printer test",
        "Transmitter or receiver test, printer X",
        AddressField.ZONE
    ),
    PRINTER_ON_LINE(
        "VY",
        "Printer on line",
        "The receiver\'s printer is now on line",
        AddressField.ZONE
    ),
    PRINTER_OFF_LINE(
        "VZ",
        "Printer off line",
        "The receiver\'s printer is now off line",
        AddressField.ZONE
    ),
    WATER_ALARM(
        "WA",
        "Water alarm",
        "Water detected at premise",
        AddressField.ZONE
    ),
    WATER_BYPASS(
        "WB",
        "Water bypass",
        "Water detection zone has been bypassed",
        AddressField.ZONE
    ),
    WATER_ALARM_RESTORAL(
        "WH",
        "Water alarm restoral",
        "Alarm condition eliminated",
        AddressField.ZONE
    ),
    WATER_TROUBLE_RESTORAL(
        "WJ",
        "Water trouble restoral",
        "Trouble condition eliminated",
        AddressField.ZONE
    ),
    WATER_RESTORAL(
        "WR",
        "Water restoral",
        "Alarm/trouble condition has been eliminated",
        AddressField.ZONE
    ),
    WATER_SUPERVISORY(
        "WS",
        "Water supervisory",
        "Unsafe water detection system detected",
        AddressField.ZONE
    ),
    WATER_TROUBLE(
        "WT",
        "Water trouble",
        "Zone disabled by fault",
        AddressField.ZONE
    ),
    WATER_UNBYPASS(
        "WU",
        "Water unbypass",
        "Water detection bypass has been removed",
        AddressField.ZONE
    ),
    EXTRA_POINT(
        "XE",
        "Extra point",
        "The panel has sensed an extra point not specified for this site",
        AddressField.ZONE
    ),
    EXTRA_RF_POINT(
        "XF",
        "Extra RF point",
        "The panel has sensed an extra RF point not specified for this site",
        AddressField.ZONE
    ),
    SENSOR_RESET(
        "XI",
        "Sensor reset",
        "A user has reset a sensor",
        AddressField.ZONE
    ),
    TX_BATTERY_RESTORAL(
        "XR",
        "TX battery restoral",
        "Low battery in wireless transmitter has been corrected",
        AddressField.ZONE
    ),
    TX_BATTERY_TROUBLE(
        "XT",
        "TX battery trouble",
        "Low battery in wireless transmitter",
        AddressField.ZONE
    ),
    FORCED_POINT(
        "XW",
        "Forced point",
        "A point was forced out of the system at arm time",
        AddressField.ZONE
    ),
    BUSY_SECONDS(
        "YB",
        "Busy seconds",
        "Percent of time receiver\'s line card is on line",
        AddressField.ZONE
    ),
    COMMUNICATION_FAIL(
        "YC",
        "Communication fail",
        "Receiver and transmitter",
        AddressField.ZONE
    ),
    RX_LINE_CARD_TROUBLE(
        "YD",
        "RX line card trouble",
        "A line card identified by the passed address is in trouble",
        AddressField.ZONE
    ),
    RX_LINE_CARD_RESTORAL(
        "YE",
        "RX line card restoral",
        "A line card identified by the passed address has restored",
        AddressField.ZONE
    ),
    PARAMETER_CHECKSUM_FAIL(
        "YF",
        "Parameter checksum fail",
        "System data corrupted",
        AddressField.ZONE
    ),
    PARAMETER_CHANGED(
        "YG",
        "Parameter changed",
        "A tranmitter\'s parameters have been changed",
        AddressField.ZONE
    ),
    COMMUNICATION_RESTORAL(
        "YK",
        "Communication restoral",
        "The transmitter has resumed communication with a receiver",
        AddressField.ZONE
    ),
    SYSTEM_BATTERY_MISSING(
        "YM",
        "System battery missing",
        "The tranmitter/receiver battery is missing",
        AddressField.ZONE
    ),
    INVALID_REPORT(
        "YN",
        "Invalid report",
        "The transmitter has send a packet with invalid data",
        AddressField.ZONE
    ),
    UNKNOWN_MESSAGE(
        "YO",
        "Unknown message",
        "An unknown message was received from automation or the printer",
        AddressField.ZONE
    ),
    POWER_SUPPLY_TROUBLE(
        "YP",
        "Power supply trouble",
        "The transmitter/receiver has a problem with the power supply",
        AddressField.ZONE
    ),
    POWER_SUPPLY_RESTORED(
        "YQ",
        "Power supply restored",
        "The transmitter/receiver power supply has restored",
        AddressField.ZONE
    ),
    SYSTEM_BATTERY_RESTORAL(
        "YR",
        "System battery restoral",
        "Low battery has been corrected",
        AddressField.ZONE
    ),
    COMMUNICATION_TROUBLE(
        "YS",
        "Communication trouble",
        "Receiver and transmitter",
        AddressField.ZONE
    ),
    SYSTEM_BATTERY_TROUBLE(
        "YT",
        "System battery trouble",
        "Low battery in control panel/communicator",
        AddressField.ZONE
    ),
    WATCHDOG_RESET(
        "YW",
        "Watchdog reset",
        "The transmitter created an internal reset",
        AddressField.ZONE
    ),
    SERVICE_REQUIRED(
        "YX",
        "Service required",
        "A transmitter/receiver needs service",
        AddressField.ZONE
    ),
    STATUS_REPORT(
        "YY",
        "Status report",
        "This is a header for an account status report transmission",
        AddressField.ZONE
    ),
    SERVICE_COMPLETED(
        "YZ",
        "Service completed",
        "Required transmitter/receiver service completed",
        AddressField.ZONE
    ),
    FREEZE_ALARM(
        "ZA",
        "Freeze alarm",
        "Low temperature detected at premise",
        AddressField.ZONE
    ),
    FREEZE_BYPASS(
        "ZB",
        "Freeze bypass",
        "Low temperature detection has been bypassed",
        AddressField.ZONE
    ),
    FREEZE_ALARM_RESTORAL(
        "ZH",
        "Freeze alarm restoral",
        "Alarm condition eliminated",
        AddressField.ZONE
    ),
    FREEZE_TROUBLE_RESTORAL(
        "ZJ",
        "Freeze trouble restoral",
        "Trouble condition eliminated",
        AddressField.ZONE
    ),
    FREEZE_RESTORAL(
        "ZR",
        "Freeze restoral",
        "Alarm/trouble condition has been eliminated",
        AddressField.ZONE
    ),
    FREEZE_SUPERVISORY(
        "ZS",
        "Freeze supervisory",
        "Unsafe freeze detection system condition detected",
        AddressField.ZONE
    ),
    FREEZE_TROUBLE(
        "ZT",
        "Freeze trouble",
        "Zone disabled by fault",
        AddressField.ZONE
    ),
    FREEZE_UNBYPASS(
        "ZU",
        "Freeze unbypass",
        "Low temperature detection bypass removed",
        AddressField.ZONE
    )
}
