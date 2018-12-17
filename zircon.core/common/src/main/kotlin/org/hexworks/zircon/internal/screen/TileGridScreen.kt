package org.hexworks.zircon.internal.screen

import org.hexworks.cobalt.datatypes.factory.IdentifierFactory
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.cobalt.events.api.subscribe
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.behavior.InputEmitter
import org.hexworks.zircon.api.behavior.base.BaseInputEmitter
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.modal.Modal
import org.hexworks.zircon.api.component.modal.ModalResult
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.listener.InputListener
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.behavior.impl.ComponentsLayerable
import org.hexworks.zircon.internal.behavior.impl.DefaultLayerable
import org.hexworks.zircon.internal.component.InternalComponentContainer
import org.hexworks.zircon.internal.component.impl.CompositeComponentContainer
import org.hexworks.zircon.internal.component.impl.DefaultComponentContainer
import org.hexworks.zircon.internal.component.impl.RootContainer
import org.hexworks.zircon.internal.component.renderer.RootContainerRenderer
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.grid.RectangleTileGrid

class TileGridScreen(
        private val tileGrid: InternalTileGrid,
        private val componentContainer: CompositeComponentContainer =
                buildCompositeContainer(tileGrid),
        private val inputEmitter: InputEmitter = object : BaseInputEmitter() {
            override fun onInput(listener: InputListener): Subscription {
                return Zircon.eventBus.subscribe<ZirconEvent.Input>(ZirconScope) { (input) ->
                    if (componentContainer.isMainContainerActive()) {
                        listener.inputEmitted(input)
                    }
                }
            }
        },
        private val bufferGrid: InternalTileGrid = RectangleTileGrid(
                tileset = tileGrid.currentTileset(),
                size = tileGrid.size,
                layerable = ComponentsLayerable(
                        layerable = DefaultLayerable(),
                        components = componentContainer),
                inputEmitter = inputEmitter))
    : InternalScreen,
        TileGrid by bufferGrid,
        InternalComponentContainer by componentContainer {

    override val id = IdentifierFactory.randomIdentifier()
    var activeScreenId = IdentifierFactory.randomIdentifier()

    private val logger = LoggerFactory.getLogger(this::class)

    init {
        applyColorTheme(ColorThemeResource.EMPTY.getTheme())
        val debug = RuntimeConfig.config.debugMode
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

    override fun onInput(listener: InputListener): Subscription {
        return Zircon.eventBus.subscribe<ZirconEvent.Input>(ZirconScope) { (input) ->
            logger.info("Input arrived.")
            if (componentContainer.isMainContainerActive()) {
                logger.info("Main container is active.")
                listener.inputEmitted(input)
            }
        }
    }

    override fun display() {
        if (activeScreenId != id) {
            Zircon.eventBus.publish(
                    event = ZirconEvent.ScreenSwitch(id),
                    eventScope = ZirconScope)
            setCursorVisibility(false)
            putCursorAt(Position.defaultPosition())
            activate()
            tileGrid.delegateActionsTo(bufferGrid)
        }
    }

    override fun <T : ModalResult> openModal(modal: Modal<T>) {
        componentContainer.addModal(modal)
    }

    companion object {
        private fun buildCompositeContainer(tileGrid: InternalTileGrid): CompositeComponentContainer {
            val metadata = ComponentMetadata(
                    size = tileGrid.size,
                    position = Position.defaultPosition(),
                    tileset = tileGrid.currentTileset(),
                    componentStyleSet = ComponentStyleSet.defaultStyleSet())
            val renderingStrategy = DefaultComponentRenderingStrategy(
                    decorationRenderers = listOf(),
                    componentRenderer = RootContainerRenderer())
            val componentContainer = DefaultComponentContainer(
                    root = RootContainer(
                            componentMetadata = metadata,
                            renderingStrategy = renderingStrategy))
            val modalContainer = DefaultComponentContainer(
                    root = RootContainer(
                            componentMetadata = metadata,
                            renderingStrategy = renderingStrategy))
            modalContainer.applyColorTheme(ColorThemeResource.EMPTY.getTheme())
            return CompositeComponentContainer(
                    mainContainer = componentContainer,
                    modalContainer = modalContainer)
        }
    }

}

