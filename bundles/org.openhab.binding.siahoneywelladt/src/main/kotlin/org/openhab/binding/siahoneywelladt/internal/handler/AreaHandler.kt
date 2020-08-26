package org.openhab.binding.siahoneywelladt.internal.handler

import org.eclipse.jdt.annotation.NonNullByDefault
import org.eclipse.smarthome.core.thing.ChannelUID
import org.eclipse.smarthome.core.thing.Thing
import org.eclipse.smarthome.core.thing.ThingStatus
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler
import org.eclipse.smarthome.core.types.Command
import org.openhab.binding.siahoneywelladt.internal.config.AreaConfig
import org.openhab.binding.siahoneywelladt.internal.support.LoggerDelegate

@NonNullByDefault
class AreaHandler(thing: Thing): BaseThingHandler(thing) {
    private val logger by LoggerDelegate()
    private var config:AreaConfig?=null

    override fun handleCommand(p0: ChannelUID?, p1: Command?) {
        TODO("Not yet implemented")
    }

    override fun initialize() {
        this.config=getConfigAs(AreaConfig::class.java)
        updateStatus(ThingStatus.ONLINE)
    }

}



