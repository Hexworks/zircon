package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.ApplicationStub
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.component.buildButton
import org.hexworks.zircon.api.builder.component.buildPanel
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.data.position
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.color.ANSITileColor.*
import org.hexworks.zircon.api.component.builder.base.withPreferredSize
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.api.uievent.MouseEventType.*
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.RootContainerRenderer
import org.junit.Before
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.contracts.ExperimentalContracts

@Suppress("TestFunctionName")
@ExperimentalContracts
class DefaultComponentContainerTest {

    lateinit var target: DefaultComponentContainer

    private lateinit var applicationStub: ApplicationStub

    @Before
    fun setUp() {
        applicationStub = ApplicationStub()
        target = DefaultComponentContainer(
            DefaultRootContainer(
                metadata = ComponentMetadata(
                    relativePosition = Position.defaultPosition(),
                    size = SIZE,
                    tilesetProperty = TILESET.toProperty(),
                    componentStyleSetProperty = buildStyles().toProperty()
                ),
                renderingStrategy = DefaultComponentRenderingStrategy(
                    decorationRenderers = listOf(),
                    componentRenderer = RootContainerRenderer()
                ),
                application = applicationStub
            )
        )
    }

    @Test
    fun shouldProperlyRemoveComponent() {
        val button = createButton() as InternalComponent
        val handle = target.addComponent(button)
        assertThat(target.flattenedTree).hasSize(2)

        handle.detach()

        assertThat(target.flattenedTree).hasSize(1) // root is always there
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotLetToAddAComponentWhichIsBiggerThanTheContainer() {
        target.addComponent(
            buildPanel {
                withPreferredSize {
                    width = 999
                    height = 999
                }
            }
        )
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
        val other = buildButton {
            +BUTTON_TEXT
            position = position {
                x = 0
                y = 1
            }.relativeToBottomOf(button)
        }
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

    private fun createButton() = buildButton {
        +BUTTON_TEXT
        position = BUTTON_POSITION
        componentStyleSet = buildStyles()
    }

    companion object {
        const val BUTTON_TEXT = "TEXT"
        val TILESET = CP437TilesetResources.rexPaint16x16()
        val SIZE = Size.create(30, 20)
        val BUTTON_POSITION = Position.create(6, 7)
        val DEFAULT_STYLE = styleSet {
            backgroundColor = BLUE
            foregroundColor = RED
        }
        private val ACTIVE_STYLE = styleSet {
            backgroundColor = GREEN
            foregroundColor = YELLOW
        }
        private val DISABLED_STYLE = styleSet {
            backgroundColor = MAGENTA
            foregroundColor = BLUE
        }
        val FOCUSED_STYLE = styleSet {
            backgroundColor = YELLOW
            foregroundColor = CYAN
        }
        private val MOUSE_OVER_STYLE = styleSet {
            backgroundColor = RED
            foregroundColor = CYAN
        }

        fun buildStyles() = componentStyleSet {
            defaultStyle = DEFAULT_STYLE
            activeStyle = ACTIVE_STYLE
            disabledStyle = DISABLED_STYLE
            focusedStyle = FOCUSED_STYLE
            highlightedStyle = MOUSE_OVER_STYLE
        }

        val SPACE = KeyboardEvent(
            type = KeyboardEventType.KEY_PRESSED,
            code = KeyCode.SPACE,
            key = " "
        )

        val TAB = KeyboardEvent(
            type = KeyboardEventType.KEY_PRESSED,
            key = "\t",
            code = KeyCode.TAB
        )

        val REVERSE_TAB = KeyboardEvent(
            type = KeyboardEventType.KEY_PRESSED,
            key = "\t",
            code = KeyCode.TAB,
            shiftDown = true
        )
    }
}
