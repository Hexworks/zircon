package org.codetome.zircon.screen.impl

import org.codetome.zircon.Position
import org.codetome.zircon.api.LayerBuilder
import org.codetome.zircon.api.StyleSetBuilder
import org.codetome.zircon.behavior.*
import org.codetome.zircon.component.ComponentStyles
import org.codetome.zircon.component.ContainerHandler
import org.codetome.zircon.component.impl.DefaultContainer
import org.codetome.zircon.component.impl.DefaultContainerHandler
import org.codetome.zircon.event.EventBus
import org.codetome.zircon.event.EventType
import org.codetome.zircon.screen.Screen
import org.codetome.zircon.terminal.Terminal
import org.codetome.zircon.terminal.virtual.VirtualTerminal
import java.io.Closeable
import java.util.*

/**
 * This class implements the logic defined in the [Screen] interface.
 * It keeps data structures for the front- and back buffers, the cursor location and
 * some other simpler states.
 */
class TerminalScreen private constructor(private val terminal: Terminal,
                                         private val backend: VirtualTerminal,
                                         private val containerHandler: ContainerHandler)
    : Screen,
        Closeable by backend,
        Clearable by backend,
        Layerable by backend,
        CursorHandler by backend,
        DrawSurface by backend,
        InputEmitter by backend,
        ContainerHandler by containerHandler {

    private val id: UUID = UUID.randomUUID()

    constructor(terminal: Terminal) : this(
            terminal = terminal,
            backend = VirtualTerminal(terminal.getBoundableSize()),
            containerHandler = DefaultContainerHandler(DefaultContainer(
                    initialSize = terminal.getBoundableSize(),
                    position = Position.DEFAULT_POSITION,
                    componentStyles = ComponentStyles(StyleSetBuilder.EMPTY))))

    init {
        EventBus.subscribe<UUID>(EventType.SCREEN_SWITCH, { (screenId) ->
            if (id != screenId) {
                deactivate()
            }
        })
        EventBus.subscribe<Unit>(EventType.COMPONENT_CHANGE, {
            if (isActive()) {
                display()
            }
        })
    }

    override fun containsBoundable(boundable: Boundable) = backend.containsBoundable(boundable)

    override fun intersects(boundable: Boundable) = backend.intersects(boundable)

    override fun containsPosition(position: Position) = backend.containsPosition(position)

    override fun getBoundableSize() = backend.getBoundableSize()

    override fun getPosition() = backend.getPosition()

    override fun getId() = id

    override fun display() {
        EventBus.emit(EventType.SCREEN_SWITCH, id)
        flipBuffers(true)
        activate()
    }

    override fun refresh() {
        flipBuffers(false)
    }

    @Synchronized
    private fun flipBuffers(forceRedraw: Boolean) {
        val positions = if (forceRedraw) {
            getBoundableSize().fetchPositions()
        } else {
            drainDirtyPositions()
        }
        positions.forEach { position ->
            val character = backend.getCharacterAt(position).get()
            terminal.setCharacterAt(position, character)
        }
        terminal.drainLayers()
        terminal.addLayer(LayerBuilder.newBuilder()
                .textImage(drawComponentsToImage())
                .build())
        backend.drainLayers().forEach {
            terminal.addLayer(it)
        }
        terminal.flush()
    }
}
