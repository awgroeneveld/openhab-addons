package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.event

import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.event.SiaEventType
import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.event.SiaMetaData

class RegularSiaEventDetails {
    var rawMessage: ByteArray? = null
    var eventType: SiaEventType? = null
    var metaData: SiaMetaData? = null
    var accountId: String? = null
}
