package org.hexworks.zircon.internal.application

import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconScope

/**
 * This interface represents the internal API of [Application].
 */
interface InternalApplication : Application {

    /**
     * The configuration this [Application] uses
     */
    val config: AppConfig

    /**
     * The [EventBus] can be used to send events to Zircon.
     * @see ZirconEvent for more details
     * @see ZirconScope
     */
    val eventBus: EventBus

    /**
     * Use this [ZirconScope] to send publish events using the [eventBus]
     */
    val eventScope: ZirconScope
}
