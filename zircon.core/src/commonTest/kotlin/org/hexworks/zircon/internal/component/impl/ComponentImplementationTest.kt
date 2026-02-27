package org.hexworks.zircon.internal.component.impl

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.component.data.ComponentState.*
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType.*
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.internal.component.InternalComponent
import kotlin.test.Test

@Suppress("TestFunctionName")
abstract class ComponentImplementationTest<T : InternalComponent> : CommonComponentTest<T>() {

    abstract val drawWindow: DrawWindow

    @Test
    fun When_render_is_called_on_a_component_Then_it_gets_rendered() {
        rendererStub.clear()

        rendererStub.render(drawWindow, ComponentRenderContext(target))

        rendererStub.renderedOnce() shouldBe true
    }

    @Test
    open fun When_a_highlighted_component_with_focus_is_activated_Then_it_becomes_active() {
        target.mouseEntered(
            event = MouseEvent(MOUSE_ENTERED, 1, ),
            phase = UIEventPhase.TARGET
        )
        target.focusGiven()
        rendererStub.clear()
        target.activated()

        target.componentState shouldBe ACTIVE
    }

    @Test
    open fun When_a_highlighted_component_without_focus_is_no_longer_hovered_Then_it_becomes_default() {
        target.mouseEntered(
            event = MouseEvent(MOUSE_ENTERED, 1, ),
            phase = UIEventPhase.TARGET
        )
        rendererStub.clear()
        target.mouseExited(
            event = MouseEvent(MOUSE_EXITED, 1, ),
            phase = UIEventPhase.TARGET
        )

        target.componentState shouldBe DEFAULT
    }

    @Test
    open fun When_a_highlighted_component_without_focus_is_activated_Then_it_becomes_active() {
        target.mouseEntered(
            event = MouseEvent(MOUSE_ENTERED, 1, ),
            phase = UIEventPhase.TARGET
        )
        rendererStub.clear()
        target.activated()

        target.componentState shouldBe ACTIVE
    }

    @Test
    open fun When_a_focused_component_is_hovered_Then_it_becomes_highlighted() {

        target.focusGiven()
        rendererStub.clear()
        target.mouseEntered(
            event = MouseEvent(MOUSE_ENTERED, 1, ),
            phase = UIEventPhase.TARGET
        )

        target.componentState shouldBe HIGHLIGHTED
    }

}
