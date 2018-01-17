package org.codetome.zircon.internal.terminal

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.builder.DeviceConfigurationBuilder
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.api.input.MouseActionType
import org.codetome.zircon.api.input.MouseActionType.*
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import org.junit.Before
import org.junit.Test
import java.awt.Component
import java.awt.event.MouseEvent
import java.util.*

class TerminalMouseListenerTest {

    lateinit var target: TerminalMouseListener
    lateinit var operations: Map<(MouseEvent) -> Unit, MouseActionType>

    val inputs = LinkedList<Input>()

    @Before
    fun setUp() {
        target = TerminalMouseListener(CONFIG, FONT_SIZE, FONT_SIZE)
        operations = mapOf(
                Pair(target::mouseClicked, MOUSE_CLICKED),
                Pair(target::mouseDragged, MOUSE_DRAGGED),
                Pair(target::mouseEntered, MOUSE_ENTERED),
                Pair(target::mouseExited, MOUSE_EXITED),
                Pair(target::mousePressed, MOUSE_PRESSED),
                Pair(target::mouseReleased, MOUSE_RELEASED))
        EventBus.subscribe<Input>(EventType.Input, {
            inputs.add(it.data)
        })
    }

    @Test
    fun shouldProperlyHandleMouseEvents() {
        operations.forEach { (op, event) ->
            op.invoke(MOUSE_EVENT)
            assertThat(inputs.poll()).isEqualTo(MouseAction(
                    actionType = event,
                    button = BUTTON,
                    position = POSITION))

        }

    }

    companion object {
        val FONT_SIZE = 16
        val POSITION = Position.of(2, 3)
        val X = POSITION.column * FONT_SIZE
        val Y = POSITION.row * FONT_SIZE
        val BUTTON = 2
        val CONFIG = DeviceConfigurationBuilder.DEFAULT
        val DUMMY_COMPONENT = object : Component() {}
        val MOUSE_EVENT = MouseEvent(DUMMY_COMPONENT, 1, 1, 1, X, Y, 1, true, BUTTON)
    }
}
