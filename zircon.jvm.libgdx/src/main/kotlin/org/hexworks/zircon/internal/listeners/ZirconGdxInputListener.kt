package org.hexworks.zircon.internal.listeners

import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.*
import com.badlogic.gdx.Input.Keys.*
import com.badlogic.gdx.InputProcessor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.api.uievent.KeyboardEventType.KEY_RELEASED
import org.hexworks.zircon.api.uievent.KeyboardEventType.KEY_PRESSED
import org.hexworks.zircon.api.uievent.KeyboardEventType.KEY_TYPED
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.uievent.injectStringAsKeyboardEvents
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.event.MouseEvent.NOBUTTON

class ZirconInputListener(
    private val fontWidth: Int,
    private val fontHeight: Int,
    private val tileGrid: InternalTileGrid
) : InputProcessor {

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
        return if (keyCode.isUnknown) {
            false
        } else {
            val event = createKeyboardEvent(
                keyCode = keyCode,
                type = KEY_TYPED
            )
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
                type = KEY_PRESSED
            )
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
            return tileGrid.process(
                createKeyboardEvent(
                    keyCode = keyCode,
                    type = KEY_RELEASED
                ), UIEventPhase.TARGET
            ).eventProcessed
        }
        return false
    }

    private fun pasteClipboardContent(): UIEventResponse {
        return Toolkit.getDefaultToolkit().systemClipboard?.let {
            injectStringAsKeyboardEvents(it.getData(DataFlavor.stringFlavor) as String, tileGrid)
        } ?: Pass
    }

    private fun createKeyboardEvent(keyCode: KeyCode, type: KeyboardEventType): KeyboardEvent {
        val keyChar = keyCode.toCharOrNull()
        return KeyboardEvent(
            type = type,
            key = "$keyChar",
            code = keyCode,
            ctrlDown = ctrlPressed,
            altDown = altPressed,
            metaDown = metaPressed,
            shiftDown = shiftPressed
        )
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

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        val actionType = if (amountY > 0) {
            MouseEventType.MOUSE_WHEEL_ROTATED_DOWN
        } else {
            MouseEventType.MOUSE_WHEEL_ROTATED_UP
        }
        (0..amountY.toInt()).forEach {
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
                y = Math.max(0, y.div(fontHeight))
            )
            MouseEvent(
                type = eventType,
                button = button,
                position = position
            ).let {
                if (mouseMovedToNewPosition(eventType, position)
                        .or(isNotMoveEvent(eventType))
                ) {
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
            NUM_0 to KeyCode.DIGIT_0,
            NUM_1 to KeyCode.DIGIT_1,
            NUM_2 to KeyCode.DIGIT_2,
            NUM_3 to KeyCode.DIGIT_3,
            NUM_4 to KeyCode.DIGIT_4,
            NUM_5 to KeyCode.DIGIT_5,
            NUM_6 to KeyCode.DIGIT_6,
            NUM_7 to KeyCode.DIGIT_7,
            NUM_8 to KeyCode.DIGIT_8,
            NUM_9 to KeyCode.DIGIT_9,
            A to KeyCode.KEY_A,
            ALT_LEFT to KeyCode.ALT,
            ALT_RIGHT to KeyCode.ALT,
            APOSTROPHE to KeyCode.APOSTROPHE,
            AT to KeyCode.AT,
            B to KeyCode.KEY_B,
            BACK to KeyCode.BACKSPACE,
            BACKSLASH to KeyCode.BACKSLASH,
            C to KeyCode.KEY_C,
            CLEAR to KeyCode.CLEAR,
            COMMA to KeyCode.COMMA,
            D to KeyCode.KEY_D,
            DEL to KeyCode.DELETE,
            BACKSPACE to KeyCode.BACKSPACE,
            FORWARD_DEL to KeyCode.DELETE,
            DOWN to KeyCode.DOWN,
            LEFT to KeyCode.LEFT,
            RIGHT to KeyCode.RIGHT,
            UP to KeyCode.UP,
            E to KeyCode.KEY_E,
            ENTER to KeyCode.ENTER,
            EQUALS to KeyCode.EQUALS,
            F to KeyCode.KEY_F,
            G to KeyCode.KEY_G,
            GRAVE to KeyCode.DEAD_GRAVE,
            H to KeyCode.KEY_H,
            HOME to KeyCode.HOME,
            I to KeyCode.KEY_I,
            J to KeyCode.KEY_J,
            K to KeyCode.KEY_K,
            L to KeyCode.KEY_L,
            LEFT_BRACKET to KeyCode.OPEN_BRACKET,
            M to KeyCode.KEY_M,
            MENU to KeyCode.CONTEXT_MENU,
            MINUS to KeyCode.MINUS,
            N to KeyCode.KEY_N,
            O to KeyCode.KEY_O,
            P to KeyCode.KEY_P,
            PERIOD to KeyCode.PERIOD,
            PLUS to KeyCode.PLUS,
            Q to KeyCode.KEY_Q,
            R to KeyCode.KEY_R,
            RIGHT_BRACKET to KeyCode.CLOSE_BRACKET,
            S to KeyCode.KEY_S,
            SEMICOLON to KeyCode.SEMICOLON,
            SHIFT_LEFT to KeyCode.SHIFT,
            SHIFT_RIGHT to KeyCode.SHIFT,
            SLASH to KeyCode.SLASH,
            SPACE to KeyCode.SPACE,
            STAR to KeyCode.ASTERISK,
            T to KeyCode.KEY_T,
            TAB to KeyCode.TAB,
            U to KeyCode.KEY_U,
            V to KeyCode.KEY_V,
            W to KeyCode.KEY_W,
            X to KeyCode.KEY_X,
            Y to KeyCode.KEY_Y,
            Z to KeyCode.KEY_Z,
            CONTROL_LEFT to KeyCode.CONTROL,
            CONTROL_RIGHT to KeyCode.CONTROL,
            ESCAPE to KeyCode.ESCAPE,
            END to KeyCode.END,
            INSERT to KeyCode.INSERT,
            PAGE_UP to KeyCode.PAGE_UP,
            PAGE_DOWN to KeyCode.PAGE_DOWN,
            NUMPAD_0 to KeyCode.NUMPAD_0,
            NUMPAD_1 to KeyCode.NUMPAD_1,
            NUMPAD_2 to KeyCode.NUMPAD_2,
            NUMPAD_3 to KeyCode.NUMPAD_3,
            NUMPAD_4 to KeyCode.NUMPAD_4,
            NUMPAD_5 to KeyCode.NUMPAD_5,
            NUMPAD_6 to KeyCode.NUMPAD_6,
            NUMPAD_7 to KeyCode.NUMPAD_7,
            NUMPAD_8 to KeyCode.NUMPAD_8,
            NUMPAD_9 to KeyCode.NUMPAD_9,
            COLON to KeyCode.COLON,
            F1 to KeyCode.F1,
            F2 to KeyCode.F2,
            F3 to KeyCode.F3,
            F4 to KeyCode.F4,
            F5 to KeyCode.F5,
            F6 to KeyCode.F6,
            F7 to KeyCode.F7,
            F8 to KeyCode.F8,
            F9 to KeyCode.F9,
            F10 to KeyCode.F10,
            F11 to KeyCode.F11,
            F12 to KeyCode.F12
        )
    }
}
