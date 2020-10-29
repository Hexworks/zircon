package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ComponentDecorations
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.builder.component.ButtonBuilder
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.component.PanelBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_ENTERED
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_MOVED
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_PRESSED
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_RELEASED
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.RootContainerRenderer
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.contracts.ExperimentalContracts

@Suppress("TestFunctionName")
@ExperimentalContracts
class DefaultComponentContainerTest {

    lateinit var target: DefaultComponentContainer

    @Before
    fun setUp() {
        target = DefaultComponentContainer(DefaultRootContainer(
                componentMetadata = ComponentMetadata(
                        relativePosition = Position.defaultPosition(),
                        size = SIZE,
                        tileset = TILESET,
                        componentStyleSet = buildStyles()),
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = listOf(),
                        componentRenderer = RootContainerRenderer())))
    }

    @Test
    fun shouldProperlyRemoveComponent() {
        val button = createButton() as InternalComponent
        val handle = target.addComponent(button)
        assertThat(target.flattenedTree).hasSize(2)

        handle.detach()

        assertThat(target.flattenedTree).hasSize(1) // root is always there
    }

    // TODO: wtf is the problem with this?
    @Ignore
    @Test(expected = IllegalArgumentException::class)
    fun shouldNotLetToAddAComponentWhichIsBiggerThanTheContainer() {
        target.addComponent(PanelBuilder.newBuilder()
                .withSize(Size.create(999, 999))
                .build())
    }

    @Test
    fun shouldProperlyHandleMouseOverWhenActive() {
        target.activate()

        val button = createButton()
        target.addComponent(button)

        val componentHovered = AtomicBoolean(false)
        button.handleMouseEvents(MOUSE_ENTERED) { _, _ ->
            componentHovered.set(true)
            Pass
        }

        target.dispatch(MouseEvent(MOUSE_MOVED, 1, BUTTON_POSITION))

        assertThat(componentHovered.get()).isTrue()
    }

    @Test
    fun shouldProperlyHandleMouseOverWhenHoveredTwice() {
        target.activate()

        val button = createButton()
        target.addComponent(button)

        target.dispatch(MouseEvent(MOUSE_MOVED, 1, BUTTON_POSITION))

        val componentHovered = AtomicBoolean(false)
        button.handleMouseEvents(MOUSE_ENTERED) { _, _ ->
            componentHovered.set(true)
            Pass
        }

        target.dispatch(MouseEvent(MOUSE_MOVED, 1, BUTTON_POSITION.withRelativeX(1)))

        assertThat(componentHovered.get()).isFalse()
    }

    @Test
    fun shouldProperlyHandleMousePressedWhenActive() {
        target.activate()

        val button = createButton()
        target.addComponent(button)

        val pressed = AtomicBoolean(false)
        button.handleMouseEvents(MOUSE_PRESSED) { _, _ ->
            pressed.set(true)
            Pass
        }

        target.dispatch(MouseEvent(MOUSE_PRESSED, 1, BUTTON_POSITION))

        assertThat(pressed.get()).isTrue()
    }

    @Test
    fun shouldProperlyHandleMouseReleasedWhenActive() {
        target.activate()

        val button = createButton()
        target.addComponent(button)

        val released = AtomicBoolean(false)
        button.handleMouseEvents(MOUSE_RELEASED) { _, _ ->
            released.set(true)
            Pass
        }

        target.dispatch(MouseEvent(MOUSE_RELEASED, 1, BUTTON_POSITION))

        assertThat(released.get()).isTrue()
    }

    @Test
    fun shouldNotHandleEventsWhenInactive() {
        target.deactivate()

        val button = createButton()
        target.addComponent(button)

        val events = mutableListOf<Boolean>()
        button.handleMouseEvents(MOUSE_ENTERED) { _, _ ->
            events.add(true)
            Pass
        }
        button.handleMouseEvents(MOUSE_PRESSED) { _, _ ->
            events.add(true)
            Pass
        }
        button.handleMouseEvents(MOUSE_RELEASED) { _, _ ->
            events.add(true)
            Pass
        }

        target.dispatch(MouseEvent(MOUSE_MOVED, 1, BUTTON_POSITION))
        target.dispatch(MouseEvent(MOUSE_PRESSED, 1, BUTTON_POSITION))
        target.dispatch(MouseEvent(MOUSE_RELEASED, 1, BUTTON_POSITION))

        assertThat(events).isEmpty()
    }

    @Test
    fun shouldProperlyFocusNextWhenTabPressed() {
        target.activate()

        val button = createButton()
        target.addComponent(button)

        assertThat(button.currentStyle).isNotEqualTo(FOCUSED_STYLE)

        target.dispatch(TAB)

        assertThat(button.currentStyle).isEqualTo(FOCUSED_STYLE)
    }

    @Test
    fun shouldProperlyFocusPrevWhenShiftTabPressed() {
        target.activate()

        val button = createButton()
        target.addComponent(button)
        val other = ButtonBuilder.newBuilder()
                .withText(BUTTON_TEXT)
                .withPosition(Position.create(0, 1)
                        .relativeToBottomOf(button))
                .build()
        target.addComponent(other)

        target.dispatch(TAB)
        target.dispatch(TAB)

        assertThat(button.currentStyle).isEqualTo(DEFAULT_STYLE)

        target.dispatch(REVERSE_TAB)

        assertThat(button.currentStyle).isEqualTo(FOCUSED_STYLE)
    }

    @Test
    fun shouldProperlyHandleSpacePressedOnFocusedWhenActive() {
        target.activate()

        val button = createButton()
        target.addComponent(button)

        val activated = AtomicBoolean(false)
        button.handleComponentEvents(ComponentEventType.ACTIVATED) {
            activated.set(true)
            Pass
        }

        target.dispatch(TAB)
        target.dispatch(SPACE)

        assertThat(activated.get()).isTrue()
    }

    @Test
    fun When_deactivated_Then_focused_component_stays_the_same() {
        target.activate()

        val button = createButton() as InternalComponent
        target.addComponent(button)

        target.focus(button)

        target.deactivate()

        assertThat(target.focusedComponent).isEqualTo(button)
    }

    private fun createButton() = ButtonBuilder.newBuilder()
            .withPosition(BUTTON_POSITION)
            .withText(BUTTON_TEXT)
            .withComponentStyleSet(buildStyles())
            .build()

    companion object {
        const val BUTTON_TEXT = "TEXT"
        val TILESET = CP437TilesetResources.rexPaint16x16()
        val SIZE = Size.create(30, 20)
        val BUTTON_POSITION = Position.create(6, 7)
        val DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .withBackgroundColor(ANSITileColor.BLUE)
                .withForegroundColor(ANSITileColor.RED)
                .build()
        private val ACTIVE_STYLE = StyleSetBuilder.newBuilder()
                .withBackgroundColor(ANSITileColor.GREEN)
                .withForegroundColor(ANSITileColor.YELLOW)
                .build()
        private val DISABLED_STYLE = StyleSetBuilder.newBuilder()
                .withBackgroundColor(ANSITileColor.MAGENTA)
                .withForegroundColor(ANSITileColor.BLUE)
                .build()
        val FOCUSED_STYLE = StyleSetBuilder.newBuilder()
                .withBackgroundColor(ANSITileColor.YELLOW)
                .withForegroundColor(ANSITileColor.CYAN)
                .build()
        private val MOUSE_OVER_STYLE = StyleSetBuilder.newBuilder()
                .withBackgroundColor(ANSITileColor.RED)
                .withForegroundColor(ANSITileColor.CYAN)
                .build()

        fun buildStyles() = ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(DEFAULT_STYLE)
                .withActiveStyle(ACTIVE_STYLE)
                .withDisabledStyle(DISABLED_STYLE)
                .withFocusedStyle(FOCUSED_STYLE)
                .withMouseOverStyle(MOUSE_OVER_STYLE)
                .build()

        val SPACE = KeyboardEvent(
                type = KeyboardEventType.KEY_PRESSED,
                code = KeyCode.SPACE,
                key = " ")

        val TAB = KeyboardEvent(
                type = KeyboardEventType.KEY_PRESSED,
                key = "\t",
                code = KeyCode.TAB)

        val REVERSE_TAB = KeyboardEvent(
                type = KeyboardEventType.KEY_PRESSED,
                key = "\t",
                code = KeyCode.TAB,
                shiftDown = true)
    }
}
