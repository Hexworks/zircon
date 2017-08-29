package org.codetome.zircon.internal.screen

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.screen.Screen
import org.codetome.zircon.api.terminal.Terminal
import org.codetome.zircon.internal.component.ContainerHandler
import org.codetome.zircon.internal.component.impl.DefaultContainer
import org.codetome.zircon.internal.component.impl.DefaultContainerHandler
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import org.codetome.zircon.internal.terminal.virtual.VirtualTerminal
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
        Terminal by backend,
        ContainerHandler by containerHandler {

    private val id: UUID = UUID.randomUUID()

    constructor(terminal: Terminal) : this(
            terminal = terminal,
            backend = VirtualTerminal(terminal.getBoundableSize()),
            containerHandler = DefaultContainerHandler(DefaultContainer(
                    initialSize = terminal.getBoundableSize(),
                    position = Position.DEFAULT_POSITION,
                    componentStyles = ComponentStylesBuilder.DEFAULT)))

    init {
        EventBus.subscribe<UUID>(EventType.ScreenSwitch, { (screenId) ->
            if (id != screenId) {
                deactivate()
            }
        })
        EventBus.subscribe<Unit>(EventType.ComponentChange, {
            if (isActive()) {
                refresh()
            }
        })
        EventBus.subscribe<Position>(EventType.RequestCursorAt, { (position) ->
            if (isActive()) {
                println("putting cursor at $position")
                backend.setCursorVisible(true)
                putCursorAt(position)
            }
        })
        EventBus.subscribe(EventType.HideCursor, {
            if (isActive()) {
                backend.setCursorVisible(false)
            }
        })
    }

    override fun getId() = id

    @Synchronized
    override fun display() {
        EventBus.emit(EventType.ScreenSwitch, id)
        flipBuffers(true)
        activate()
    }

    @Synchronized
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
            val character = backend.getCharacterAt(position).get()
            terminal.setCharacterAt(position, character)
        }
        // TODO: only do this when forceRedraw is true
        terminal.drainLayers()
        transformComponentsToLayers().forEach {
            terminal.addLayer(it)
        }
        backend.drainLayers().forEach {
            terminal.addLayer(it)
        }
        terminal.flush()
    }
}
