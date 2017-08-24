package org.codetome.zircon.internal.terminal.swing

import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import org.codetome.zircon.api.input.InputType
import org.codetome.zircon.api.input.KeyStroke
import org.codetome.zircon.api.terminal.Terminal
import org.codetome.zircon.api.terminal.config.DeviceConfiguration
import org.codetome.zircon.api.util.TextUtils
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.event.InputEvent
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.util.*

class TerminalKeyListener(private val terminal: Terminal,
                          private val deviceConfiguration: DeviceConfiguration) : KeyAdapter() {

    override fun keyTyped(e: KeyEvent) {
        var character = e.keyChar
        val altDown = e.modifiersEx and InputEvent.ALT_DOWN_MASK != 0
        val ctrlDown = e.modifiersEx and InputEvent.CTRL_DOWN_MASK != 0
        val shiftDown = e.modifiersEx and InputEvent.SHIFT_DOWN_MASK != 0

        if (!TYPED_KEYS_TO_IGNORE.contains(character)) {
            //We need to re-adjust alphabet characters if ctrl was pressed, just like for the AnsiTerminal
            if (ctrlDown && character.toInt() > 0 && character.toInt() < 0x1a) {
                character = ('a' - 1 + character.toInt()).toChar()
                if (shiftDown) {
                    character = Character.toUpperCase(character)
                }
            }

            // Check if clipboard is avavilable and this was a paste (ctrl + shift + v) before
            // adding the key to the input queue
            if (!altDown && ctrlDown && shiftDown && character == 'V' && deviceConfiguration.isClipboardAvailable) {
                pasteClipboardContent()
            } else {
                EventBus.emit(EventType.Input, KeyStroke(
                        character = character,
                        ctrlDown = ctrlDown,
                        altDown = altDown,
                        shiftDown = shiftDown))
            }
        }
    }

    override fun keyPressed(e: KeyEvent) {
        val altDown = e.modifiersEx and InputEvent.ALT_DOWN_MASK != 0
        val ctrlDown = e.modifiersEx and InputEvent.CTRL_DOWN_MASK != 0
        val shiftDown = e.modifiersEx and InputEvent.SHIFT_DOWN_MASK != 0

        if (e.keyCode == KeyEvent.VK_INSERT) {
            // This could be a paste (shift+insert) if the clipboard is available
            if (!altDown && !ctrlDown && shiftDown && deviceConfiguration.isClipboardAvailable) {
                pasteClipboardContent()
            } else {
                EventBus.emit(EventType.Input, KeyStroke(it = InputType.Insert,
                        ctrlDown = ctrlDown,
                        altDown = altDown,
                        shiftDown = shiftDown))
            }
        } else if (e.keyCode == KeyEvent.VK_TAB) {
            if (e.isShiftDown) {
                EventBus.emit(EventType.Input, KeyStroke(it = InputType.ReverseTab,
                        ctrlDown = ctrlDown,
                        altDown = altDown,
                        shiftDown = shiftDown))
            } else {
                EventBus.emit(EventType.Input, KeyStroke(it = InputType.Tab,
                        ctrlDown = ctrlDown,
                        altDown = altDown,
                        shiftDown = shiftDown))
            }
        } else if (KEY_EVENT_TO_KEY_TYPE_LOOKUP.containsKey(e.keyCode)) {
            EventBus.emit(EventType.Input, KeyStroke(it = KEY_EVENT_TO_KEY_TYPE_LOOKUP[e.keyCode]!!,
                    ctrlDown = ctrlDown,
                    altDown = altDown,
                    shiftDown = shiftDown))
        } else {
            //keyTyped doesn't catch this scenario (for whatever reason...) so we have to do it here
            if (altDown && ctrlDown && e.keyCode >= 'A'.toByte() && e.keyCode <= 'Z'.toByte()) {
                var character = e.keyCode.toChar()
                if (!shiftDown) {
                    character = Character.toLowerCase(character)
                }
                EventBus.emit(EventType.Input, KeyStroke(
                        character = character,
                        ctrlDown = ctrlDown,
                        altDown = altDown,
                        shiftDown = shiftDown))
            }
        }
    }

    private fun pasteClipboardContent() {
        Toolkit.getDefaultToolkit().systemClipboard?.let {
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

    companion object {

        private val TYPED_KEYS_TO_IGNORE = HashSet(Arrays.asList('\n', '\t', '\r', '\b', '\u001b', 127.toChar()))
        private val KEY_EVENT_TO_KEY_TYPE_LOOKUP = mapOf(
                Pair(KeyEvent.VK_ENTER, InputType.Enter),
                Pair(KeyEvent.VK_ESCAPE, InputType.Escape),
                Pair(KeyEvent.VK_BACK_SPACE, InputType.Backspace),
                Pair(KeyEvent.VK_LEFT, InputType.ArrowLeft),
                Pair(KeyEvent.VK_RIGHT, InputType.ArrowRight),
                Pair(KeyEvent.VK_UP, InputType.ArrowUp),
                Pair(KeyEvent.VK_DOWN, InputType.ArrowDown),
                Pair(KeyEvent.VK_DELETE, InputType.Delete),
                Pair(KeyEvent.VK_HOME, InputType.Home),
                Pair(KeyEvent.VK_END, InputType.End),
                Pair(KeyEvent.VK_PAGE_UP, InputType.PageUp),
                Pair(KeyEvent.VK_PAGE_DOWN, InputType.PageDown),
                Pair(KeyEvent.VK_F1, InputType.F1),
                Pair(KeyEvent.VK_F2, InputType.F2),
                Pair(KeyEvent.VK_F3, InputType.F3),
                Pair(KeyEvent.VK_F4, InputType.F4),
                Pair(KeyEvent.VK_F5, InputType.F5),
                Pair(KeyEvent.VK_F6, InputType.F6),
                Pair(KeyEvent.VK_F7, InputType.F7),
                Pair(KeyEvent.VK_F8, InputType.F8),
                Pair(KeyEvent.VK_F9, InputType.F9),
                Pair(KeyEvent.VK_F10, InputType.F10),
                Pair(KeyEvent.VK_F11, InputType.F11),
                Pair(KeyEvent.VK_F12, InputType.F12))
    }
}