package org.hexworks.zircon.internal.listeners

import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.Keys.*
import com.badlogic.gdx.InputProcessor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.api.uievent.KeyboardEventType.KEY_RELEASED
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.uievent.injectStringAsKeyboardEvents
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.event.MouseEvent.NOBUTTON

class ZirconInputListener(private val fontWidth: Int,
                          private val fontHeight: Int,
                          private val tileGrid: InternalTileGrid) : InputProcessor {

    private var clicking = false
    private var lastMouseLocation = Position.unknown()
    private var ctrlPressed = false
    private var altPressed = false
    private var metaPressed = false
    private var shiftPressed = false

    /////////////////
    // keyboard events
    /////////////////

    override fun keyTyped(character: Char): Boolean {
        val keyCode = KeyCode.findByChar(character)
        return if (keyCode.isUnknown()) {
            false
        } else {
            val event = createKeyboardEvent(
                    keyCode = keyCode,
                    type = KEY_RELEASED)
            if (RuntimeConfig.config.isClipboardAvailable && event.isPasteEvent()) {
                pasteClipboardContent()
            } else {
                tileGrid.process(event, UIEventPhase.TARGET)
            }.eventProcessed
        }
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == ALT_LEFT || keycode == ALT_RIGHT) {
            altPressed = true
        } else if (keycode == CONTROL_LEFT || keycode == CONTROL_RIGHT) {
            ctrlPressed = true
        } else if (keycode == SHIFT_LEFT || keycode == SHIFT_RIGHT) {
            shiftPressed = true
        }
        KEY_EVENT_TO_KEY_TYPE_LOOKUP[keycode]?.let { keyCode ->
            val event = createKeyboardEvent(
                    keyCode = keyCode,
                    type = KEY_RELEASED)
            return if (RuntimeConfig.config.isClipboardAvailable && event.isPasteEvent()) {
                pasteClipboardContent()
            } else {
                tileGrid.process(event, UIEventPhase.TARGET)
            }.eventProcessed
        }
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        if (keycode == ALT_LEFT || keycode == ALT_RIGHT) {
            altPressed = false
        } else if (keycode == CONTROL_LEFT || keycode == CONTROL_RIGHT) {
            ctrlPressed = false
        } else if (keycode == SHIFT_LEFT || keycode == SHIFT_RIGHT) {
            shiftPressed = false
        }
        KEY_EVENT_TO_KEY_TYPE_LOOKUP[keycode]?.let { keyCode ->
            return tileGrid.process(createKeyboardEvent(
                    keyCode = keyCode,
                    type = KEY_RELEASED), UIEventPhase.TARGET).eventProcessed
        }
        return false
    }

    private fun pasteClipboardContent(): UIEventResponse {
        return Toolkit.getDefaultToolkit().systemClipboard?.let {
            injectStringAsKeyboardEvents(it.getData(DataFlavor.stringFlavor) as String, tileGrid)
        } ?: Pass
    }

    private fun createKeyboardEvent(keyCode: KeyCode, type: KeyboardEventType): KeyboardEvent {
        val keyChar = keyCode.toChar()
        return KeyboardEvent(
                type = type,
                key = "${keyChar.orElse(' ')}",
                code = keyCode,
                ctrlDown = ctrlPressed,
                altDown = altPressed,
                metaDown = metaPressed,
                shiftDown = shiftPressed)
    }

    private fun KeyboardEvent.isPasteEvent() = (code == KeyCode.INSERT &&
            ctrlDown.not() && altDown.not() && metaDown.not() && shiftDown)


    /////////////////
    // mouse events
    /////////////////

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        processMouseEvent(MouseEventType.MOUSE_RELEASED, screenX, screenY, button)
        if (clicking) {
            processMouseEvent(MouseEventType.MOUSE_CLICKED, screenX, screenY, button)
        }
        return true
    }

    override fun scrolled(amount: Int): Boolean {
        val actionType = if (amount > 0) {
            MouseEventType.MOUSE_WHEEL_ROTATED_DOWN
        } else {
            MouseEventType.MOUSE_WHEEL_ROTATED_UP
        }
        (0..amount).forEach {
            processMouseEvent(actionType, 0, 0, NOBUTTON)
        }
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        clicking = false
        processMouseEvent(MouseEventType.MOUSE_DRAGGED, screenX, screenY, NOBUTTON)
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        clicking = true
        processMouseEvent(MouseEventType.MOUSE_PRESSED, screenX, screenY, button)
        return true
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        processMouseEvent(MouseEventType.MOUSE_MOVED, screenX, screenY, NOBUTTON)
        return true
    }

    private fun processMouseEvent(eventType: MouseEventType, x: Int, y: Int, button: Int) {
        try {
            val position = Position.create(
                    x = Math.max(0, x.div(fontWidth)),
                    y = Math.max(0, y.div(fontHeight)))
            MouseEvent(
                    type = eventType,
                    button = button,
                    position = position).let {
                if (mouseMovedToNewPosition(eventType, position)
                                .or(isNotMoveEvent(eventType))) {
                    lastMouseLocation = position
                    tileGrid.process(it, UIEventPhase.TARGET)
                }
            }
        } catch (e: Exception) {
            System.err.println("position for mouse event '$e' was out of bounds. It is dropped.")
            e.printStackTrace()
        }
    }

    private fun isNotMoveEvent(eventType: MouseEventType) = eventType != MouseEventType.MOUSE_MOVED

    private fun mouseMovedToNewPosition(eventType: MouseEventType, position: Position) =
            eventType == MouseEventType.MOUSE_MOVED && position != lastMouseLocation

    companion object {

        // Please Note: LibGDX does NOT Support the caps lock key (for
        // whatever reason) and thus it cannot be used in the LibGDX version
        // of Zircon

        private val KEY_EVENT_TO_KEY_TYPE_LOOKUP = mapOf(
                Input.Keys.NUM_0 to KeyCode.DIGIT_0,
                Input.Keys.NUM_1 to KeyCode.DIGIT_1,
                Input.Keys.NUM_2 to KeyCode.DIGIT_2,
                Input.Keys.NUM_3 to KeyCode.DIGIT_3,
                Input.Keys.NUM_4 to KeyCode.DIGIT_4,
                Input.Keys.NUM_5 to KeyCode.DIGIT_5,
                Input.Keys.NUM_6 to KeyCode.DIGIT_6,
                Input.Keys.NUM_7 to KeyCode.DIGIT_7,
                Input.Keys.NUM_8 to KeyCode.DIGIT_8,
                Input.Keys.NUM_9 to KeyCode.DIGIT_9,
                Input.Keys.A to KeyCode.KEY_A,
                Input.Keys.ALT_LEFT to KeyCode.ALT,
                Input.Keys.ALT_RIGHT to KeyCode.ALT,
                Input.Keys.APOSTROPHE to KeyCode.APOSTROPHE,
                Input.Keys.AT to KeyCode.AT,
                Input.Keys.B to KeyCode.KEY_B,
                Input.Keys.BACK to KeyCode.BACKSPACE,
                Input.Keys.BACKSLASH to KeyCode.BACKSLASH,
                Input.Keys.C to KeyCode.KEY_C,
                Input.Keys.CLEAR to KeyCode.CLEAR,
                Input.Keys.COMMA to KeyCode.COMMA,
                Input.Keys.D to KeyCode.KEY_D,
                Input.Keys.DEL to KeyCode.DELETE,
                Input.Keys.BACKSPACE to KeyCode.BACKSPACE,
                Input.Keys.FORWARD_DEL to KeyCode.DELETE,
                Input.Keys.DOWN to KeyCode.DOWN,
                Input.Keys.LEFT to KeyCode.LEFT,
                Input.Keys.RIGHT to KeyCode.RIGHT,
                Input.Keys.UP to KeyCode.UP,
                Input.Keys.E to KeyCode.KEY_E,
                Input.Keys.ENTER to KeyCode.ENTER,
                Input.Keys.EQUALS to KeyCode.EQUALS,
                Input.Keys.F to KeyCode.KEY_F,
                Input.Keys.G to KeyCode.KEY_G,
                Input.Keys.GRAVE to KeyCode.DEAD_GRAVE,
                Input.Keys.H to KeyCode.KEY_H,
                Input.Keys.HOME to KeyCode.HOME,
                Input.Keys.I to KeyCode.KEY_I,
                Input.Keys.J to KeyCode.KEY_J,
                Input.Keys.K to KeyCode.KEY_K,
                Input.Keys.L to KeyCode.KEY_L,
                Input.Keys.LEFT_BRACKET to KeyCode.OPEN_BRACKET,
                Input.Keys.M to KeyCode.KEY_M,
                Input.Keys.MENU to KeyCode.CONTEXT_MENU,
                Input.Keys.MINUS to KeyCode.MINUS,
                Input.Keys.N to KeyCode.KEY_N,
                Input.Keys.O to KeyCode.KEY_O,
                Input.Keys.P to KeyCode.KEY_P,
                Input.Keys.PERIOD to KeyCode.PERIOD,
                Input.Keys.PLUS to KeyCode.PLUS,
                Input.Keys.Q to KeyCode.KEY_Q,
                Input.Keys.R to KeyCode.KEY_R,
                Input.Keys.RIGHT_BRACKET to KeyCode.CLOSE_BRACKET,
                Input.Keys.S to KeyCode.KEY_S,
                Input.Keys.SEMICOLON to KeyCode.SEMICOLON,
                Input.Keys.SHIFT_LEFT to KeyCode.SHIFT,
                Input.Keys.SHIFT_RIGHT to KeyCode.SHIFT,
                Input.Keys.SLASH to KeyCode.SLASH,
                Input.Keys.SPACE to KeyCode.SPACE,
                Input.Keys.STAR to KeyCode.ASTERISK,
                Input.Keys.T to KeyCode.KEY_T,
                Input.Keys.TAB to KeyCode.TAB,
                Input.Keys.U to KeyCode.KEY_U,
                Input.Keys.V to KeyCode.KEY_V,
                Input.Keys.W to KeyCode.KEY_W,
                Input.Keys.X to KeyCode.KEY_X,
                Input.Keys.Y to KeyCode.KEY_Y,
                Input.Keys.Z to KeyCode.KEY_Z,
                Input.Keys.CONTROL_LEFT to KeyCode.CONTROL,
                Input.Keys.CONTROL_RIGHT to KeyCode.CONTROL,
                Input.Keys.ESCAPE to KeyCode.ESCAPE,
                Input.Keys.END to KeyCode.END,
                Input.Keys.INSERT to KeyCode.INSERT,
                Input.Keys.PAGE_UP to KeyCode.PAGE_UP,
                Input.Keys.PAGE_DOWN to KeyCode.PAGE_DOWN,
                Input.Keys.NUMPAD_0 to KeyCode.NUMPAD_0,
                Input.Keys.NUMPAD_1 to KeyCode.NUMPAD_1,
                Input.Keys.NUMPAD_2 to KeyCode.NUMPAD_2,
                Input.Keys.NUMPAD_3 to KeyCode.NUMPAD_3,
                Input.Keys.NUMPAD_4 to KeyCode.NUMPAD_4,
                Input.Keys.NUMPAD_5 to KeyCode.NUMPAD_5,
                Input.Keys.NUMPAD_6 to KeyCode.NUMPAD_6,
                Input.Keys.NUMPAD_7 to KeyCode.NUMPAD_7,
                Input.Keys.NUMPAD_8 to KeyCode.NUMPAD_8,
                Input.Keys.NUMPAD_9 to KeyCode.NUMPAD_9,
                Input.Keys.COLON to KeyCode.COLON,
                Input.Keys.F1 to KeyCode.F1,
                Input.Keys.F2 to KeyCode.F2,
                Input.Keys.F3 to KeyCode.F3,
                Input.Keys.F4 to KeyCode.F4,
                Input.Keys.F5 to KeyCode.F5,
                Input.Keys.F6 to KeyCode.F6,
                Input.Keys.F7 to KeyCode.F7,
                Input.Keys.F8 to KeyCode.F8,
                Input.Keys.F9 to KeyCode.F9,
                Input.Keys.F10 to KeyCode.F10,
                Input.Keys.F11 to KeyCode.F11,
                Input.Keys.F12 to KeyCode.F12)
    }
}
