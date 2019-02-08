package org.hexworks.zircon.internal.screen

import org.hexworks.cobalt.datatypes.factory.IdentifierFactory
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.cobalt.events.api.subscribe
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.modal.Modal
import org.hexworks.zircon.api.component.modal.ModalResult
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.extensions.onKeyboardEvent
import org.hexworks.zircon.api.extensions.onMouseEvent
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.api.uievent.*
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
import org.hexworks.zircon.internal.extensions.cancelAll
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.grid.RectangleTileGrid
import org.hexworks.zircon.internal.uievent.UIEventProcessor

class TileGridScreen(
        private val tileGrid: InternalTileGrid,
        private val componentContainer: CompositeComponentContainer =
                buildCompositeContainer(tileGrid),
        private val subscriptions: MutableList<Subscription> = mutableListOf(),
        private val bufferGrid: InternalTileGrid = RectangleTileGrid(
                tileset = tileGrid.currentTileset(),
                size = tileGrid.size,
                layerable = ComponentsLayerable(
                        layerable = DefaultLayerable(),
                        components = componentContainer)),
        private val eventProcessor: UIEventProcessor = UIEventProcessor.createDefault())
    : InternalScreen,
        TileGrid by bufferGrid,
        InternalComponentContainer by componentContainer {

    override val id = IdentifierFactory.randomIdentifier()

    private var activeScreenId = IdentifierFactory.randomIdentifier()

    init {
        applyColorTheme(ColorThemeResource.EMPTY.getTheme())
        val debug = RuntimeConfig.config.debugMode
        MouseEventType.values().forEach { eventType ->
            tileGrid.onMouseEvent(eventType) { event, phase ->
                if (isActive()) {
                    process(event, phase)
                } else Pass
            }.also { subscriptions.add(it) }
        }
        KeyboardEventType.values().forEach { eventType ->
            tileGrid.onKeyboardEvent(eventType) { event, phase ->
                if (isActive()) {
                    process(event, phase)
                } else Pass
            }.also { subscriptions.add(it) }
        }
        Zircon.eventBus.subscribe<ZirconEvent.ScreenSwitch>(ZirconScope) { (screenId) ->
            if (debug) println("Screen switch event received. screenId: '$screenId'.")
            activeScreenId = screenId
            if (id != screenId) {
                if (debug) println("Deactivating screen")
                deactivate()
            }
        }.also { subscriptions.add(it) }
        Zircon.eventBus.subscribe<ZirconEvent.RequestCursorAt>(ZirconScope) { (position) ->
            if (isActive()) {
                tileGrid.setCursorVisibility(true)
                tileGrid.putCursorAt(position)
            }
        }.also { subscriptions.add(it) }
        Zircon.eventBus.subscribe<ZirconEvent.HideCursor>(ZirconScope) {
            if (isActive()) {
                tileGrid.setCursorVisibility(false)
            }
        }.also { subscriptions.add(it) }
    }

    override fun process(event: UIEvent, phase: UIEventPhase): UIEventResponse {
        return if (isActive()) {
            eventProcessor.process(event, phase)
                    .pickByPrecedence(componentContainer.dispatch(event))
        } else Pass
    }

    override fun onMouseEvent(eventType: MouseEventType, handler: MouseEventHandler): Subscription {
        return eventProcessor.onMouseEvent(eventType) { event, phase ->
            if (componentContainer.isMainContainerActive()) {
                handler.handle(event, phase)
            } else Pass
        }
    }

    override fun onKeyboardEvent(eventType: KeyboardEventType, handler: KeyboardEventHandler): Subscription {
        return eventProcessor.onKeyboardEvent(eventType) { event, phase ->
            if (componentContainer.isMainContainerActive()) {
                handler.handle(event, phase)
            } else Pass
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

    override fun close() {
        deactivate()
        subscriptions.cancelAll()
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

