package org.openhab.binding.siahoneywelladt.internal.sia.model.technical

enum class SiaFunction(val code: Int, val needsAcknowledge: Boolean, text: String, val eventType: SiaEventHandlerType) {
    END_OF_DATA(0x30, true, "End of data",
        SiaEventHandlerType.MESSAGELESS
    ),
    WAIT(0x31, true, "Wait",
        SiaEventHandlerType.MESSAGELESS
    ),
    ABORT(0x32, true, "Abort",
        SiaEventHandlerType.MESSAGELESS
    ),
    RES_3(0x33, true, "Reserved",
        SiaEventHandlerType.MESSAGELESS
    ),
    RES_4(0x34, true, "Reserved",
        SiaEventHandlerType.MESSAGELESS
    ),
    RES_5(0x35, true, "Reserved",
        SiaEventHandlerType.MESSAGELESS
    ),
    ACK_AND_STANDBY(0x36, true, "Acknowledge and standby",
        SiaEventHandlerType.MESSAGELESS
    ),
    ACK_AND_DISCONNECT(0x37, true, "Acknowledge and disconnect",
        SiaEventHandlerType.MESSAGELESS
    ),
    ACKNOWLEDGE(0x38, false, "Acknowledge",
        SiaEventHandlerType.MESSAGELESS
    ),
    ALT_ACKNOWLEDGE(0x08, false, "Acknowledge",
        SiaEventHandlerType.MESSAGELESS
    ),
    REJECT(0x39, false, "Reject",
        SiaEventHandlerType.MESSAGELESS
    ),
    ALT_REJECT(0x09, false, "Reject",
        SiaEventHandlerType.MESSAGELESS
    ),

    // INFO BLOCKS
    CONTROL(0x43, false, "Control",
        SiaEventHandlerType.UNKNOWN
    ),
    ENVIRONMENTAL(0x45, true, "Environmental",
        SiaEventHandlerType.UNKNOWN
    ),
    NEW_EVENT(0x4E, true, "New event",
        SiaEventHandlerType.REGULAR
    ),
    OLD_EVENT(0x4F, true, "Old event",
        SiaEventHandlerType.REGULAR
    ),
    PROGRAM(0x50, true, "Program",
        SiaEventHandlerType.UNKNOWN
    ),

    // SPECIAL BLOCKS
    CONFIGURATION(0x40, false, "Configuration",
        SiaEventHandlerType.CONFIGURATION
    ),
    REMOTE_LOGIN(0x3F, true, "Remote login",
        SiaEventHandlerType.UNKNOWN
    ),
    ACCOUNT_ID(0x23, true, "Account ID",
        SiaEventHandlerType.ACCOUNT
    ),
    ORIGIN_ID(0x26, true, "Origin ID",
        SiaEventHandlerType.UNKNOWN
    ),
    ASCII(0x41, true, "ASCII",
        SiaEventHandlerType.ASCII
    ),
    EXTENDED(0x58, false, "Extended",
        SiaEventHandlerType.UNKNOWN
    ),
    LISTEN_IN(0x4C, true, "Listen-In",
        SiaEventHandlerType.UNKNOWN
    ),
    VCHN_REQUEST(0x56, true, "Video channel request",
        SiaEventHandlerType.UNKNOWN
    ),
    VCHN_FRAME(0x76, true, "Video channel frame data",
        SiaEventHandlerType.UNKNOWN
    ),
    VIDEO(0x49, true, "Video",
        SiaEventHandlerType.CONFIGURATION
    );

    companion object {
        val messageLessValues = values().filter { it.eventType == SiaEventHandlerType.MESSAGELESS }

        private val functionsByCode = values().associateBy { it.code }
        fun getFunction(code: Int): SiaFunction? = functionsByCode[code]
    }


}

