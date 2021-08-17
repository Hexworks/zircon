package org.hexworks.zircon.internal.screen

import org.hexworks.cobalt.core.platform.factory.UUIDFactory
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.cobalt.events.api.simpleSubscribeTo
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.modal.Modal
import org.hexworks.zircon.api.component.modal.ModalResult
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.abbreviate
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.UIEvent
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.internal.application.InternalApplication
import org.hexworks.zircon.internal.behavior.impl.ComponentsLayerable
import org.hexworks.zircon.internal.behavior.impl.ThreadSafeLayerable
import org.hexworks.zircon.internal.component.InternalComponentContainer
import org.hexworks.zircon.internal.component.impl.ModalComponentContainer
import org.hexworks.zircon.internal.component.modal.DefaultModal
import org.hexworks.zircon.internal.event.ZirconEvent.HideCursor
import org.hexworks.zircon.internal.event.ZirconEvent.RequestCursorAt
import org.hexworks.zircon.internal.event.ZirconEvent.ScreenSwitch
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.graphics.Renderable
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.grid.ThreadSafeTileGrid
import org.hexworks.zircon.internal.uievent.UIEventProcessor
import kotlin.jvm.Synchronized

class TileGridScreen(
    private val tileGrid: InternalTileGrid,
    private val componentContainer: ModalComponentContainer =
        buildComponentContainer(
            initialSize = tileGrid.size,
            initialTileset = tileGrid.tileset,
            initialTheme = tileGrid.config.defaultColorTheme,
            application = tileGrid.application
        ),
    private val bufferGrid: InternalTileGrid = ThreadSafeTileGrid(
        config = tileGrid.config,
        layerable = ComponentsLayerable(
            componentContainer = componentContainer,
            layerable = ThreadSafeLayerable(
                initialSize = tileGrid.size
            )
        )
    ),
    private val eventProcessor: UIEventProcessor = UIEventProcessor.createDefault()
) : InternalScreen,
    InternalTileGrid by bufferGrid,
    InternalComponentContainer by componentContainer {

    override val renderables: List<Renderable>
        get() = bufferGrid.renderables

    // we make this random because we don't know which one is the active
    // yet, and we only need this to determine whether this Screen is the active
    // one or not, so a random id will do fine by default
    private var activeScreenId = UUIDFactory.randomUUID()
    private val id = UUIDFactory.randomUUID()

    override val application: InternalApplication = tileGrid.application.asInternal()

    private val eventBus = application.eventBus
    private val eventScope = application.eventScope

    val root = componentContainer.flattenedTree.first()

    init {
        eventBus.simpleSubscribeTo<ScreenSwitch>(eventScope) { (screenId) ->
            LOGGER.debug("Screen switch event received (id=${screenId.abbreviate()}) in screen object (id=${id.abbreviate()}).")
            activeScreenId = screenId
            if (id != activeScreenId && isActive.value) {
                LOGGER.debug("Deactivating screen (id=${id.abbreviate()}).")
                deactivate()
            }
        }.disposeWhen(closedValue)
    }

    // note that events / event listeners on the screen itself are only handled
    // if the main container is active (otherwise a modal is open and they would
    // have no visible effect)
    @Synchronized
    override fun process(event: UIEvent, phase: UIEventPhase): UIEventResponse {
        return if (isActive.value) {
            LOGGER.debug("Processing event $event in phase $phase for screen $this.")
            // note that first we process listeners on the Screen itself
            // then component ones. They don't affect each other
            (if (componentContainer.isMainContainerActive()) {
                eventProcessor.process(event, phase)
            } else Pass).pickByPrecedence(componentContainer.dispatch(event))
        } else Pass
    }

    override fun handleMouseEvents(
        eventType: MouseEventType,
        handler: (event: MouseEvent, phase: UIEventPhase) -> UIEventResponse
    ): Subscription {
        return eventProcessor.handleMouseEvents(eventType) { event, phase ->
            if (componentContainer.isMainContainerActive()) {
                handler(event, phase)
            } else Pass
        }
    }

    override fun handleKeyboardEvents(
        eventType: KeyboardEventType,
        handler: (event: KeyboardEvent, phase: UIEventPhase) -> UIEventResponse
    ): Subscription {
        return eventProcessor.handleKeyboardEvents(eventType) { event, phase ->
            if (componentContainer.isMainContainerActive()) {
                handler(event, phase)
            } else Pass
        }
    }

    @Synchronized
    override fun display() {
        if (activeScreenId != id) {
            eventBus.publish(
                event = ScreenSwitch(id, this),
                eventScope = eventScope
            )
            activate()
            MouseEventType.values().forEach { eventType ->
                tileGrid.handleMouseEvents(eventType) { event, phase ->
                    process(event, phase)
                }.keepWhile(isActive)
            }
            KeyboardEventType.values().forEach { eventType ->
                tileGrid.handleKeyboardEvents(eventType) { event, phase ->
                    process(event, phase)
                }.keepWhile(isActive)
            }
            eventBus.simpleSubscribeTo<RequestCursorAt>(eventScope) { (position) ->
                tileGrid.isCursorVisible = true
                tileGrid.cursorPosition = position
            }.keepWhile(isActive)
            eventBus.simpleSubscribeTo<HideCursor>(eventScope) {
                tileGrid.isCursorVisible = false
            }.keepWhile(isActive)
            tileGrid.delegateTo(bufferGrid)
        }
    }

    @Synchronized
    override fun close() {
        bufferGrid.close()
        deactivate()
    }

    override fun <T : ModalResult> openModal(modal: Modal<T>) {
        require(modal is DefaultModal) {
            "This Screen does not accept custom Modals yet."
        }
        componentContainer.addModal(modal)
    }

    companion object {

        private val LOGGER = LoggerFactory.getLogger(TileGrid::class)

        private fun buildComponentContainer(
            initialSize: Size,
            initialTileset: TilesetResource,
            initialTheme: ColorTheme,
            application: Application
        ): ModalComponentContainer {
            val metadata = ComponentMetadata(
                relativePosition = Position.defaultPosition(),
                size = initialSize,
                name = "Modal Component Container",
                tilesetProperty = initialTileset.toProperty(),
                themeProperty = initialTheme.toProperty()
            )
            return ModalComponentContainer(
                metadata = metadata,
                application = application
            )
        }
    }
}

