package org.openhab.binding.siahoneywelladt.internal.model

enum class SiaFunction(val value: Byte, needsAcknowledge:Boolean) {
    END_OF_DATA(0x30, true),
    WAIT(0x31, true),
    ABORT(0x32, true),
    RES_3(0x33, true),
    RES_4(0x34, true),
    RES_5(0x35, true),
    ACK_AND_STANDBY(0x36, true),
    ACK_AND_DISCONNECT(0x37, true),
    ACKNOWLEDGE(0x38,false),
    ALT_ACKNOWLEDGE(0x08,false),
    REJECT(0x39, false),
    ALT_REJECT(0x09, false),

    // INFO BLOCKS
    CONTROL(0x43,false),
    ENVIRONMENTAL(0x45, true),
    NEW_EVENT(0x4E, true),
    OLD_EVENT(0x4F, true),
    PROGRAM(0x50, true),

    // SPECIAL BLOCKS
    CONFIGURATION(0x40,false),
    REMOTE_LOGIN(0x3F, true),
    ACCOUNT_ID(0x23, true),
    ORIGIN_ID(0x26, true),
    ASCII(0x41, true),
    EXTENDED(0x58, false),
    LISTEN_IN(0x4C, true),
    VCHN_REQUEST(0x56, true),
    VCHN_FRAME(0x76, true),
    VIDEO(0x49, true)
}
