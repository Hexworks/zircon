package org.hexworks.zircon.internal

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.platform.util.ZirconDispatchers

object Zircon {

    val dispatcher = ZirconDispatchers.single()

    /**
     * Can be used to send events to Zircon.
     */
    val eventBus: EventBus = EventBus.create()

    val scope: CoroutineScope = CoroutineScope(dispatcher + SupervisorJob())

}
