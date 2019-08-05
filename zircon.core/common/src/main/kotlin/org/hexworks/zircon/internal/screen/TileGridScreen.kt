package org.hexworks.zircon.internal.screen

import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.cobalt.events.api.subscribe
import org.hexworks.cobalt.factory.IdentifierFactory
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.modal.Modal
import org.hexworks.zircon.api.component.modal.ModalResult
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.extensions.abbreviate
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.behavior.impl.ComponentsLayerable
import org.hexworks.zircon.internal.behavior.impl.DefaultLayerable
import org.hexworks.zircon.internal.component.InternalComponentContainer
import org.hexworks.zircon.internal.component.impl.CompositeComponentContainer
import org.hexworks.zircon.internal.component.modal.DefaultModal
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
        MouseEventType.values().forEach { eventType ->
            tileGrid.handleMouseEvents(eventType) { event, phase ->
                if (isActive()) {
                    process(event, phase)
                } else Pass
            }.also { subscriptions.add(it) }
        }
        KeyboardEventType.values().forEach { eventType ->
            tileGrid.handleKeyboardEvents(eventType) { event, phase ->
                if (isActive()) {
                    process(event, phase)
                } else Pass
            }.also { subscriptions.add(it) }
        }
        Zircon.eventBus.subscribe<ZirconEvent.ScreenSwitch>(ZirconScope) { (screenId) ->
            LOGGER.debug("Screen switch event received (id=${screenId.abbreviate()}) in screen object (id=${id.abbreviate()}).")
            activeScreenId = screenId
            if (id != screenId) {
                LOGGER.debug("Deactivating screen (id=${id.abbreviate()}).")
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


    // note that events / event listeners on the screen itself are only handled
    // if the main container is active (otherwise a modal is open and they would
    // have no visible effect)
    override fun process(event: UIEvent, phase: UIEventPhase): UIEventResponse {
        return if (isActive()) {
            // note that first we process listeners on the Screen itself
            // then component ones. They don't affect each other
            (if (componentContainer.isMainContainerActive()) {
                eventProcessor.process(event, phase)
            } else Pass).pickByPrecedence(componentContainer.dispatch(event))
        } else Pass
    }

    override fun handleMouseEvents(
            eventType: MouseEventType,
            handler: (event: MouseEvent, phase: UIEventPhase) -> UIEventResponse): Subscription {
        return eventProcessor.handleMouseEvents(eventType) { event, phase ->
            if (componentContainer.isMainContainerActive()) {
                handler(event, phase)
            } else Pass
        }
    }

    override fun handleKeyboardEvents(
            eventType: KeyboardEventType,
            handler: (event: KeyboardEvent, phase: UIEventPhase) -> UIEventResponse): Subscription {
        return eventProcessor.handleKeyboardEvents(eventType) { event, phase ->
            if (componentContainer.isMainContainerActive()) {
                handler(event, phase)
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
            tileGrid.delegateTo(bufferGrid)
        }
    }

    override fun close() {
        deactivate()
        subscriptions.cancelAll()
    }

    override fun <T : ModalResult> openModal(modal: Modal<T>) {
        require(modal is DefaultModal) {
            "This Screen does not accept custom Modals yet."
        }
        componentContainer.addModal(modal)
    }

    companion object {

        private val LOGGER = LoggerFactory.getLogger(TileGrid::class)

        private fun buildCompositeContainer(tileGrid: InternalTileGrid): CompositeComponentContainer {
            val metadata = ComponentMetadata(
                    size = tileGrid.size,
                    position = Position.defaultPosition(),
                    tileset = tileGrid.currentTileset(),
                    componentStyleSet = ComponentStyleSet.defaultStyleSet())
            return CompositeComponentContainer(metadata = metadata)
        }
    }
}

