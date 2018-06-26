package org.codetome.zircon.internal.terminal

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.builder.DeviceConfigurationBuilder
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.InputType
import org.codetome.zircon.api.input.InputType.Character
import org.codetome.zircon.api.input.KeyStroke
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.Event
import org.junit.Before
import org.junit.Test
import java.awt.Component
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import java.util.*

class TerminalKeyListenerTest {

    lateinit var target: TerminalKeyListener

    val inputs = LinkedList<Input>()

    @Before
    fun setUp() {
        target = TerminalKeyListener(CONFIG)
        EventBus.subscribe<Input>(Event.Input, {
            inputs.add(it.data)
        })
    }

    @Test
    fun shouldProperlyHandleTyping() {
        target.keyTyped(KeyEvent(DUMMY_COMPONENT, 1, 1L,
                InputEvent.ALT_DOWN_MASK or InputEvent.CTRL_DOWN_MASK or InputEvent.SHIFT_DOWN_MASK,
                KeyEvent.VK_X, CHAR))

        assertThat(inputs.first())
                .isEqualTo(KeyStroke(character = CHAR, type = Character, ctrlDown = true, altDown = true, shiftDown = true))
    }

    @Test
    fun shouldProperlyTestForInsert() {
        target.keyPressed(KeyEvent(DUMMY_COMPONENT, 1, 1L,
                InputEvent.ALT_DOWN_MASK or InputEvent.CTRL_DOWN_MASK,
                KeyEvent.VK_INSERT, CHAR))

        assertThat(inputs.first()).isEqualTo(KeyStroke(type = InputType.Insert,
                ctrlDown = true,
                altDown = true,
                shiftDown = false))
    }

    @Test
    fun shouldProperlyTestForTab() {
        target.keyPressed(KeyEvent(DUMMY_COMPONENT, 1, 1L,
                InputEvent.ALT_DOWN_MASK or InputEvent.CTRL_DOWN_MASK,
                KeyEvent.VK_TAB, CHAR))

        assertThat(inputs.first()).isEqualTo(KeyStroke(type = InputType.Tab,
                ctrlDown = true,
                altDown = true,
                shiftDown = false))
    }

    @Test
    fun shouldProperlyTestForReverseTab() {
        target.keyPressed(KeyEvent(DUMMY_COMPONENT, 1, 1L,
                InputEvent.SHIFT_DOWN_MASK,
                KeyEvent.VK_TAB, CHAR))

        assertThat(inputs.first()).isEqualTo(KeyStroke(type = InputType.ReverseTab,
                ctrlDown = false,
                altDown = false,
                shiftDown = true))
    }

    @Test
    fun shouldProperlyTestForSpecialKey() {
        target.keyPressed(KeyEvent(DUMMY_COMPONENT, 1, 1L,
                InputEvent.SHIFT_DOWN_MASK,
                KeyEvent.VK_F5, CHAR))

        assertThat(inputs.first()).isEqualTo(KeyStroke(type = InputType.F5,
                ctrlDown = false,
                altDown = false,
                shiftDown = true))
    }

    companion object {
        val CHAR = 'x'
        val FONT_SIZE = 16
        val POSITION = Position.create(2, 3)
        val X = POSITION.x * FONT_SIZE
        val Y = POSITION.y * FONT_SIZE
        val BUTTON = 2
        val CONFIG = DeviceConfigurationBuilder.DEFAULT
        val DUMMY_COMPONENT = object : Component() {}
    }
}
