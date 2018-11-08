package org.hexworks.zircon.internal.grid

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

class TerminalKeyListener() : KeyAdapter() {

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
                Zircon.eventBus.broadcast(
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
                Zircon.eventBus.broadcast(
                        event = ZirconEvent.Input(KeyStroke(type = InputType.Insert,
                                ctrlDown = ctrlDown,
                                altDown = altDown,
                                shiftDown = shiftDown)),
                        eventScope = ZirconScope)
            }
        } else if (e.keyCode == KeyEvent.VK_TAB) {
            if (e.isShiftDown) {
                Zircon.eventBus.broadcast(
                        event = ZirconEvent.Input(KeyStroke(type = InputType.ReverseTab,
                                ctrlDown = ctrlDown,
                                altDown = altDown,
                                shiftDown = shiftDown)),
                        eventScope = ZirconScope)
            } else {
                Zircon.eventBus.broadcast(
                        event = ZirconEvent.Input(KeyStroke(type = InputType.Tab,
                                ctrlDown = ctrlDown,
                                altDown = altDown,
                                shiftDown = shiftDown)),
                        eventScope = ZirconScope)
            }
        } else if (KEY_EVENT_TO_KEY_TYPE_LOOKUP.containsKey(e.keyCode)) {
            Zircon.eventBus.broadcast(
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
                Zircon.eventBus.broadcast(
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
            Zircon.eventBus.broadcast(
                    event = ZirconEvent.Input(KeyStroke(character = it)),
                    eventScope = ZirconScope)
        }
    }

    companion object {

        private val TYPED_KEYS_TO_IGNORE = HashSet(Arrays.asList('\n', '\t', '\r', '\b', '\u001b', 127.toChar()))

        private val KEY_EVENT_TO_KEY_TYPE_LOOKUP = mapOf(
                Pair(KeyEvent.VK_ENTER, InputType.Enter), Pair(KeyEvent.VK_ESCAPE, InputType.Escape),
                Pair(KeyEvent.VK_BACK_SPACE, InputType.Backspace), Pair(KeyEvent.VK_LEFT, InputType.ArrowLeft),
                Pair(KeyEvent.VK_RIGHT, InputType.ArrowRight), Pair(KeyEvent.VK_UP, InputType.ArrowUp),
                Pair(KeyEvent.VK_DOWN, InputType.ArrowDown), Pair(KeyEvent.VK_DELETE, InputType.Delete),
                Pair(KeyEvent.VK_HOME, InputType.Home), Pair(KeyEvent.VK_END, InputType.End),
                Pair(KeyEvent.VK_PAGE_UP, InputType.PageUp), Pair(KeyEvent.VK_PAGE_DOWN, InputType.PageDown),
                Pair(KeyEvent.VK_F1, InputType.F1), Pair(KeyEvent.VK_F2, InputType.F2),
                Pair(KeyEvent.VK_F3, InputType.F3), Pair(KeyEvent.VK_F4, InputType.F4),
                Pair(KeyEvent.VK_F5, InputType.F5), Pair(KeyEvent.VK_F6, InputType.F6),
                Pair(KeyEvent.VK_F7, InputType.F7), Pair(KeyEvent.VK_F8, InputType.F8),
                Pair(KeyEvent.VK_F9, InputType.F9), Pair(KeyEvent.VK_F10, InputType.F10),
                Pair(KeyEvent.VK_F11, InputType.F11), Pair(KeyEvent.VK_F12, InputType.F12))
    }
}
