package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.value.ValueValidationFailedException
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventType.KEY_PRESSED
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_CLICKED
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_DRAGGED
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_MOVED
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_PRESSED
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_WHEEL_ROTATED_DOWN
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_WHEEL_ROTATED_UP
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventPhase.TARGET
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.internal.resource.ColorThemeResource
import org.junit.Test

@Suppress("PropertyName")
abstract class CommonComponentTest<T : InternalComponent> {

    abstract val target: T
    abstract val expectedComponentStyles: ComponentStyleSet

    lateinit var componentStub: ComponentStub
    lateinit var rendererStub: ComponentRendererStub<T>

    val DEFAULT_THEME = ColorThemeResource.CYBERPUNK.getTheme()
    val POSITION_2_3 = Position.create(2, 3)
    val POSITION_1_1 = Position.create(1, 1)
    val SIZE_3_4 = Size.create(3, 4)
    val SIZE_10_4 = Size.create(10, 4)
    val TILESET_REX_PAINT_20X20 = CP437TilesetResources.rexPaint20x20()
    val COMPONENT_STYLES = ComponentStyleSet.defaultStyleSet()
    val COMMON_COMPONENT_METADATA = ComponentMetadata(
        relativePosition = POSITION_2_3,
        size = SIZE_3_4,
        tilesetProperty = TILESET_REX_PAINT_20X20.toProperty(),
        componentStyleSetProperty = COMPONENT_STYLES.toProperty()
    )

    abstract fun setUp()

    @Test
    fun shouldUseProperTileset() {
        assertThat(target.tileset.id)
            .isEqualTo(TILESET_REX_PAINT_20X20.id)
    }

    @Test
    fun shouldProperlyChangeTileset() {
        val newTileset = BuiltInCP437TilesetResource.TAFFER_20X20
        target.tileset = newTileset
        assertThat(target.tileset.id)
            .isEqualTo(newTileset.id)
    }

    @Test(expected = ValueValidationFailedException::class)
    fun shouldNotAllowTilesetWithDifferentSize() {
        target.tileset = BuiltInCP437TilesetResource.ACORN_8X16
    }

    @Test
    open fun shouldProperlyHandleKeyStroked() {
        rendererStub.clear()

        target.keyPressed(
            KeyboardEvent(
                type = KEY_PRESSED,
                key = "x",
                code = KeyCode.KEY_X
            ), TARGET
        )
    }

    @Test
    open fun shouldProperlyHandleMouseClicked() {
        rendererStub.clear()

        target.mouseClicked(MouseEvent(MOUSE_CLICKED, 1, Position.zero()), TARGET)
    }


    @Test
    open fun shouldProperlyHandleMouseWheelRotatedUp() {
        rendererStub.clear()

        target.mouseWheelRotatedUp(
            event = MouseEvent(MOUSE_WHEEL_ROTATED_UP, 1, Position.zero()),
            phase = TARGET
        )
    }

    @Test
    open fun shouldProperlyHandleMouseWheelRotatedDown() {
        rendererStub.clear()

        target.mouseWheelRotatedDown(
            event = MouseEvent(MOUSE_WHEEL_ROTATED_DOWN, 1, Position.zero()),
            phase = TARGET
        )
    }

    @Test
    open fun shouldProperlyHandleMouseDragged() {
        rendererStub.clear()

        target.mouseDragged(
            event = MouseEvent(MOUSE_DRAGGED, 1, Position.zero()),
            phase = TARGET
        )
    }

    @Test
    open fun shouldProperlyHandleMouseMoved() {
        rendererStub.clear()

        target.mouseMoved(
            event = MouseEvent(MOUSE_MOVED, 1, Position.zero()),
            phase = TARGET
        )
    }


    @Test
    open fun shouldProperlyHandleKeyboardEvents() {
        var maybeEvent = Maybe.empty<KeyboardEvent>()
        target.handleKeyboardEvents(KEY_PRESSED) { event, _ ->
            maybeEvent = Maybe.of(event)
            Processed
        }

        val keyboardEvent = KeyboardEvent(
            type = KEY_PRESSED,
            key = "x",
            code = KeyCode.KEY_X
        )

        target.process(keyboardEvent, TARGET)

        assertThat(maybeEvent.get()).isEqualTo(keyboardEvent)
    }

    @Test
    open fun shouldProperlyHandleOnMouseEvent() {
        var maybeEvent = Maybe.empty<MouseEvent>()
        target.handleMouseEvents(MOUSE_PRESSED) { event, _ ->
            maybeEvent = Maybe.of(event)
            Processed
        }

        val event = MouseEvent(
            type = MOUSE_PRESSED,
            button = 1,
            position = Position.defaultPosition()
        )

        target.process(event, TARGET)

        assertThat(maybeEvent.get()).isEqualTo(event)
    }


}
