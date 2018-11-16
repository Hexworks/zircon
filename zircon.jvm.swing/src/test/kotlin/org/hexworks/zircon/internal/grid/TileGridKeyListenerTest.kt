package org.hexworks.zircon.internal.grid

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.cobalt.events.api.subscribe
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.input.InputType.Character
import org.hexworks.zircon.api.input.KeyStroke
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconScope
import org.junit.Before
import org.junit.Test
import java.awt.Component
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import java.util.*

class TileGridKeyListenerTest {

    lateinit var target: TerminalKeyListener

    val inputs = LinkedList<Input>()

    @Before
    fun setUp() {
        target = TerminalKeyListener()
        Zircon.eventBus.subscribe<ZirconEvent.Input>(ZirconScope) {
            inputs.add(it.input)
        }
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
        const val CHAR = 'x'
        const val FONT_SIZE = 16
        const val BUTTON = 2
        private val POSITION = Position.create(2, 3)
        val X = POSITION.x * FONT_SIZE
        val Y = POSITION.y * FONT_SIZE
        val DUMMY_COMPONENT = object : Component() {}
    }
}
