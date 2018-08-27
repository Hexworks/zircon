package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.component.ButtonBuilder
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.component.PanelBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.input.KeyStroke
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.input.MouseActionType.*
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.internal.component.impl.wrapping.BorderWrappingStrategy
import org.hexworks.zircon.internal.component.impl.wrapping.ShadowWrappingStrategy
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.api.Modifiers
import org.junit.Before
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean

class DefaultComponentContainerTest {

    lateinit var target: DefaultComponentContainer

    @Before
    fun setUp() {
        target = DefaultComponentContainer(DefaultContainer(
                initialSize = SIZE,
                position = Position.defaultPosition(),
                componentStyleSet = STYLES,
                wrappers = WRAPPERS,
                initialTileset = TILESET))
    }

    @Test
    fun shouldProperlyRemoveComponent() {
        val button = createButton()
        target.addComponent(button)
        assertThat(target.transformComponentsToLayers()).hasSize(2)

        val result = target.removeComponent(button)

        assertThat(result).isTrue()
        assertThat(target.transformComponentsToLayers()).hasSize(1) // default container
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotLetToAddAComponentWhichIsBiggerThanTheContainer() {
        target.addComponent(PanelBuilder.newBuilder()
                .size(Size.create(999, 999))
                .build())
    }

    @Test
    fun shouldProperlyHandleMouseOverWhenActive() {
        target.activate()

        val button = createButton()
        target.addComponent(button)

        val componentHovered = AtomicBoolean(false)
        EventBus.listenTo<ZirconEvent.MouseOver>(button.id) {
            componentHovered.set(true)
        }

        EventBus.broadcast(ZirconEvent.Input(MouseAction(MOUSE_MOVED, 1, BUTTON_POSITION)))

        assertThat(componentHovered.get()).isTrue()
    }

    @Test
    fun shouldProperlyHandleMouseOverWhenHoveredTwice() {
        target.activate()

        val button = createButton()
        target.addComponent(button)

        EventBus.broadcast(ZirconEvent.Input(MouseAction(MOUSE_MOVED, 1, BUTTON_POSITION)))

        val componentHovered = AtomicBoolean(false)
        EventBus.listenTo<ZirconEvent.MouseOver>(button.id) {
            componentHovered.set(true)
        }

        EventBus.broadcast(ZirconEvent.Input(MouseAction(MOUSE_MOVED, 1, BUTTON_POSITION.withRelativeX(1))))

        assertThat(componentHovered.get()).isFalse()
    }

    @Test
    fun shouldProperlyHandleMousePressedWhenActive() {
        target.activate()

        val button = createButton()
        target.addComponent(button)

        val pressed = AtomicBoolean(false)
        EventBus.listenTo<ZirconEvent.MousePressed>(button.id) {
            pressed.set(true)
        }

        EventBus.broadcast(ZirconEvent.Input(MouseAction(MOUSE_PRESSED, 1, BUTTON_POSITION)))

        assertThat(pressed.get()).isTrue()
    }

    @Test
    fun shouldProperlyHandleMouseReleasedWhenActive() {
        target.activate()

        val button = createButton()
        target.addComponent(button)

        val released = AtomicBoolean(false)
        EventBus.listenTo<ZirconEvent.MouseReleased>(button.id) {
            released.set(true)
        }

        EventBus.broadcast(ZirconEvent.Input(MouseAction(MOUSE_RELEASED, 1, BUTTON_POSITION)))

        assertThat(released.get()).isTrue()
    }

    @Test
    fun shouldNotHandleEventsWhenInactive() {
        target.deactivate()

        val button = createButton()
        target.addComponent(button)

        val events = mutableListOf<Boolean>()
        EventBus.listenTo<ZirconEvent.MouseOver>(button.id) {
            events.add(true)
        }
        EventBus.listenTo<ZirconEvent.MousePressed>(button.id) {
            events.add(true)
        }
        EventBus.listenTo<ZirconEvent.MouseReleased>(button.id) {
            events.add(true)
        }

        EventBus.broadcast(ZirconEvent.Input(MouseAction(MOUSE_MOVED, 1, BUTTON_POSITION)))
        EventBus.broadcast(ZirconEvent.Input(MouseAction(MOUSE_PRESSED, 1, BUTTON_POSITION)))
        EventBus.broadcast(ZirconEvent.Input(MouseAction(MOUSE_RELEASED, 1, BUTTON_POSITION)))


        assertThat(events).isEmpty()
    }

    @Test
    fun shouldProperlyFocusNextWhenTabPressed() {
        target.activate()

        val button = createButton()
        target.addComponent(button)

        assertThat(button.getComponentStyles().getCurrentStyle()).isNotEqualTo(FOCUSED_STYLE)

        EventBus.broadcast(ZirconEvent.Input(KeyStroke(type = InputType.Tab)))

        assertThat(button.getComponentStyles().getCurrentStyle()).isEqualTo(FOCUSED_STYLE)
    }

    // TODO: FIX THIS
//    @Test
//    fun shouldProperlyFocusPrevWhenShiftTabPressed() {
//        target.applyActiveStyle()
//
//        val button = createButton()
//        target.addComponent(button)
//        val other = ButtonBuilder.newBuilder()
//                .text(BUTTON_TEXT)
//                .position(Position.create(0, 1)
//                        .relativeToBottomOf(button))
//                .build()
//        target.addComponent(other)
//
//        EventBus.broadcast<Input>(EventType.Input, KeyStroke(type = InputType.Tab))
//        EventBus.broadcast<Input>(EventType.Input, KeyStroke(type = InputType.Tab))
//
//        assertThat(button.getComponentStyles().getCurrentStyle()).isEqualTo(DEFAULT_STYLE)
//
//        EventBus.broadcast<Input>(EventType.Input, KeyStroke(shiftDown = true, type = InputType.ReverseTab))
//
//        assertThat(button.getComponentStyles().getCurrentStyle()).isEqualTo(FOCUSED_STYLE)
//    }

    @Test
    fun shouldProperlyHandleSpacePressedOnFocusedWhenActive() {
        target.activate()

        val button = createButton()
        target.addComponent(button)

        val released = AtomicBoolean(false)
        EventBus.listenTo<ZirconEvent.MouseReleased>(button.id) {
            released.set(true)
        }

        EventBus.broadcast(ZirconEvent.Input(KeyStroke(type = InputType.Tab)))
        EventBus.broadcast(ZirconEvent.Input(KeyStroke(type = InputType.Character, character = ' ')))

        assertThat(released.get()).isTrue()
    }

    private fun createButton() = ButtonBuilder.newBuilder()
            .position(BUTTON_POSITION)
            .text(BUTTON_TEXT)
            .componentStyles(STYLES)
            .build()

    companion object {
        val TILESET = BuiltInCP437TilesetResource.REX_PAINT_16X16
        val SIZE = Size.create(30, 20)
        val BUTTON_TEXT = "TEXT"
        val BUTTON_POSITION = Position.create(6, 7)
        val DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .backgroundColor(ANSITileColor.BLUE)
                .foregroundColor(ANSITileColor.RED)
                .build()
        val ACTIVE_STYLE = StyleSetBuilder.newBuilder()
                .backgroundColor(ANSITileColor.GREEN)
                .foregroundColor(ANSITileColor.YELLOW)
                .build()
        val DISABLED_STYLE = StyleSetBuilder.newBuilder()
                .backgroundColor(ANSITileColor.MAGENTA)
                .foregroundColor(ANSITileColor.BLUE)
                .build()
        val FOCUSED_STYLE = StyleSetBuilder.newBuilder()
                .backgroundColor(ANSITileColor.YELLOW)
                .foregroundColor(ANSITileColor.CYAN)
                .build()
        val MOUSE_OVER_STYLE = StyleSetBuilder.newBuilder()
                .backgroundColor(ANSITileColor.RED)
                .foregroundColor(ANSITileColor.CYAN)
                .build()
        val STYLES = ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(DEFAULT_STYLE)
                .activeStyle(ACTIVE_STYLE)
                .disabledStyle(DISABLED_STYLE)
                .focusedStyle(FOCUSED_STYLE)
                .mouseOverStyle(MOUSE_OVER_STYLE)
                .build()
        val WRAPPERS = listOf(
                ShadowWrappingStrategy(),
                BorderWrappingStrategy(Modifiers.border()))
    }
}
