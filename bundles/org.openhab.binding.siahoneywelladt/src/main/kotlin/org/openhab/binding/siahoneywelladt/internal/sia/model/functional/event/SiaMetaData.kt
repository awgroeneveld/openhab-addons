package org.openhab.binding.siahoneywelladt.internal.sia.model.functional.event

import org.openhab.binding.siahoneywelladt.internal.sia.model.functional.Area
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class SiaMetaData(
    val date: LocalDate?,
    val time: LocalTime?,
    val subscriberId: Int?,
    val areaId: Area?,
    val peripheralId: Int?,
    val automatedId: Int?,
    val telephone: Int?,
    val packetLevel: Int?,
    val value: Int?,
    val path: Int?,
    val routeGroup: Int?,
    val subSubscriber: Int?
) {
    companion object {
        val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yy")
        val timeFormatterWithSeconds = DateTimeFormatter.ofPattern("HH:mm:ss")
        val timeFormatterWithoutSeconds = DateTimeFormatter.ofPattern("HH:mm")

        fun fromMessage(message: String): SiaMetaData {
            var date: LocalDate? = null
            var time: LocalTime? = null
            var subscriberId: Int? = null
            var areaId: Area? = null
            var peripheralId: Int? = null
            var automatedId: Int? = null
            var telephone: Int? = null
            var packetLevel: Int? = null
            var value: Int? = null
            var path: Int? = null
            var routeGroup: Int? = null
            var subSubscriber: Int? = null
            val items = message.split("/")
            items.map {
                SiaMetaDataType.getMetaDataType(
                    it.substring(0, 2)
                ) to it.substring(2)
            }
                .forEach {
                    when (it.first) {
                        SiaMetaDataType.DATE -> date =
                            LocalDate.parse(it.second,
                                dateFormatter
                            )
                        SiaMetaDataType.TIME -> time =
                            if (it.second.length > 5) LocalTime.parse(
                                it.second,
                                timeFormatterWithSeconds
                            )
                            else LocalTime.parse(it.second,
                                timeFormatterWithoutSeconds
                            )

                        SiaMetaDataType.SUBSCRIBER_ID -> subscriberId = it.second.toInt()
                        SiaMetaDataType.AREA_ID -> areaId =
                            Area(it.second.toInt())
                        SiaMetaDataType.PERIPHERAL_ID -> peripheralId = it.second.toInt()
                        SiaMetaDataType.AUTOMATED_ID -> automatedId = it.second.toInt()
                        SiaMetaDataType.TELEPHONE -> telephone = it.second.toInt()
                        SiaMetaDataType.PACKET_LEVEL -> packetLevel = it.second.toInt()
                        SiaMetaDataType.VALUE -> value = it.second.toInt()
                        SiaMetaDataType.PATH -> path = it.second.toInt()
                        SiaMetaDataType.ROUTE_GROUP -> routeGroup = it.second.toInt()
                        SiaMetaDataType.SUB_SUBSCRIBER -> value = it.second.toInt()
                    }
                }
            return SiaMetaData(
                date = date,
                time = time,
                subscriberId = subscriberId,
                areaId = areaId,
                peripheralId = peripheralId,
                automatedId = automatedId,
                telephone = telephone,
                packetLevel = packetLevel,
                value = value,
                path = path,
                routeGroup = routeGroup,
                subSubscriber = subSubscriber
            )
        }
    }
}
