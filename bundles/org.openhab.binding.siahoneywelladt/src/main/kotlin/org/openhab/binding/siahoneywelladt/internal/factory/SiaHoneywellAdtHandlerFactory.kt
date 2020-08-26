package org.openhab.binding.siahoneywelladt.internal.factory

import org.eclipse.jdt.annotation.NonNullByDefault
import org.eclipse.smarthome.core.thing.Bridge
import org.eclipse.smarthome.core.thing.Thing
import org.eclipse.smarthome.core.thing.ThingTypeUID
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory
import org.eclipse.smarthome.core.thing.binding.ThingHandler
import org.eclipse.smarthome.core.thing.binding.ThingHandlerFactory
import org.eclipse.smarthome.io.transport.serial.SerialPortManager
import org.openhab.binding.siahoneywelladt.internal.SiaHoneywellAdtBindingConstants
import org.openhab.binding.siahoneywelladt.internal.handler.AreaHandler
import org.openhab.binding.siahoneywelladt.internal.handler.SerialBridgeHandler
import org.openhab.binding.siahoneywelladt.internal.handler.ZoneHandler
import org.osgi.service.component.annotations.Activate
import org.osgi.service.component.annotations.Component
import org.osgi.service.component.annotations.Reference

@NonNullByDefault
@Component(configurationPid = ["binding.siahoneywelladt"], service = [ThingHandlerFactory::class])
class SiaHoneywellAdtHandlerFactory @Activate constructor(@param:Reference private val serialPortManager: SerialPortManager) :
    BaseThingHandlerFactory() {

    companion object {
        private val SUPPORTED_THING_TYPES_UIDS = setOf(
            SiaHoneywellAdtBindingConstants.THING_TYPE_SERIAL_BRIDGE,
            SiaHoneywellAdtBindingConstants.THING_TYPE_AREA,
            SiaHoneywellAdtBindingConstants.THING_TYPE_ZONE
        )
    }

    override fun supportsThingType(thingTypeUID: ThingTypeUID): Boolean {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID)
    }

    override fun createHandler(thing: Thing): ThingHandler? {
        val thingTypeUID = thing.thingTypeUID
        return when (thingTypeUID){
            SiaHoneywellAdtBindingConstants.THING_TYPE_SERIAL_BRIDGE -> SerialBridgeHandler(
                (thing as Bridge),
                serialPortManager
            )
            SiaHoneywellAdtBindingConstants.THING_TYPE_AREA -> AreaHandler(thing)
            SiaHoneywellAdtBindingConstants.THING_TYPE_ZONE -> ZoneHandler(thing)
            else -> throw IllegalArgumentException("The thing type $thingTypeUID is not supported by the KNX binding.")
        }
    }


}
