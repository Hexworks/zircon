package org.hexworks.zircon.internal.component.impl

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.component.data.ComponentState.DEFAULT
import org.hexworks.zircon.api.component.data.ComponentState.FOCUSED
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.DrawWindow
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.UIEventPhase.TARGET
import org.hexworks.zircon.api.extensions.asCharacterTileOrNull
import org.hexworks.zircon.api.graphics.extensions.toDrawWindow
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.DefaultTextAreaRenderer
import kotlin.test.BeforeTest
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.component.data.ComponentState.HIGHLIGHTED
import kotlin.test.Test

@Suppress("UNCHECKED_CAST", "unused")
class DefaultTextAreaTest : FocusableComponentImplementationTest<DefaultTextArea>() {

    override lateinit var target: DefaultTextArea
    override lateinit var drawWindow: DrawWindow


    @BeforeTest
    override fun setUp() {
        rendererStub = ComponentRendererStub(DefaultTextAreaRenderer())
        componentStub = ComponentStub(Position(1, 1), Size(2, 2))
        drawWindow = tileGraphics {
            size = COMMON_COMPONENT_METADATA.size
        }.toDrawWindow()
        target = DefaultTextArea(
            componentMetadata = COMMON_COMPONENT_METADATA,
            renderingStrategy = DefaultComponentRenderingStrategy(
                componentRenderer = rendererStub as ComponentRenderer<TextArea>
            ),
            initialText = TEXT
        )
        rendererStub.render(drawWindow, ComponentRenderContext(target))
    }

    @Test
    fun shouldProperlyReturnText() {
        target.text shouldBe TEXT
    }

    @Test
    fun shouldUseProperFont() {
        target.tileset.id shouldBe TILESET_REX_PAINT_20X20.id
    }

    @Test
    fun shouldAcceptFocus() {
        target.acceptsFocus() shouldBe true
    }

    @Test
    fun shouldProperlyMoveCursorRightWhenArrowRightPressed() {
        target = initializeMultiLineTextArea()

        target.keyPressed(
            event = KeyboardEvent(
                type = KeyboardEventType.KEY_PRESSED,
                key = "${KeyCode.RIGHT.toCharOrNull()}",
                code = KeyCode.RIGHT
            ),
            phase = TARGET
        )

        target.editorState.cursor shouldBe Position(1, 0)
    }

    @Test
    fun shouldProperlyMoveCursorLeftWhenArrowLeftPressed() {
        target = initializeMultiLineTextArea()

        target.keyPressed(
            event = KeyboardEvent(
                type = KeyboardEventType.KEY_PRESSED,
                key = "${KeyCode.RIGHT.toCharOrNull()}",
                code = KeyCode.RIGHT
            ),
            phase = TARGET
        )
        target.keyPressed(
            event = KeyboardEvent(
                type = KeyboardEventType.KEY_PRESSED,
                key = "${KeyCode.RIGHT.toCharOrNull()}",
                code = KeyCode.RIGHT
            ),
            phase = TARGET
        )
        target.keyPressed(
            event = KeyboardEvent(
                type = KeyboardEventType.KEY_PRESSED,
                key = "${KeyCode.LEFT.toCharOrNull()}",
                code = KeyCode.LEFT
            ),
            phase = TARGET
        )

        target.editorState.cursor shouldBe Position(1, 0)
    }

    @Test
    fun shouldProperlyMoveCursorDownWhenArrowDownPressed() {
        target = initializeMultiLineTextArea()

        target.keyPressed(
            event = KeyboardEvent(
                type = KeyboardEventType.KEY_PRESSED,
                key = "${KeyCode.DOWN.toCharOrNull()}",
                code = KeyCode.DOWN
            ),
            phase = TARGET
        )

        target.editorState.cursor shouldBe Position(0, 1)
    }

    @Test
    fun shouldProperlyMoveCursorUpWhenArrowUpPressed() {
        target = initializeMultiLineTextArea()

        target.keyPressed(DOWN, TARGET)
        target.keyPressed(DOWN, TARGET)
        target.keyPressed(UP, TARGET)

        target.editorState.cursor shouldBe Position(0, 1)
    }

    @Test
    fun shouldProperlyDeleteWhenDeleteIsPressed() {
        target.keyPressed(DELETE, TARGET)

        target.text shouldBe "ext"
    }

    @Test
    fun shouldProperlyDeleteWhenBackspaceIsPressed() {
        target.keyPressed(RIGHT, TARGET)
        target.keyPressed(RIGHT, TARGET)
        target.keyPressed(BACKSPACE, TARGET)

        target.text shouldBe "txt"
    }

    @Test
    fun shouldProperlyAddLineBreakWhenEnterPressedAt0x0Position() {
        target.keyPressed(ENTER, TARGET)

        target.text shouldBe "${SEP}text"
    }

    @Test
    fun shouldProperlyAddLineBreakWhenEnterPressedAt1x0Position() {
        target.keyPressed(RIGHT, TARGET)
        target.keyPressed(ENTER, TARGET)

        target.text shouldBe "t${SEP}ext"
    }

    @Test
    fun shouldProperlyInsertCharacter() {
        target.keyPressed(X, TARGET)

        target.text shouldBe "xtext"
    }

    @Test
    fun shouldProperlyGiveFocus() {
        target.focusGiven()

        target.componentState shouldBe FOCUSED
    }

    @Test
    fun shouldProperlyTakeFocus() {
        target.focusTaken()

        target.componentState shouldBe DEFAULT
    }

    @Test
    fun shouldRefreshDrawSurfaceIfSetText() {
        target.text = UPDATE_TEXT.toString()
        rendererStub.render(drawWindow, ComponentRenderContext(target))
        val character = drawWindow.getTileAtOrNull(Position.ZERO)

        character?.asCharacterTileOrNull()?.character shouldBe UPDATE_TEXT
    }

    // A text area swallows the activation key (it types) and keeps the focus look on a
    // release: the generic state transitions below do not apply to it.

    @Test
    override fun When_a_focused_component_is_activated_Then_it_becomes_active() {
        target.focusGiven()
        target.activated() shouldBe Processed
        target.componentState shouldBe FOCUSED
    }

    @Test
    override fun When_a_highlighted_component_without_focus_is_activated_Then_it_becomes_active() {
        target.mouseEntered(MouseEvent(MouseEventType.MOUSE_ENTERED, 1, Position.ZERO), TARGET)
        target.activated() shouldBe Processed
        target.componentState shouldBe HIGHLIGHTED
    }

    @Test
    override fun When_a_highlighted_component_with_focus_is_activated_Then_it_becomes_active() {
        target.mouseEntered(MouseEvent(MouseEventType.MOUSE_ENTERED, 1, Position.ZERO), TARGET)
        target.focusGiven()
        target.activated() shouldBe Processed
        target.componentState shouldBe HIGHLIGHTED
    }

    @Test
    override fun When_the_mouse_is_released_on_a_focused_component_Then_it_becomes_highlighted() {
        target.focusGiven()
        target.mouseReleased(MouseEvent(MouseEventType.MOUSE_RELEASED, 1, Position.ZERO), TARGET)
        target.componentState shouldBe FOCUSED
    }

    @Test
    override fun When_the_mouse_is_released_on_an_active_component_Then_it_becomes_highlighted() {
        target.focusGiven()
        target.activated()
        target.mouseReleased(MouseEvent(MouseEventType.MOUSE_RELEASED, 1, Position.ZERO), TARGET)
        target.componentState shouldBe FOCUSED
    }

    private fun initializeMultiLineTextArea(): DefaultTextArea {
        return DefaultTextArea(
            componentMetadata = COMMON_COMPONENT_METADATA,
            renderingStrategy = DefaultComponentRenderingStrategy(
                componentRenderer = rendererStub as ComponentRenderer<TextArea>
            ),
            initialText = MULTI_LINE_TEXT
        )
    }

    companion object {
        val SEP = '\n'
        const val TEXT = "text"
        const val UPDATE_TEXT = 'U'
        val MULTI_LINE_TEXT = "text${SEP}text$SEP"

        val DOWN = KeyboardEvent(
            type = KeyboardEventType.KEY_PRESSED,
            key = "${KeyCode.DOWN.toCharOrNull()}",
            code = KeyCode.DOWN
        )

        val UP = DOWN.copy(key = " ", code = KeyCode.UP)
        val LEFT = DOWN.copy(key = " ", code = KeyCode.LEFT)
        val RIGHT = DOWN.copy(key = " ", code = KeyCode.RIGHT)
        val ENTER = DOWN.copy(key = " ", code = KeyCode.ENTER)
        val BACKSPACE = DOWN.copy(key = " ", code = KeyCode.BACKSPACE)
        val DELETE = DOWN.copy(key = " ", code = KeyCode.DELETE)
        val X = DOWN.copy(key = "x", code = KeyCode.KEY_X)
    }

}
