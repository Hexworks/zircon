package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.component.data.ComponentState.*
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType.*
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.internal.component.InternalComponent
import org.junit.Test

@Suppress("PropertyName", "TestFunctionName")
abstract class ComponentImplementationTest<T : InternalComponent> : CommonComponentTest<T>() {

    abstract val drawWindow: DrawWindow

    @Test
    fun When_render_is_called_on_a_component_Then_it_gets_rendered() {
        rendererStub.clear()

        rendererStub.render(drawWindow, ComponentRenderContext(target))

        assertThat(rendererStub.renderedOnce()).isTrue
    }

    @Test
    open fun When_a_highlighted_component_with_focus_is_activated_Then_it_becomes_active() {
        target.mouseEntered(
            event = MouseEvent(MOUSE_ENTERED, 1, Position.zero()),
            phase = UIEventPhase.TARGET
        )
        target.focusGiven()
        rendererStub.clear()
        target.activated()

        assertThat(target.componentState).isEqualTo(ACTIVE)
    }

    @Test
    open fun When_a_highlighted_component_without_focus_is_no_longer_hovered_Then_it_becomes_default() {
        target.mouseEntered(
            event = MouseEvent(MOUSE_ENTERED, 1, Position.zero()),
            phase = UIEventPhase.TARGET
        )
        rendererStub.clear()
        target.mouseExited(
            event = MouseEvent(MOUSE_EXITED, 1, Position.zero()),
            phase = UIEventPhase.TARGET
        )

        assertThat(target.componentState).isEqualTo(DEFAULT)
    }

    @Test
    open fun When_a_highlighted_component_without_focus_is_activated_Then_it_becomes_active() {
        target.mouseEntered(
            event = MouseEvent(MOUSE_ENTERED, 1, Position.zero()),
            phase = UIEventPhase.TARGET
        )
        rendererStub.clear()
        target.activated()

        assertThat(target.componentState).isEqualTo(ACTIVE)
    }

    @Test
    open fun When_a_focused_component_is_hovered_Then_it_becomes_highlighted() {

        target.focusGiven()
        rendererStub.clear()
        target.mouseEntered(
            event = MouseEvent(MOUSE_ENTERED, 1, Position.zero()),
            phase = UIEventPhase.TARGET
        )

        assertThat(target.componentState).isEqualTo(HIGHLIGHTED)
    }

}
