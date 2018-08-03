package org.codetome.zircon.internal.screen

import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.api.screen.Screen
import org.codetome.zircon.api.util.Identifier
import org.codetome.zircon.internal.component.InternalContainerHandler
import org.codetome.zircon.internal.component.impl.DefaultContainer
import org.codetome.zircon.internal.component.impl.DefaultContainerHandler
import org.codetome.zircon.internal.event.Event
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.grid.InternalTileGrid
import org.codetome.zircon.internal.grid.RectangleTileGrid

class TileGridScreen(
        private val tileGrid: TileGrid,
        private val buffer: InternalTileGrid = RectangleTileGrid(
                tileset = tileGrid.tileset(),
                size = tileGrid.getBoundableSize()),
        private val containerHandler: InternalContainerHandler = DefaultContainerHandler(DefaultContainer(
                initialSize = tileGrid.getBoundableSize(),
                position = Position.defaultPosition(),
                componentStyleSet = ComponentStyleSet.defaultStyleSet(),
                initialTileset = tileGrid.tileset())))
    : Screen,
        TileGrid by buffer,
        InternalContainerHandler by containerHandler {

    override val id = Identifier.randomIdentifier()

    init {
        require(tileGrid is InternalTileGrid) {
            "The supplied TileGrid is not an instance of InternalTileGrid."
        }
        EventBus.subscribe<Event.ScreenSwitch> { (screenId) ->
            if (id != screenId) {
                deactivate()
            }
        }
        EventBus.subscribe<Event.ComponentChange> {
            if (isActive()) {
                display()
            }
        }
        EventBus.subscribe<Event.RequestCursorAt> { (position) ->
            if (isActive()) {
                tileGrid.setCursorVisibility(true)
                tileGrid.putCursorAt(position)
            }
        }
        EventBus.subscribe<Event.HideCursor> {
            if (isActive()) {
                tileGrid.setCursorVisibility(false)
            }
        }
    }

    override fun display() {
        transformComponentsToLayers().forEach {
            buffer.pushLayer(it)
        }
        (tileGrid as InternalTileGrid).useContentsOf(buffer)
    }

}
