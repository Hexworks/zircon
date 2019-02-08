package org.hexworks.zircon.internal.uievent

import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyCode.INSERT
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.KeyboardEventType.*
import org.hexworks.zircon.api.uievent.UIEventPhase.TARGET
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.grid.InternalTileGrid
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class KeyboardEventListener(private val tileGrid: InternalTileGrid) : KeyListener {

    override fun keyPressed(e: KeyEvent) {
        val keyboardEvent = createKeyboardEvent(e, KEY_PRESSED)

        // check for paste TODO: customizable?

        if (RuntimeConfig.config.isClipboardAvailable && keyboardEvent.isPasteEvent()) {
            pasteClipboardContent()
        } else {
            tileGrid.process(keyboardEvent, TARGET)
        }
    }

    override fun keyTyped(e: KeyEvent) {
        tileGrid.process(createKeyboardEvent(e, KEY_TYPED), TARGET)
    }

    override fun keyReleased(e: KeyEvent) {
        tileGrid.process(createKeyboardEvent(e, KEY_RELEASED), TARGET)
    }

    private fun createKeyboardEvent(e: KeyEvent, type: KeyboardEventType): KeyboardEvent {
        val keyChar = e.keyChar
        val keyCode = e.keyCode
        val ctrlDown = e.modifiersEx and InputEvent.CTRL_DOWN_MASK != 0
        val altDown = e.modifiersEx and InputEvent.ALT_DOWN_MASK != 0
        val metaDown = e.modifiersEx and InputEvent.META_DOWN_MASK != 0
        val shiftDown = e.modifiersEx and InputEvent.SHIFT_DOWN_MASK != 0

        return KeyboardEvent(
                type = type,
                key = "$keyChar",
                code = KeyCode.findByCode(keyCode),
                ctrlDown = ctrlDown,
                altDown = altDown,
                metaDown = metaDown,
                shiftDown = shiftDown)
    }

    private fun pasteClipboardContent() {
        Toolkit.getDefaultToolkit().systemClipboard?.let {
            injectStringAsKeyboardEvents(it.getData(DataFlavor.stringFlavor) as String, tileGrid)
        }
    }

    private fun KeyboardEvent.isPasteEvent() = (code == INSERT &&
            ctrlDown.not() && altDown.not() && metaDown.not() && shiftDown)
}
