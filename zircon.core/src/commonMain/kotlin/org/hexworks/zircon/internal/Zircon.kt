package org.hexworks.zircon.internal

import org.hexworks.cobalt.events.api.EventBus

object Zircon {

    /**
     * Can be used to send events to Zircon.
     */
    val eventBus: EventBus = EventBus.create()

}
