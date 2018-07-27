package org.codetome.zircon.internal.screen

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.screen.Screen
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.api.util.Identifier
import org.codetome.zircon.internal.component.InternalContainerHandler
import org.codetome.zircon.internal.component.impl.DefaultContainer
import org.codetome.zircon.internal.component.impl.DefaultContainerHandler
import org.codetome.zircon.internal.event.Event
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.grid.InternalTileGrid
import org.codetome.zircon.internal.grid.virtual.VirtualTileGrid

/**
 * This class implements the logic defined in the [Screen] interface.
 * A [TileGridScreen] wraps a [TileGrid] and uses a [VirtualTileGrid] as a backend
 * for its changes. When `refresh` or `display` is called the changes are written to
 * the [TileGrid] this [TileGridScreen] wraps. This means that a [TileGridScreen] acts
 * as a double buffer for the wrapped [TileGrid].
 */
class TileGridScreen(private val terminal: InternalTileGrid,
                     private val backend: VirtualTileGrid = VirtualTileGrid(
                             initialSize = terminal.getBoundableSize(),
                             initialTileset = terminal.getCurrentFont()),
                     private val containerHandler: InternalContainerHandler = DefaultContainerHandler(DefaultContainer(
                             initialSize = terminal.getBoundableSize(),
                             position = Position.defaultPosition(),
                             componentStyleSet = ComponentStyleSet.defaultStyleSet(),
                             initialTileset = terminal.getCurrentFont())))
    : InternalScreen,
        InternalTileGrid by backend,
        InternalContainerHandler by containerHandler {

    private val id = Identifier.randomIdentifier()

    init {
        EventBus.subscribe<Event.ScreenSwitch> { (screenId) ->
            if (id != screenId) {
                deactivate()
            }
        }
        EventBus.subscribe<Event.ComponentChange> {
            if (isActive()) {
                refresh()
            }
        }
        EventBus.subscribe<Event.RequestCursorAt> { (position) ->
            if (isActive()) {
                terminal.setCursorVisibility(true)
                terminal.putCursorAt(position)
            }
        }
        EventBus.subscribe<Event.HideCursor> {
            if (isActive()) {
                terminal.setCursorVisibility(false)
            }
        }
    }

    override fun getId() = id

    override fun display() {
        EventBus.broadcast(Event.ScreenSwitch(id))
        setCursorVisibility(false)
        putCursorAt(Position.defaultPosition())
        flipBuffers(true)
        activate()
    }

    override fun refresh() {
        flipBuffers(false)
    }

    private fun flipBuffers(forceRedraw: Boolean) {
        val positions = if (forceRedraw) {
            getBoundableSize().fetchPositions()
        } else {
            drainDirtyPositions()
        }
        positions.forEach { position ->
            val character = backend.getTileAt(position).get()
            terminal.setTileAt(position, character)
        }
        // TODO: optimize this
        terminal.drainLayers()
        transformComponentsToLayers().forEach {
            terminal.pushLayer(it)
        }
        backend.getLayers().forEach {
            terminal.pushLayer(it)
        }
        if (hasOverrideFont()) {
            terminal.useFont(getCurrentFont())
        }
        terminal.flush()
    }
}
