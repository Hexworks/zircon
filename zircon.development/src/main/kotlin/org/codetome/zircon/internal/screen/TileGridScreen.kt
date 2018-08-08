package org.codetome.zircon.internal.screen

import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.event.EventBus
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.api.util.Identifier
import org.codetome.zircon.internal.behavior.impl.ComponentsLayerable
import org.codetome.zircon.internal.behavior.impl.DefaultLayerable
import org.codetome.zircon.internal.component.InternalContainerHandler
import org.codetome.zircon.internal.component.impl.DefaultContainer
import org.codetome.zircon.internal.component.impl.DefaultContainerHandler
import org.codetome.zircon.internal.config.RuntimeConfig
import org.codetome.zircon.internal.event.InternalEvent
import org.codetome.zircon.internal.grid.InternalTileGrid
import org.codetome.zircon.internal.grid.RectangleTileGrid

class TileGridScreen(
        private val tileGrid: TileGrid,
        private val componentsContainer: DefaultContainer = DefaultContainer(
                initialSize = tileGrid.getBoundableSize(),
                position = Position.defaultPosition(),
                componentStyleSet = ComponentStyleSet.defaultStyleSet(),
                initialTileset = tileGrid.tileset()),
        private val buffer: InternalTileGrid = RectangleTileGrid(
                tileset = tileGrid.tileset(),
                size = tileGrid.getBoundableSize(),
                layerable = ComponentsLayerable(
                        layers = DefaultLayerable(tileGrid.getBoundableSize()),
                        components = componentsContainer)),
        private val containerHandler: InternalContainerHandler =
                DefaultContainerHandler(componentsContainer))
    : InternalScreen,
        TileGrid by buffer,
        InternalContainerHandler by containerHandler {

    override val id = Identifier.randomIdentifier()

    init {
        val debug = RuntimeConfig.config.debugMode
        require(tileGrid is InternalTileGrid) {
            "The supplied TileGrid is not an instance of InternalTileGrid."
        }
        EventBus.subscribe<InternalEvent.ScreenSwitch> { (screenId) ->
            if (debug) println("Screen switch event received. screenId: '$screenId'.")
            if (id != screenId) {
                if (debug) println("Deactivating screen")
                deactivate()
            }
        }
        EventBus.subscribe<InternalEvent.RequestCursorAt> { (position) ->
            if (isActive()) {
                tileGrid.setCursorVisibility(true)
                tileGrid.putCursorAt(position)
            }
        }
        EventBus.subscribe<InternalEvent.HideCursor> {
            if (isActive()) {
                tileGrid.setCursorVisibility(false)
            }
        }
    }

    override fun display() {
        EventBus.broadcast(InternalEvent.ScreenSwitch(id))
        setCursorVisibility(false)
        putCursorAt(Position.defaultPosition())
        activate()
        (tileGrid as InternalTileGrid).useContentsOf(buffer)
    }

}
