package org.codetome.zircon.internal.terminal.swing

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.input.KeyStroke
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.api.input.MouseActionType
import org.codetome.zircon.api.input.MouseActionType.*
import org.codetome.zircon.api.terminal.config.DeviceConfiguration
import org.codetome.zircon.api.util.TextUtils
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import org.slf4j.LoggerFactory
import java.awt.MouseInfo
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseWheelEvent

open class TerminalMouseListener(private val deviceConfiguration: DeviceConfiguration,
                                 private val fontWidth: Int,
                                 private val fontHeight: Int) : MouseAdapter() {

    private val logger = LoggerFactory.getLogger(javaClass)
    private var lastMouseLocation = Position.UNKNOWN

    override fun mouseClicked(e: MouseEvent) {
        if (MouseInfo.getNumberOfButtons() > 2 &&
                e.button == MouseEvent.BUTTON2 &&
                deviceConfiguration.isClipboardAvailable) {
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
            val position = Position.of(
                    column = e.x.div(fontWidth),
                    row = e.y.div(fontHeight))
            MouseAction(
                    actionType = actionType,
                    button = e.button,
                    position = position
            ).let {
                if (mouseMovedToNewPosition(actionType, position)
                        .or(isNotMoveEvent(actionType))) {
                    lastMouseLocation = position
                    EventBus.emit(EventType.Input, it)
                }
            }
        } catch (e: Exception) {
            println("position for mouse event '$e' was out of bounds. It is dropped.")
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
        string
                .filter {
                    TextUtils.isPrintableCharacter(it)
                }
                .forEach {
                    EventBus.emit(EventType.Input, KeyStroke(character = it))
                }
    }

}