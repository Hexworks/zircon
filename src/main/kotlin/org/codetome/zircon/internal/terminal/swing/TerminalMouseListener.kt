package org.codetome.zircon.terminal.swing

import org.codetome.zircon.api.Position
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import org.codetome.zircon.api.input.KeyStroke
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.api.input.MouseActionType
import org.codetome.zircon.api.terminal.config.DeviceConfiguration
import org.codetome.zircon.api.util.TextUtils
import java.awt.MouseInfo
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseWheelEvent

open class TerminalMouseListener(private val deviceConfiguration: DeviceConfiguration,
                                 private val fontWidth: Int,
                                 private val fontHeight: Int) : MouseAdapter() {

    override fun mouseClicked(e: MouseEvent) {
        if (MouseInfo.getNumberOfButtons() > 2 &&
                e.button == MouseEvent.BUTTON2 &&
                deviceConfiguration.isClipboardAvailable) {
            pasteSelectionContent()
        }
        addActionToKeyQueue(MouseActionType.MOUSE_CLICKED, e)
    }

    override fun mouseReleased(e: MouseEvent) {
        addActionToKeyQueue(MouseActionType.MOUSE_RELEASED, e)
    }

    override fun mouseMoved(e: MouseEvent) {
        addActionToKeyQueue(MouseActionType.MOUSE_MOVED, e)
    }

    override fun mouseEntered(e: MouseEvent) {
        addActionToKeyQueue(MouseActionType.MOUSE_ENTERED, e)
    }

    override fun mouseExited(e: MouseEvent) {
        addActionToKeyQueue(MouseActionType.MOUSE_EXITED, e)
    }

    override fun mouseDragged(e: MouseEvent) {
        addActionToKeyQueue(MouseActionType.MOUSE_DRAGGED, e)
    }

    override fun mousePressed(e: MouseEvent) {
        addActionToKeyQueue(MouseActionType.MOUSE_PRESSED, e)
    }

    override fun mouseWheelMoved(e: MouseWheelEvent) {
        val actionType = if (e.preciseWheelRotation > 0) {
            MouseActionType.MOUSE_WHEEL_ROTATED_DOWN
        } else {
            MouseActionType.MOUSE_WHEEL_ROTATED_UP
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
                EventBus.emit(EventType.Input, it)
                EventBus.emit(EventType.MouseAction, it)
            }
        } catch (e: Exception) {
            println("position for mouse event '$e' was out of bounds. It is dropped.")
        }
    }

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