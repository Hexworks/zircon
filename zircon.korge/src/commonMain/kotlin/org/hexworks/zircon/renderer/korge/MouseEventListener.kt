package org.hexworks.zircon.renderer.korge

import korlibs.event.MouseEvent.Type.*
import korlibs.korge.input.MouseEvents
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.MouseEventType.*
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.internal.grid.InternalTileGrid

class MouseEventListener(
    private val tileGrid: InternalTileGrid
) {
    private val logger = LoggerFactory.getLogger(this::class)
    private val events = mutableListOf<Pair<org.hexworks.zircon.api.uievent.MouseEvent, UIEventPhase>>()

    private var lastMouseLocation = Position.unknown()

    fun drainEvents(): Iterable<Pair<org.hexworks.zircon.api.uievent.MouseEvent, UIEventPhase>> {
        return events.toList().also {
            events.clear()
        }
    }

    fun handleMouseEvent(events: MouseEvents) {
        events.apply {
            click { processMouseEvent(it) }
            over { processMouseEvent(it) }
            out { processMouseEvent(it) }
            move { processMouseEvent(it) }
            upAnywhere { processMouseEvent(it) }
            down { processMouseEvent(it) }
            downOutside { processMouseEvent(it) }
            scroll { processMouseEvent(it) }
        }
    }

    private fun processMouseEvent(e: MouseEvents) {
        try {

            val position = Position.create(
                x = 0.coerceAtLeast((e.currentPosLocal.x / tileGrid.tileset.width.toFloat()).toInt()),
                y = 0.coerceAtLeast((e.currentPosLocal.y / tileGrid.tileset.height.toFloat()).toInt())
            )

            val type = when (e.lastEvent.type) {
                MOVE -> MOUSE_MOVED
                DRAG -> MOUSE_DRAGGED
                UP -> MOUSE_RELEASED
                DOWN -> MOUSE_PRESSED
                CLICK -> MOUSE_CLICKED
                ENTER -> MOUSE_ENTERED
                EXIT -> MOUSE_EXITED
                SCROLL -> if (e.scrollDeltaYPages < 0f) MOUSE_WHEEL_ROTATED_DOWN else MOUSE_WHEEL_ROTATED_UP
            }

            org.hexworks.zircon.api.uievent.MouseEvent(
                type = type,
                button = e.button.id,
                position = position
            ).let {
                if (mouseMovedToNewPosition(type, position)
                        .or(isNotMoveEvent(type))
                ) {
                    lastMouseLocation = position
                    logger.debug { "Processing Mouse Event: $it." }
                    events.add(it to UIEventPhase.TARGET)
                }
            }
        } catch (e: Exception) {
            logger.error {
                "Position for mouse event '$e' was out of bounds. It is dropped."
            }
            e.printStackTrace()
        }
    }

    private fun isNotMoveEvent(eventType: MouseEventType) = eventType != MOUSE_MOVED

    private fun mouseMovedToNewPosition(eventType: MouseEventType, position: Position) =
        eventType in setOf(MOUSE_MOVED, MOUSE_DRAGGED) && position != lastMouseLocation

}