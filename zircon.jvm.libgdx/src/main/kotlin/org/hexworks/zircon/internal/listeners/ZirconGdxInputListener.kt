package org.hexworks.zircon.internal.listeners

import com.badlogic.gdx.Input.Keys.*
import com.badlogic.gdx.InputProcessor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.input.KeyStroke
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.input.MouseActionType
import org.hexworks.zircon.api.util.TextUtils
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconScope
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.event.MouseEvent.NOBUTTON
import java.util.*

class ZirconInputListener(private val fontWidth: Int,
                          private val fontHeight: Int) : InputProcessor {

    private var clicking = false
    private var lastMouseLocation = Position.unknown()
    private var altPressed = false
    private var ctrlPressed = false
    private var shiftPressed = false

    override fun keyTyped(character: Char): Boolean {
        var char = character
        if (TYPED_KEYS_TO_IGNORE.contains(char).not()) {
            if (ctrlPressed && char.toInt() > 0 && char.toInt() < 0x1a) {
                char = ('a' - 1 + char.toInt())
                if (shiftPressed) {
                    char = Character.toUpperCase(char)
                }
            }

            // Check if clipboard is available and this was a paste (ctrl + shift + v) before
            // adding the key to the input queue
            if (!altPressed && ctrlPressed && shiftPressed && char == 'V' && RuntimeConfig.config.isClipboardAvailable) {
                pasteClipboardContent()
            } else {
                Zircon.eventBus.publish(
                        event = ZirconEvent.Input(KeyStroke(
                                character = character,
                                ctrlDown = ctrlPressed,
                                altDown = altPressed,
                                shiftDown = shiftPressed)),
                        eventScope = ZirconScope)
            }
        }
        return true
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == ALT_LEFT || keycode == ALT_RIGHT) {
            altPressed = true
        } else if (keycode == CONTROL_LEFT || keycode == CONTROL_RIGHT) {
            ctrlPressed = true
        } else if (keycode == SHIFT_LEFT || keycode == SHIFT_RIGHT) {
            shiftPressed = true
        }

        if (keycode == INSERT) {
            // This could be a paste (shift+insert) if the clipboard is available
            if (!altPressed && !ctrlPressed && shiftPressed && RuntimeConfig.config.isClipboardAvailable) {
                pasteClipboardContent()
            } else {
                Zircon.eventBus.publish(
                        event = ZirconEvent.Input(KeyStroke(type = InputType.Insert,
                                ctrlDown = ctrlPressed,
                                altDown = altPressed,
                                shiftDown = shiftPressed)),
                        eventScope = ZirconScope)
            }
        } else if (keycode == TAB) {
            if (shiftPressed) {
                Zircon.eventBus.publish(
                        event = ZirconEvent.Input(KeyStroke(type = InputType.ReverseTab,
                                ctrlDown = ctrlPressed,
                                altDown = altPressed,
                                shiftDown = shiftPressed)),
                        eventScope = ZirconScope)
            } else {
                Zircon.eventBus.publish(
                        event = ZirconEvent.Input(KeyStroke(type = InputType.Tab,
                                ctrlDown = ctrlPressed,
                                altDown = altPressed,
                                shiftDown = shiftPressed)),
                        eventScope = ZirconScope)
            }
        } else if (KEY_EVENT_TO_KEY_TYPE_LOOKUP.containsKey(keycode)) {
            Zircon.eventBus.publish(
                    event = ZirconEvent.Input(KeyStroke(type = KEY_EVENT_TO_KEY_TYPE_LOOKUP[keycode]!!,
                            ctrlDown = ctrlPressed,
                            altDown = altPressed,
                            shiftDown = shiftPressed)),
                    eventScope = ZirconScope)
        } else {
            //keyTyped doesn't catch this scenario (for whatever reason...) so we have to do it here
            if (altPressed && ctrlPressed && keycode >= 'A'.toByte() && keycode <= 'Z'.toByte()) {
                var character = keycode.toChar()
                if (!shiftPressed) {
                    character = Character.toLowerCase(character)
                }
                Zircon.eventBus.publish(
                        event = ZirconEvent.Input(KeyStroke(
                                character = character,
                                ctrlDown = ctrlPressed,
                                altDown = altPressed,
                                shiftDown = shiftPressed)),
                        eventScope = ZirconScope)
            }
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
        return true
    }

    private fun pasteClipboardContent() {
        Toolkit.getDefaultToolkit().systemClipboard?.let {
            injectStringAsKeyStrokes(it.getData(DataFlavor.stringFlavor) as String)
        }
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        addActionToKeyQueue(MouseActionType.MOUSE_RELEASED, screenX, screenY, button)
        if (clicking) {
            addActionToKeyQueue(MouseActionType.MOUSE_CLICKED, screenX, screenY, button)
        }
        return true
    }

    override fun scrolled(amount: Int): Boolean {
        val actionType = if (amount > 0) {
            MouseActionType.MOUSE_WHEEL_ROTATED_DOWN
        } else {
            MouseActionType.MOUSE_WHEEL_ROTATED_UP
        }
        (0..amount).forEach {
            addActionToKeyQueue(actionType, 0, 0, NOBUTTON)
        }
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        clicking = false
        addActionToKeyQueue(MouseActionType.MOUSE_DRAGGED, screenX, screenY, NOBUTTON)
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        clicking = true
        addActionToKeyQueue(MouseActionType.MOUSE_PRESSED, screenX, screenY, button)
        return true
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        addActionToKeyQueue(MouseActionType.MOUSE_MOVED, screenX, screenY, NOBUTTON)
        return true
    }

    private fun addActionToKeyQueue(actionType: MouseActionType, x: Int, y: Int, button: Int) {
        try {
            val position = Position.create(
                    x = Math.max(0, x.div(fontWidth)),
                    y = Math.max(0, y.div(fontHeight)))
            MouseAction(
                    actionType = actionType,
                    button = button,
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

    private fun isNotMoveEvent(actionType: MouseActionType) = actionType != MouseActionType.MOUSE_MOVED

    private fun mouseMovedToNewPosition(actionType: MouseActionType, position: Position) =
            actionType == MouseActionType.MOUSE_MOVED && position != lastMouseLocation


    private fun injectStringAsKeyStrokes(string: String) {
        string.filter {
            TextUtils.isPrintableCharacter(it)
        }.forEach {
            Zircon.eventBus.publish(
                    event = ZirconEvent.Input(KeyStroke(character = it)),
                    eventScope = ZirconScope)
        }
    }

    companion object {

        private val TYPED_KEYS_TO_IGNORE = HashSet(Arrays.asList('\n', '\t', '\r', '\b', '\u001b', 127.toChar()))

        /*
        Please Note: LibGDX does NOT Support the caps lock key (for
        whatever reason) and thus it cannot be used in the LibGDX version
        of Zircon
         */
        private val KEY_EVENT_TO_KEY_TYPE_LOOKUP = mapOf(
                Pair(NUMPAD_0, InputType.Numpad0), Pair(NUMPAD_1, InputType.Numpad1),
                Pair(NUMPAD_2, InputType.Numpad2), Pair(NUMPAD_3, InputType.Numpad3),
                Pair(NUMPAD_4, InputType.Numpad4), Pair(NUMPAD_5, InputType.Numpad5),
                Pair(NUMPAD_6, InputType.Numpad6), Pair(NUMPAD_7, InputType.Numpad7),
                Pair(NUMPAD_8, InputType.Numpad8), Pair(NUMPAD_9, InputType.Numpad9),
                Pair(MEDIA_PLAY_PAUSE, InputType.Pause), /*Pair(DOES_NOT_EXIST, InputType.CapsLock),*/
                Pair(SPACE, InputType.Space),
                Pair(ENTER, InputType.Enter), Pair(ESCAPE, InputType.Escape),
                Pair(BACKSPACE, InputType.Backspace), Pair(LEFT, InputType.ArrowLeft),
                Pair(RIGHT, InputType.ArrowRight), Pair(UP, InputType.ArrowUp),
                Pair(DOWN, InputType.ArrowDown), Pair(DEL, InputType.Delete),
                Pair(HOME, InputType.Home), Pair(END, InputType.End),
                Pair(PAGE_UP, InputType.PageUp), Pair(PAGE_DOWN, InputType.PageDown),
                Pair(F1, InputType.F1), Pair(F2, InputType.F2),
                Pair(F3, InputType.F3), Pair(F4, InputType.F4),
                Pair(F5, InputType.F5), Pair(F6, InputType.F6),
                Pair(F7, InputType.F7), Pair(F8, InputType.F8),
                Pair(F9, InputType.F9), Pair(F10, InputType.F10),
                Pair(F11, InputType.F11), Pair(F12, InputType.F12))
    }
}
