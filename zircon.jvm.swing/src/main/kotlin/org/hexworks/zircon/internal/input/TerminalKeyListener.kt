package org.hexworks.zircon.internal.input

import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.input.KeyStroke
import org.hexworks.zircon.api.util.TextUtils
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconScope
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.event.InputEvent
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.util.*

class TerminalKeyListener : KeyAdapter() {

    override fun keyTyped(e: KeyEvent) {
        var character = e.keyChar
        val altDown = e.modifiersEx and InputEvent.ALT_DOWN_MASK != 0
        val ctrlDown = e.modifiersEx and InputEvent.CTRL_DOWN_MASK != 0
        val shiftDown = e.modifiersEx and InputEvent.SHIFT_DOWN_MASK != 0

        if (TYPED_KEYS_TO_IGNORE.contains(character).not()) {
            if (ctrlDown && character.toInt() > 0 && character.toInt() < 0x1a) {
                character = ('a' - 1 + character.toInt())
                if (shiftDown) {
                    character = Character.toUpperCase(character)
                }
            }

            // Check if clipboard is available and this was a paste (ctrl + shift + v) before
            // adding the key to the input queue
            if (!altDown && ctrlDown && shiftDown && character == 'V' && RuntimeConfig.config.isClipboardAvailable) {
                pasteClipboardContent()
            } else {
                Zircon.eventBus.publish(
                        event = ZirconEvent.Input(KeyStroke(
                                character = character,
                                ctrlDown = ctrlDown,
                                altDown = altDown,
                                shiftDown = shiftDown)),
                        eventScope = ZirconScope)
            }
        }
    }

    override fun keyPressed(e: KeyEvent) {
        val altDown = e.modifiersEx and InputEvent.ALT_DOWN_MASK != 0
        val ctrlDown = e.modifiersEx and InputEvent.CTRL_DOWN_MASK != 0
        val shiftDown = e.modifiersEx and InputEvent.SHIFT_DOWN_MASK != 0

        if (e.keyCode == KeyEvent.VK_INSERT) {
            // This could be a paste (shift+insert) if the clipboard is available
            if (!altDown && !ctrlDown && shiftDown && RuntimeConfig.config.isClipboardAvailable) {
                pasteClipboardContent()
            } else {
                Zircon.eventBus.publish(
                        event = ZirconEvent.Input(KeyStroke(type = InputType.Insert,
                                ctrlDown = ctrlDown,
                                altDown = altDown,
                                shiftDown = shiftDown)),
                        eventScope = ZirconScope)
            }
        } else if (e.keyCode == KeyEvent.VK_TAB) {
            if (e.isShiftDown) {
                Zircon.eventBus.publish(
                        event = ZirconEvent.Input(KeyStroke(type = InputType.ReverseTab,
                                ctrlDown = ctrlDown,
                                altDown = altDown,
                                shiftDown = shiftDown)),
                        eventScope = ZirconScope)
            } else {
                Zircon.eventBus.publish(
                        event = ZirconEvent.Input(KeyStroke(type = InputType.Tab,
                                ctrlDown = ctrlDown,
                                altDown = altDown,
                                shiftDown = shiftDown)),
                        eventScope = ZirconScope)
            }
        } else if (KEY_EVENT_TO_KEY_TYPE_LOOKUP.containsKey(e.keyCode)) {
            Zircon.eventBus.publish(
                    event = ZirconEvent.Input(KeyStroke(type = KEY_EVENT_TO_KEY_TYPE_LOOKUP[e.keyCode]!!,
                            ctrlDown = ctrlDown,
                            altDown = altDown,
                            shiftDown = shiftDown)),
                    eventScope = ZirconScope)
        } else {
            //keyTyped doesn't catch this scenario (for whatever reason...) so we have to do it here
            if (altDown && ctrlDown && e.keyCode >= 'A'.toByte() && e.keyCode <= 'Z'.toByte()) {
                var character = e.keyCode.toChar()
                if (!shiftDown) {
                    character = Character.toLowerCase(character)
                }
                Zircon.eventBus.publish(
                        event = ZirconEvent.Input(KeyStroke(
                                character = character,
                                ctrlDown = ctrlDown,
                                altDown = altDown,
                                shiftDown = shiftDown)),
                        eventScope = ZirconScope)
            }
        }
    }

    private fun pasteClipboardContent() {
        Toolkit.getDefaultToolkit().systemClipboard?.let {
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

    companion object {

        private val TYPED_KEYS_TO_IGNORE = HashSet(Arrays.asList('\n', '\t', '\r', '\b', '\u001b', 127.toChar()))

        private val KEY_EVENT_TO_KEY_TYPE_LOOKUP = mapOf(
                KeyEvent.VK_NUMPAD0 to InputType.Numpad0,
                KeyEvent.VK_NUMPAD1 to InputType.Numpad1,
                KeyEvent.VK_NUMPAD2 to InputType.Numpad2,
                KeyEvent.VK_NUMPAD3 to InputType.Numpad3,
                KeyEvent.VK_NUMPAD4 to InputType.Numpad4,
                KeyEvent.VK_NUMPAD5 to InputType.Numpad5,
                KeyEvent.VK_NUMPAD6 to InputType.Numpad6,
                KeyEvent.VK_NUMPAD7 to InputType.Numpad7,
                KeyEvent.VK_NUMPAD8 to InputType.Numpad8,
                KeyEvent.VK_NUMPAD9 to InputType.Numpad9,
                KeyEvent.VK_ADD to InputType.NumpadAdd,
                KeyEvent.VK_SUBTRACT to InputType.NumpadSubtract,
                KeyEvent.VK_MULTIPLY to InputType.NumpadMultiply,
                KeyEvent.VK_DIVIDE to InputType.NumpadDivide,
                KeyEvent.VK_PAUSE to InputType.Pause,
                KeyEvent.VK_CAPS_LOCK to InputType.CapsLock,
                KeyEvent.VK_SPACE to InputType.Space,
                KeyEvent.VK_ENTER to InputType.Enter,
                KeyEvent.VK_ESCAPE to InputType.Escape,
                KeyEvent.VK_BACK_SPACE to InputType.Backspace,
                KeyEvent.VK_LEFT to InputType.ArrowLeft,
                KeyEvent.VK_RIGHT to InputType.ArrowRight,
                KeyEvent.VK_UP to InputType.ArrowUp,
                KeyEvent.VK_DOWN to InputType.ArrowDown,
                KeyEvent.VK_DELETE to InputType.Delete,
                KeyEvent.VK_HOME to InputType.Home,
                KeyEvent.VK_END to InputType.End,
                KeyEvent.VK_PAGE_UP to InputType.PageUp,
                KeyEvent.VK_PAGE_DOWN to InputType.PageDown,
                KeyEvent.VK_F1 to InputType.F1,
                KeyEvent.VK_F2 to InputType.F2,
                KeyEvent.VK_F3 to InputType.F3,
                KeyEvent.VK_F4 to InputType.F4,
                KeyEvent.VK_F5 to InputType.F5,
                KeyEvent.VK_F6 to InputType.F6,
                KeyEvent.VK_F7 to InputType.F7,
                KeyEvent.VK_F8 to InputType.F8,
                KeyEvent.VK_F9 to InputType.F9,
                KeyEvent.VK_F10 to InputType.F10,
                KeyEvent.VK_F11 to InputType.F11,
                KeyEvent.VK_F12 to InputType.F12)
    }
}
