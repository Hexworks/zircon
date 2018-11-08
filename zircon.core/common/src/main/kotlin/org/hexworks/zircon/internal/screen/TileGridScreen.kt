package org.hexworks.zircon.internal.screen

import org.hexworks.cobalt.datatypes.factory.IdentifierFactory
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.behavior.impl.ComponentsLayerable
import org.hexworks.zircon.internal.behavior.impl.DefaultLayerable
import org.hexworks.zircon.internal.component.InternalComponentContainer
import org.hexworks.zircon.internal.component.impl.DefaultComponentContainer
import org.hexworks.zircon.internal.component.impl.RootContainer
import org.hexworks.zircon.internal.component.renderer.RootContainerRenderer
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.grid.RectangleTileGrid

class TileGridScreen(
        private val tileGrid: TileGrid,
        private val componentsContainer: RootContainer = RootContainer(
                componentMetadata = ComponentMetadata(
                        size = tileGrid.size,
                        position = Position.defaultPosition(),
                        tileset = tileGrid.currentTileset(),
                        componentStyleSet = ComponentStyleSet.defaultStyleSet()),
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = listOf(),
                        componentRenderer = RootContainerRenderer())),
        private val buffer: InternalTileGrid = RectangleTileGrid(
                tileset = tileGrid.currentTileset(),
                size = tileGrid.size,
                layerable = ComponentsLayerable(
                        layerable = DefaultLayerable(),
                        components = componentsContainer)),
        private val containerHandler: InternalComponentContainer =
                DefaultComponentContainer(componentsContainer))
    : InternalScreen,
        TileGrid by buffer,
        InternalComponentContainer by containerHandler {

    override val id = IdentifierFactory.randomIdentifier()
    var activeScreenId = IdentifierFactory.randomIdentifier()

    init {
        applyColorTheme(ColorThemeResource.EMPTY.getTheme())
        val debug = RuntimeConfig.config.debugMode
        require(tileGrid is InternalTileGrid) {
            "The supplied TileGrid is not an instance of InternalTileGrid."
        }
        Zircon.eventBus.subscribe<ZirconEvent.ScreenSwitch>(ZirconScope) { (screenId) ->
            if (debug) println("Screen switch event received. screenId: '$screenId'.")
            activeScreenId = screenId
            if (id != screenId) {
                if (debug) println("Deactivating screen")
                deactivate()
            }
        }
        Zircon.eventBus.subscribe<ZirconEvent.RequestCursorAt>(ZirconScope) { (position) ->
            if (isActive()) {
                tileGrid.setCursorVisibility(true)
                tileGrid.putCursorAt(position)
            }
        }
        Zircon.eventBus.subscribe<ZirconEvent.HideCursor>(ZirconScope) {
            if (isActive()) {
                tileGrid.setCursorVisibility(false)
            }
        }
    }

    override fun display() {
        if (activeScreenId != id) {
            Zircon.eventBus.broadcast(
                    event = ZirconEvent.ScreenSwitch(id),
                    eventScope = ZirconScope)
            setCursorVisibility(false)
            putCursorAt(Position.defaultPosition())
            activate()
            (tileGrid as InternalTileGrid).useContentsOf(buffer)
        }
    }

}
