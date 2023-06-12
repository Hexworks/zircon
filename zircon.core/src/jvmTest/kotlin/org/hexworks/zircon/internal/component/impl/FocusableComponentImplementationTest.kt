package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.data.ComponentState.FOCUSED
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.internal.component.InternalComponent
import org.junit.Test

@Suppress("TestFunctionName")
abstract class FocusableComponentImplementationTest<T : InternalComponent> : ComponentImplementationTest<T>() {

    @Test
    open fun When_an_active_component_is_deactivated_Then_it_becomes_focused() {

        target.focusGiven()
        target.activated()
        rendererStub.clear()

        target.deactivated()

        assertThat(target.componentState).isEqualTo(FOCUSED)
    }

    @Test
    open fun When_a_highlighted_component_with_focus_is_no_longer_hovered_Then_it_becomes_focused() {
        target.mouseEntered(
            event = MouseEvent(MouseEventType.MOUSE_ENTERED, 1, Position.zero()),
            phase = UIEventPhase.TARGET
        )
        target.focusGiven()
        rendererStub.clear()
        target.mouseExited(
            event = MouseEvent(MouseEventType.MOUSE_EXITED, 1, Position.zero()),
            phase = UIEventPhase.TARGET
        )

        assertThat(target.componentState).isEqualTo(FOCUSED)
    }

    @Test
    open fun When_focus_is_taken_from_a_focused_component_Then_it_becomes_default() {

        target.focusGiven()
        rendererStub.clear()
        target.focusTaken()

        assertThat(target.componentState).isEqualTo(ComponentState.DEFAULT)
    }

    @Test
    open fun When_the_mouse_is_exited_from_an_active_component_Then_it_becomes_focused() {

        target.focusGiven()
        target.activated()
        rendererStub.clear()

        target.mouseExited(
            event = MouseEvent(MouseEventType.MOUSE_EXITED, 1, Position.zero()),
            phase = UIEventPhase.TARGET
        )

        assertThat(target.componentState).isEqualTo(FOCUSED)
    }


    @Test
    open fun When_a_focused_component_is_activated_Then_it_becomes_active() {

        target.focusGiven()
        rendererStub.clear()
        target.activated()

        assertThat(target.componentState).isEqualTo(ComponentState.ACTIVE)
    }

    @Test
    open fun When_the_mouse_is_released_on_a_focused_component_Then_it_becomes_highlighted() {

        target.focusGiven()
        rendererStub.clear()
        target.mouseReleased(
            event = MouseEvent(MouseEventType.MOUSE_RELEASED, 1, Position.zero()),
            phase = UIEventPhase.TARGET
        )

        assertThat(target.componentState).isEqualTo(ComponentState.HIGHLIGHTED)
    }

    @Test
    open fun When_the_mouse_is_released_on_an_active_component_Then_it_becomes_highlighted() {

        target.focusGiven()
        target.activated()
        rendererStub.clear()

        target.mouseReleased(
            event = MouseEvent(MouseEventType.MOUSE_RELEASED, 1, Position.zero()),
            phase = UIEventPhase.TARGET
        )

        assertThat(target.componentState).isEqualTo(ComponentState.HIGHLIGHTED)
    }
}
