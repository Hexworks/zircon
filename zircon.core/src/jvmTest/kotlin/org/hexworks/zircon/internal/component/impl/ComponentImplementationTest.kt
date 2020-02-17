package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.component.data.ComponentState.*
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType.*
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.internal.component.InternalComponent
import org.junit.Test

@Suppress("PropertyName")
abstract class ComponentImplementationTest<T : InternalComponent> : CommonComponentTest<T>() {

    @Test
    fun shouldRenderOnInit() {
        assertThat(rendererStub.renderings.size).isEqualTo(1)
    }

    @Test
    fun shouldRenderComponentWhenRenderIsCalled() {
        rendererStub.clear()

        target.render()

        assertThat(rendererStub.renderedOnce()).isTrue()
    }

    @Test
    open fun shouldProperlyHandleMousePressed() {
        rendererStub.clear()

        target.mousePressed(
                event = MouseEvent(MOUSE_PRESSED, 1, Position.zero()),
                phase = UIEventPhase.TARGET)

        assertThat(target.componentState).isEqualTo(ACTIVE)
        assertThat(rendererStub.renderings.size).isEqualTo(1)
    }

    @Test
    open fun shouldProperlyHandleMouseReleased() {
        rendererStub.clear()

        target.mouseReleased(
                event = MouseEvent(MOUSE_RELEASED, 1, Position.zero()),
                phase = UIEventPhase.TARGET)

        assertThat(target.componentState).isEqualTo(HIGHLIGHTED)
        assertThat(rendererStub.renderings.size).isGreaterThanOrEqualTo(1)
    }

    @Test
    open fun shouldProperlyHandleMouseEntered() {
        rendererStub.clear()

        target.mouseEntered(
                event = MouseEvent(MOUSE_ENTERED, 1, Position.zero()),
                phase = UIEventPhase.TARGET)

        assertThat(target.componentState).isEqualTo(HIGHLIGHTED)
        assertThat(rendererStub.renderings.size).isEqualTo(1)
    }

    @Test
    open fun shouldProperlyHandleMouseExited() {
        rendererStub.clear()

        target.mouseExited(
                event = MouseEvent(MOUSE_EXITED, 1, Position.zero()),
                phase = UIEventPhase.TARGET)

        assertThat(target.componentState).isEqualTo(DEFAULT)
        assertThat(rendererStub.renderings.size).isEqualTo(1)
    }


}
