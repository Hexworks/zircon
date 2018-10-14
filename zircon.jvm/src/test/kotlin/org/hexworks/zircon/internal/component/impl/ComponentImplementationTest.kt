package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.component.data.ComponentState.*
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.input.MouseActionType.*
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
    fun shouldProperlyApplyTheme() {
        target.applyColorTheme(DEFAULT_THEME)

        assertThat(target.componentStyleSet).isEqualTo(expectedComponentStyles)
    }

    @Test
    open fun shouldProperlyHandleMousePressed() {
        rendererStub.clear()

        target.mousePressed(MouseAction(MOUSE_PRESSED, 1, Position.zero()))

        assertThat(target.componentStyleSet.currentState()).isEqualTo(ACTIVE)
        assertThat(rendererStub.renderings.size).isEqualTo(1)
    }

    @Test
    open fun shouldProperlyHandleMouseReleased() {
        rendererStub.clear()

        target.mouseReleased(MouseAction(MOUSE_RELEASED, 1, Position.zero()))

        assertThat(target.componentStyleSet.currentState()).isEqualTo(MOUSE_OVER)
        assertThat(rendererStub.renderings.size).isEqualTo(1)
    }

    @Test
    open fun shouldProperlyHandleMouseEntered() {
        rendererStub.clear()

        target.mouseEntered(MouseAction(MOUSE_ENTERED, 1, Position.zero()))

        assertThat(target.componentStyleSet.currentState()).isEqualTo(MOUSE_OVER)
        assertThat(rendererStub.renderings.size).isEqualTo(1)
    }

    @Test
    open fun shouldProperlyHandleMouseExited() {
        rendererStub.clear()

        target.mouseExited(MouseAction(MOUSE_EXITED, 1, Position.zero()))

        assertThat(target.componentStyleSet.currentState()).isEqualTo(DEFAULT)
        assertThat(rendererStub.renderings.size).isEqualTo(1)
    }


}
