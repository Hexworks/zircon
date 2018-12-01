package org.hexworks.zircon.internal.input

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.input.KeyStroke
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.input.MouseActionType
import org.hexworks.zircon.api.input.MouseActionType.*
import org.hexworks.zircon.api.util.TextUtils
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconScope
import java.awt.GraphicsEnvironment
import java.awt.MouseInfo
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseWheelEvent

open class TerminalMouseListener(private val fontWidth: Int,
                                 private val fontHeight: Int) : MouseAdapter() {

    private var lastMouseLocation = Position.unknown()

    override fun mouseClicked(e: MouseEvent) {
        if (GraphicsEnvironment.isHeadless().not() &&
                MouseInfo.getNumberOfButtons() > 2 &&
                e.button == MouseEvent.BUTTON2 &&
                RuntimeConfig.config.isClipboardAvailable) {
            pasteSelectionContent()
        }
        addActionToKeyQueue(MOUSE_CLICKED, e)
    }

    override fun mouseReleased(e: MouseEvent) {
        addActionToKeyQueue(MOUSE_RELEASED, e)
    }

    override fun mouseMoved(e: MouseEvent) {
        addActionToKeyQueue(MOUSE_MOVED, e)
    }

    override fun mouseEntered(e: MouseEvent) {
        addActionToKeyQueue(MOUSE_ENTERED, e)
    }

    override fun mouseExited(e: MouseEvent) {
        addActionToKeyQueue(MOUSE_EXITED, e)
    }

    override fun mouseDragged(e: MouseEvent) {
        addActionToKeyQueue(MOUSE_DRAGGED, e)
    }

    override fun mousePressed(e: MouseEvent) {
        addActionToKeyQueue(MOUSE_PRESSED, e)
    }

    override fun mouseWheelMoved(e: MouseWheelEvent) {
        val actionType = if (e.preciseWheelRotation > 0) {
            MOUSE_WHEEL_ROTATED_DOWN
        } else {
            MOUSE_WHEEL_ROTATED_UP
        }
        (0..e.preciseWheelRotation.toInt()).forEach {
            addActionToKeyQueue(actionType, e)
        }
    }

    private fun addActionToKeyQueue(actionType: MouseActionType, e: MouseEvent) {
        try {
            val position = Position.create(
                    x = Math.max(0, e.x.div(fontWidth)),
                    y = Math.max(0, e.y.div(fontHeight)))
            MouseAction(
                    actionType = actionType,
                    button = e.button,
                    position = position
            ).let {
                if (mouseMovedToNewPosition(actionType, position)
                                .or(isNotMoveEvent(actionType))) {
                    lastMouseLocation = position
                    Zircon.eventBus.publish(
                            event = ZirconEvent.Input(it),
                            eventScope = ZirconScope)
                }
            }
        } catch (e: Exception) {
            System.err.println("position for mouse event '$e' was out of bounds. It is dropped.")
            e.printStackTrace()
        }
    }

    private fun isNotMoveEvent(actionType: MouseActionType) = actionType != MOUSE_MOVED

    private fun mouseMovedToNewPosition(actionType: MouseActionType, position: Position) =
            actionType == MOUSE_MOVED && position != lastMouseLocation

    private fun pasteSelectionContent() {
        Toolkit.getDefaultToolkit().systemSelection?.let {
            injectStringAsKeyStrokes(it.getData(DataFlavor.stringFlavor) as String)
        }
    }

    private fun injectStringAsKeyStrokes(string: String) {
        string.filter {
            TextUtils.isPrintableCharacter(it)
        }.forEach {
            Zircon.eventBus.publish(
                    event = ZirconEvent.Input(KeyStroke(character = it)),
                    eventScope = ZirconScope)
        }
    }

}
