package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.component.ButtonBuilder
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.component.PanelBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.handleComponentEvents
import org.hexworks.zircon.api.extensions.handleMouseEvents
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType.*
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.internal.component.renderer.RootContainerRenderer
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
class DefaultComponentContainerTest {

    lateinit var target: DefaultComponentContainer

    @Before
    fun setUp() {
        target = DefaultComponentContainer(RootContainer(
                componentMetadata = ComponentMetadata(
                        position = Position.defaultPosition(),
                        size = SIZE,
                        tileset = TILESET,
                        componentStyleSet = buildStyles()),
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = listOf(),
                        componentRenderer = RootContainerRenderer())))
    }

    @Test
    fun shouldProperlyRemoveComponent() {
        val button = createButton()
        target.addComponent(button)
        assertThat(target.toFlattenedLayers()).hasSize(2)

        val result = target.removeComponent(button)

        assertThat(result).isTrue()
        assertThat(target.toFlattenedLayers()).hasSize(1) // default container
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

        assertThat(button.componentStyleSet.currentStyle()).isNotEqualTo(FOCUSED_STYLE)

        target.dispatch(TAB)

        assertThat(button.componentStyleSet.currentStyle()).isEqualTo(FOCUSED_STYLE)
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

        assertThat(button.componentStyleSet.currentStyle()).isEqualTo(DEFAULT_STYLE)

        target.dispatch(REVERSE_TAB)

        assertThat(button.componentStyleSet.currentStyle()).isEqualTo(FOCUSED_STYLE)
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
