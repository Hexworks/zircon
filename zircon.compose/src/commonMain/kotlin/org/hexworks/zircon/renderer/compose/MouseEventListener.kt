@file:OptIn(androidx.compose.ui.ExperimentalComposeUiApi::class)

package org.hexworks.zircon.renderer.compose

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerButton
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.api.uievent.MouseEvent as ZirconMouseEvent

/**
 * Bridges Compose pointer events to Zircon mouse events.
 */
class MouseEventListener(
    private val tileGrid: InternalTileGrid
) {

    private val logger = LoggerFactory.getLogger(this::class)
    private val events = mutableListOf<Pair<ZirconMouseEvent, UIEventPhase>>()

    private var lastMouseLocation = Position.UNKNOWN
    private var isDragging = false

    /**
     * Drains all accumulated mouse events.
     */
    fun drainEvents(): Iterable<Pair<ZirconMouseEvent, UIEventPhase>> {
        return events.toList().also {
            events.clear()
        }
    }

    /**
     * Handles a Compose PointerEvent and converts it to Zircon format.
     */
    fun handlePointerEvent(event: PointerEvent) {
        try {
            val change = event.changes.firstOrNull() ?: return

            val position = calculateGridPosition(change.position)

            val type = when (event.type) {
                PointerEventType.Move -> {
                    if (isDragging) MouseEventType.MOUSE_DRAGGED
                    else MouseEventType.MOUSE_MOVED
                }
                PointerEventType.Press -> {
                    isDragging = true
                    MouseEventType.MOUSE_PRESSED
                }
                PointerEventType.Release -> {
                    isDragging = false
                    MouseEventType.MOUSE_RELEASED
                }
                PointerEventType.Enter -> MouseEventType.MOUSE_ENTERED
                PointerEventType.Exit -> MouseEventType.MOUSE_EXITED
                PointerEventType.Scroll -> {
                    val scrollDelta = change.scrollDelta
                    if (scrollDelta.y < 0) MouseEventType.MOUSE_WHEEL_ROTATED_UP
                    else MouseEventType.MOUSE_WHEEL_ROTATED_DOWN
                }
                else -> return
            }

            val button = when (event.button) {
                PointerButton.Primary -> 1
                PointerButton.Secondary -> 3
                PointerButton.Tertiary -> 2
                else -> 0
            }

            val mouseEvent = ZirconMouseEvent(
                type = type,
                button = button,
                position = position
            )

            // Filter duplicate move events
            if (shouldEmitEvent(type, position)) {
                lastMouseLocation = position
                logger.debug { "Processing Mouse Event: $mouseEvent" }
                events.add(mouseEvent to UIEventPhase.TARGET)
            }

        } catch (e: Exception) {
            logger.error {
                "Position for mouse event was out of bounds. It is dropped."
            }
            e.printStackTrace()
        }
    }

    private fun calculateGridPosition(offset: Offset): Position {
        return Position.create(
            x = 0.coerceAtLeast((offset.x / tileGrid.tileset.width.toFloat()).toInt()),
            y = 0.coerceAtLeast((offset.y / tileGrid.tileset.height.toFloat()).toInt())
        )
    }

    private fun shouldEmitEvent(eventType: MouseEventType, position: Position): Boolean {
        return when (eventType) {
            MouseEventType.MOUSE_MOVED, MouseEventType.MOUSE_DRAGGED -> {
                position != lastMouseLocation
            }
            else -> true
        }
    }
}
