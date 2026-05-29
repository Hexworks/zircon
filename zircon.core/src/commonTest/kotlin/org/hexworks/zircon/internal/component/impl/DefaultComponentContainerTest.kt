package org.hexworks.zircon.internal.component.impl

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.ApplicationStub
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.component.buildButton
import org.hexworks.zircon.api.builder.component.buildPanel
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.data.position
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette
import org.hexworks.zircon.api.component.builder.base.withPreferredSize
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.api.uievent.MouseEventType.*
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.RootContainerRenderer
import kotlin.contracts.ExperimentalContracts
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

@Suppress("TestFunctionName")
@ExperimentalContracts
class DefaultComponentContainerTest {

    lateinit var target: DefaultComponentContainer

    private lateinit var applicationStub: ApplicationStub

    @BeforeTest
    fun setUp() {
        applicationStub = ApplicationStub()
        target = DefaultComponentContainer(
            DefaultRootContainer(
                metadata = ComponentMetadata(
                    position = Position.ZERO,
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
        target.flattenedTree shouldHaveSize 2

        handle.detach()

        target.flattenedTree shouldHaveSize 1 // root is always there
    }

    @Test
    fun shouldNotLetToAddAComponentWhichIsBiggerThanTheContainer() {
        assertFailsWith<IllegalArgumentException> {
            target.addComponent(
                buildPanel {
                    withPreferredSize {
                        width = 999
                        height = 999
                    }
                }
            )
        }
    }

    @Test
    fun shouldProperlyHandleMouseOverWhenActive() {
        target.activate()

        val button = createButton()
        target.addComponent(button)

        var componentHovered = false
        button.handleMouseEvents(MOUSE_ENTERED) { _, _ ->
            componentHovered = true
            Pass
        }

        target.dispatch(MouseEvent(MOUSE_MOVED, 1, BUTTON_POSITION))

        componentHovered shouldBe true
    }

    @Test
    fun shouldProperlyHandleMouseOverWhenHoveredTwice() {
        target.activate()

        val button = createButton()
        target.addComponent(button)

        target.dispatch(MouseEvent(MOUSE_MOVED, 1, BUTTON_POSITION))

        var componentHovered = false
        button.handleMouseEvents(MOUSE_ENTERED) { _, _ ->
            componentHovered = true
            Pass
        }

        target.dispatch(MouseEvent(MOUSE_MOVED, 1, BUTTON_POSITION.withRelativeX(1)))

        componentHovered shouldBe false
    }

    @Test
    fun shouldProperlyHandleMousePressedWhenActive() {
        target.activate()

        val button = createButton()
        target.addComponent(button)

        var pressed = false
        button.handleMouseEvents(MOUSE_PRESSED) { _, _ ->
            pressed = true
            Pass
        }

        target.dispatch(MouseEvent(MOUSE_PRESSED, 1, BUTTON_POSITION))

        pressed shouldBe true
    }

    @Test
    fun shouldProperlyHandleMouseReleasedWhenActive() {
        target.activate()

        val button = createButton()
        target.addComponent(button)

        var released = false
        button.handleMouseEvents(MOUSE_RELEASED) { _, _ ->
            released = true
            Pass
        }

        target.dispatch(MouseEvent(MOUSE_RELEASED, 1, BUTTON_POSITION))

        released shouldBe true
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

        events.shouldBeEmpty()
    }

    @Test
    fun shouldProperlyFocusNextWhenTabPressed() {
        target.activate()

        val button = createButton()
        target.addComponent(button)

        button.currentStyle shouldNotBe FOCUSED_STYLE

        target.dispatch(TAB)

        button.currentStyle shouldBe FOCUSED_STYLE
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

        button.currentStyle shouldBe DEFAULT_STYLE

        target.dispatch(REVERSE_TAB)

        button.currentStyle shouldBe FOCUSED_STYLE
    }

    @Test
    fun shouldProperlyHandleSpacePressedOnFocusedWhenActive() {
        target.activate()

        val button = createButton()
        target.addComponent(button)

        var activated = false
        button.handleComponentEvents(ComponentEventType.ACTIVATED) {
            activated = true
            Pass
        }

        target.dispatch(TAB)
        target.dispatch(SPACE)

        activated shouldBe true
    }

    @Test
    fun When_deactivated_Then_focused_component_stays_the_same() {
        target.activate()

        val button = createButton() as InternalComponent
        target.addComponent(button)

        target.focus(button)

        target.deactivate()

        target.focusedComponent shouldBe button
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
            backgroundColor = DefaultAnsiPalette[ANSIColor.BLUE]
            foregroundColor = DefaultAnsiPalette[ANSIColor.RED]
        }
        private val ACTIVE_STYLE = styleSet {
            backgroundColor = DefaultAnsiPalette[ANSIColor.GREEN]
            foregroundColor = DefaultAnsiPalette[ANSIColor.YELLOW]
        }
        private val DISABLED_STYLE = styleSet {
            backgroundColor = DefaultAnsiPalette[ANSIColor.MAGENTA]
            foregroundColor = DefaultAnsiPalette[ANSIColor.BLUE]
        }
        val FOCUSED_STYLE = styleSet {
            backgroundColor = DefaultAnsiPalette[ANSIColor.YELLOW]
            foregroundColor = DefaultAnsiPalette[ANSIColor.CYAN]
        }
        private val MOUSE_OVER_STYLE = styleSet {
            backgroundColor = DefaultAnsiPalette[ANSIColor.RED]
            foregroundColor = DefaultAnsiPalette[ANSIColor.CYAN]
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
