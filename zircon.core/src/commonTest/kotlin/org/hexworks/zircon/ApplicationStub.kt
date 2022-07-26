package org.hexworks.zircon

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.RenderData
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.application.InternalApplication
import org.hexworks.zircon.internal.event.ZirconScope

class ApplicationStub : InternalApplication {

    override var config: AppConfig = AppConfig.defaultConfiguration()
    override var eventBus: EventBus = EventBus.create()
    override var eventScope: ZirconScope = ZirconScope()
    override lateinit var tileGrid: TileGrid

    override val closedValue: Property<Boolean> = false.toProperty()
    override fun close() {
        closedValue.value = true
    }


    override fun asInternal() = this

    override fun beforeRender(listener: (RenderData) -> Unit): Subscription {
        error("not implemented")
    }

    override fun afterRender(listener: (RenderData) -> Unit): Subscription {
        error("not implemented")
    }
}
