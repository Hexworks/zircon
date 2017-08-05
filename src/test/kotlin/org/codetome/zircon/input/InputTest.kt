package org.codetome.zircon.input

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.Position
import org.junit.Test

class InputTest {

    @Test
    fun keyStrokeShouldBeKeyStroke() {
        assertThat(KeyStroke.EOF_STROKE.isKeyStroke()).isTrue()
    }

    @Test
    fun mouseActionShouldBeMouseAction() {
        assertThat(fetchMouseAction().isMouseAction()).isTrue()
    }

    @Test
    fun aMouseActionShouldHaveMouseActionInputType() {
        assertThat(fetchMouseAction().getInputType())
                .isEqualTo(InputType.MouseEvent)
    }

    private fun fetchMouseAction(): MouseAction {
        return MouseAction(
                actionType = MouseActionType.MOUSE_WHEEL_ROTATED_UP,
                button = 1,
                position = Position.DEFAULT_POSITION
        )
    }

}