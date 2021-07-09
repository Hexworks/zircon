package org.hexworks.zircon.internal.uievent

import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.KeyboardEventType.*
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.api.uievent.UIEventPhase.TARGET
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class KeyboardEventListener : KeyListener {

    private val events = mutableListOf<Pair<KeyboardEvent, UIEventPhase>>()

    fun drainEvents(): Iterable<Pair<KeyboardEvent, UIEventPhase>> {
        return events.toList().also {
            events.clear()
        }
    }

    override fun keyPressed(e: KeyEvent) {
        val keyboardEvent = createKeyboardEvent(e, KEY_PRESSED)
        events.add(keyboardEvent to TARGET)
    }

    override fun keyTyped(e: KeyEvent) {
        events.add(createKeyboardEvent(e, KEY_TYPED) to TARGET)
    }

    override fun keyReleased(e: KeyEvent) {
        events.add(createKeyboardEvent(e, KEY_RELEASED) to TARGET)
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
            shiftDown = shiftDown
        )
    }
}
