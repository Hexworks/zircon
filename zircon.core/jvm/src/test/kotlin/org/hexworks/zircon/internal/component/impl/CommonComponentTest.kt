package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.ComponentState.*
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.KeyStroke
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.input.MouseActionType.*
import org.hexworks.zircon.api.kotlin.*
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.component.InternalComponent
import org.junit.Test

@Suppress("PropertyName")
abstract class CommonComponentTest<T : InternalComponent> {

    abstract val target: T
    abstract val expectedComponentStyles: ComponentStyleSet

    lateinit var componentStub: ComponentStub
    lateinit var rendererStub: ComponentRendererStub<T>

    val DEFAULT_THEME = ColorThemeResource.CYBERPUNK.getTheme()
    val POSITION_2_3 = Position.create(2, 3)
    val SIZE_3_4 = Size.create(3, 4)
    val TILESET_REX_PAINT_20X20 = BuiltInCP437TilesetResource.REX_PAINT_20X20
    val COMPONENT_STYLES = ComponentStyleSet.defaultStyleSet()
    val COMMON_COMPONENT_METADATA = ComponentMetadata(
            position = POSITION_2_3,
            size = SIZE_3_4,
            tileset = TILESET_REX_PAINT_20X20,
            componentStyleSet = COMPONENT_STYLES)

    abstract fun setUp()

    @Test
    fun shouldUseProperTileset() {
        assertThat(target.currentTileset().id)
                .isEqualTo(TILESET_REX_PAINT_20X20.id)
    }

    @Test
    fun shouldProperlyChangeTileset() {
        val newTileset = BuiltInCP437TilesetResource.TAFFER_20X20
        target.useTileset(newTileset)
        assertThat(target.currentTileset().id)
                .isEqualTo(newTileset.id)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotAllowTilesetWithDifferentSize() {
        target.useTileset(BuiltInCP437TilesetResource.ACORN_8X16)
    }

    @Test
    open fun shouldProperlyHandleKeyStroked() {
        rendererStub.clear()

        target.keyStroked(KeyStroke('x'))
    }

    @Test
    open fun shouldProperlyHandleMouseClicked() {
        rendererStub.clear()

        target.mouseClicked(MouseAction(MOUSE_CLICKED, 1, Position.zero()))
    }


    @Test
    open fun shouldProperlyHandleMouseWheelRotatedUp() {
        rendererStub.clear()

        target.mouseWheelRotatedUp(MouseAction(MOUSE_WHEEL_ROTATED_UP, 1, Position.zero()))
    }

    @Test
    open fun shouldProperlyHandleMouseWheelRotatedDown() {
        rendererStub.clear()

        target.mouseWheelRotatedDown(MouseAction(MOUSE_WHEEL_ROTATED_DOWN, 1, Position.zero()))
    }

    @Test
    open fun shouldProperlyHandleMouseDragged() {
        rendererStub.clear()

        target.mouseDragged(MouseAction(MOUSE_DRAGGED, 1, Position.zero()))
    }

    @Test
    open fun shouldProperlyHandleMouseMoved() {
        rendererStub.clear()

        target.mouseMoved(MouseAction(MOUSE_MOVED, 1, Position.zero()))
    }

    @Test
    open fun shouldProperlyHandleOnInput() {
        var maybeInput = Maybe.empty<Input>()
        target.onInput {
            maybeInput = Maybe.of(it)
        }

        val input = KeyStroke()

        target.inputEmitted(input)

        assertThat(maybeInput.get()).isEqualTo(input)
    }

    @Test
    open fun shouldProperlyHandleOnKeyStroke() {
        var maybeKeyStroke = Maybe.empty<KeyStroke>()
        target.onKeyStroke {
            maybeKeyStroke = Maybe.of(it)
        }

        val input = KeyStroke()

        target.inputEmitted(input)

        assertThat(maybeKeyStroke.get()).isEqualTo(input)
    }

    @Test
    open fun shouldProperlyHandleOnMouseClicked() {
        var maybeInput = Maybe.empty<MouseAction>()
        target.onMouseClicked {
            maybeInput = Maybe.of(it)
        }

        val mouseAction = MouseAction(MOUSE_CLICKED, 1, Position.zero())

        target.inputEmitted(mouseAction)

        assertThat(maybeInput.get()).isEqualTo(mouseAction)
    }

    @Test
    open fun shouldProperlyHandleOnMousePressed() {
        var maybeInput = Maybe.empty<MouseAction>()
        target.onMousePressed {
            maybeInput = Maybe.of(it)
        }

        val mouseAction = MouseAction(MOUSE_PRESSED, 1, Position.zero())

        target.inputEmitted(mouseAction)

        assertThat(maybeInput.get()).isEqualTo(mouseAction)
    }

    @Test
    open fun shouldProperlyHandleOnMouseReleased() {
        var maybeInput = Maybe.empty<MouseAction>()
        target.onMouseReleased {
            maybeInput = Maybe.of(it)
        }

        val mouseAction = MouseAction(MOUSE_RELEASED, 1, Position.zero())

        target.inputEmitted(mouseAction)

        assertThat(maybeInput.get()).isEqualTo(mouseAction)
    }

    @Test
    open fun shouldProperlyHandleOnMouseEntered() {
        var maybeInput = Maybe.empty<MouseAction>()
        target.onMouseEntered {
            maybeInput = Maybe.of(it)
        }

        val mouseAction = MouseAction(MOUSE_ENTERED, 1, Position.zero())

        target.inputEmitted(mouseAction)

        assertThat(maybeInput.get()).isEqualTo(mouseAction)
    }

    @Test
    open fun shouldProperlyHandleOnMouseExited() {
        var maybeInput = Maybe.empty<MouseAction>()
        target.onMouseExited {
            maybeInput = Maybe.of(it)
        }

        val mouseAction = MouseAction(MOUSE_EXITED, 1, Position.zero())

        target.inputEmitted(mouseAction)

        assertThat(maybeInput.get()).isEqualTo(mouseAction)
    }

    @Test
    open fun shouldProperlyHandleOnMouseWheelRotatedUp() {
        var maybeInput = Maybe.empty<MouseAction>()
        target.onMouseWheelRotatedUp {
            maybeInput = Maybe.of(it)
        }

        val mouseAction = MouseAction(MOUSE_WHEEL_ROTATED_UP, 1, Position.zero())

        target.inputEmitted(mouseAction)

        assertThat(maybeInput.get()).isEqualTo(mouseAction)
    }

    @Test
    open fun shouldProperlyHandleOnMouseWheelRotatedDown() {
        var maybeInput = Maybe.empty<MouseAction>()
        target.onMouseWheelRotatedDown {
            maybeInput = Maybe.of(it)
        }

        val mouseAction = MouseAction(MOUSE_WHEEL_ROTATED_DOWN, 1, Position.zero())

        target.inputEmitted(mouseAction)

        assertThat(maybeInput.get()).isEqualTo(mouseAction)
    }

    @Test
    open fun shouldProperlyHandleOnMouseDragged() {
        var maybeInput = Maybe.empty<MouseAction>()
        target.onMouseDragged {
            maybeInput = Maybe.of(it)
        }

        val mouseAction = MouseAction(MOUSE_DRAGGED, 1, Position.zero())

        target.inputEmitted(mouseAction)

        assertThat(maybeInput.get()).isEqualTo(mouseAction)
    }

    @Test
    open fun shouldProperlyHandleOnMouseMoved() {
        var maybeInput = Maybe.empty<MouseAction>()
        target.onMouseMoved {
            maybeInput = Maybe.of(it)
        }

        val mouseAction = MouseAction(MOUSE_MOVED, 1, Position.zero())

        target.inputEmitted(mouseAction)

        assertThat(maybeInput.get()).isEqualTo(mouseAction)
    }


}
