package org.hexworks.zircon.internal.renderer

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.ApplicationStub
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.NoOpApplication
import org.hexworks.zircon.api.application.RenderData
import org.hexworks.zircon.api.behavior.Clearable
import org.hexworks.zircon.api.component.ComponentContainer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.uievent.UIEvent
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.api.view.base.BaseView
import org.hexworks.zircon.internal.behavior.RenderableContainer
import org.hexworks.zircon.internal.graphics.FastTileGraphics
import org.hexworks.zircon.internal.grid.DefaultTileGrid
import org.hexworks.zircon.internal.uievent.UIEventDispatcher

/**
 * This is a simple test renderer that draws things back into the provided [tileGraphics]. After instantiation,
 * you should call [withComponentContainer] to add components and fragments, and then call [render] to see the result.
 * You can also [dispatch] events to interact with it.
 *
 * @sample org.hexworks.zircon.internal.renderer.TestRendererTest.tinyExample
 */
class TestRenderer(
    config: AppConfig,
    private val tileGraphics: TileGraphics,
) : UIEventDispatcher, Renderer<Any, ApplicationStub, Any>, Clearable {
    private val tileGrid: TileGrid = DefaultTileGrid(config).apply {
        application = ApplicationStub()
    }
    private val mainView = object : BaseView(tileGrid) {}
    private val closedValueProperty: Property<Boolean> = false.toProperty()
    override val closedValue: ObservableValue<Boolean> get() = closedValueProperty

    init {
        mainView.dock()
    }

    fun withComponentContainer(cb: ComponentContainer.() -> Unit) {
        with(mainView.screen, cb)
    }

    override fun create() {
    }

    override fun beforeRender(listener: (RenderData) -> Unit): Subscription {
        return NoOpApplication.NoOpSubscription
    }

    override fun afterRender(listener: (RenderData) -> Unit): Subscription {
        return NoOpApplication.NoOpSubscription
    }

    override fun clear() {
        tileGraphics.clear()
    }

    override fun render(context: Any) {
        (tileGrid as RenderableContainer).renderables.forEach { renderable ->
            if (!renderable.isHidden) {
                val graphics = FastTileGraphics(
                    initialSize = renderable.size,
                    initialTileset = renderable.tileset,
                    initialTiles = mapOf()
                )
                renderable.render(graphics)
                graphics.contents().forEach { (pos, tile) ->
                    tileGraphics.draw(tile, pos + renderable.position)
                }
            }
        }
    }

    override fun dispatch(event: UIEvent): UIEventResponse = (mainView.screen as UIEventDispatcher).dispatch(event)

    override fun close() {
        if (!closedValueProperty.value) {
            tileGrid.close()
            closedValueProperty.value = true
        }
    }
}
