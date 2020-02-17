package org.hexworks.zircon.internal.uievent

import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_DRAGGED
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_MOVED
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.api.uievent.UIEventPhase.TARGET
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseWheelEvent

open class MouseEventListener(
        private val fontWidth: Int,
        private val fontHeight: Int) : MouseAdapter() {

    private val logger = LoggerFactory.getLogger(this::class)
    private val events = mutableListOf<Pair<org.hexworks.zircon.api.uievent.MouseEvent, UIEventPhase>>()

    private var lastMouseLocation = Position.unknown()

    @Synchronized
    fun drainEvents(): Iterable<Pair<org.hexworks.zircon.api.uievent.MouseEvent, UIEventPhase>> {
        return events.toList().also {
            events.clear()
        }
    }

    override fun mouseClicked(e: MouseEvent) {
        processMouseEvent(MouseEventType.MOUSE_CLICKED, e)
    }

    override fun mouseReleased(e: MouseEvent) {
        processMouseEvent(MouseEventType.MOUSE_RELEASED, e)
    }

    override fun mouseMoved(e: MouseEvent) {
        processMouseEvent(MOUSE_MOVED, e)
    }

    override fun mouseEntered(e: MouseEvent) {
        processMouseEvent(MouseEventType.MOUSE_ENTERED, e)
    }

    override fun mouseExited(e: MouseEvent) {
        processMouseEvent(MouseEventType.MOUSE_EXITED, e)
    }

    override fun mouseDragged(e: MouseEvent) {
        processMouseEvent(MOUSE_DRAGGED, e)
    }

    override fun mousePressed(e: MouseEvent) {
        processMouseEvent(MouseEventType.MOUSE_PRESSED, e)
    }

    override fun mouseWheelMoved(e: MouseWheelEvent) {
        val actionType = if (e.preciseWheelRotation > 0) {
            MouseEventType.MOUSE_WHEEL_ROTATED_DOWN
        } else {
            MouseEventType.MOUSE_WHEEL_ROTATED_UP
        }
        (0..e.preciseWheelRotation.toInt()).forEach {
            processMouseEvent(actionType, e)
        }
    }

    private fun processMouseEvent(eventType: MouseEventType, e: MouseEvent) {
        try {
            val position = Position.create(
                    x = 0.coerceAtLeast(e.x.div(fontWidth)),
                    y = 0.coerceAtLeast(e.y.div(fontHeight)))
            org.hexworks.zircon.api.uievent.MouseEvent(
                    type = eventType,
                    button = e.button,
                    position = position).let {
                if (mouseMovedToNewPosition(eventType, position)
                                .or(isNotMoveEvent(eventType))) {
                    lastMouseLocation = position
                    logger.debug("Processing Mouse Event: $it.")
                    events.add(it to TARGET)
                }
            }
        } catch (e: Exception) {
            logger.error("Position for mouse event '$e' was out of bounds. It is dropped.")
            e.printStackTrace()
        }
    }

    private fun isNotMoveEvent(eventType: MouseEventType) = eventType != MOUSE_MOVED

    private fun mouseMovedToNewPosition(eventType: MouseEventType, position: Position) =
            eventType in setOf(MOUSE_MOVED, MOUSE_DRAGGED) && position != lastMouseLocation

}
