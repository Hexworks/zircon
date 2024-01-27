package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.color.TileColor.Companion.transparent
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.component.data.ComponentState.DEFAULT
import org.hexworks.zircon.api.component.data.ComponentState.FOCUSED
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.UIEventPhase.TARGET
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.DefaultTextAreaRenderer
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

// TODO: fixme
@Ignore
@Suppress("UNCHECKED_CAST", "unused")
class DefaultTextAreaTest : FocusableComponentImplementationTest<DefaultTextArea>() {

    override lateinit var target: DefaultTextArea
    override lateinit var drawWindow: DrawWindow

    override val expectedComponentStyles: ComponentStyleSet
        get() = componentStyleSet {
            defaultStyle = styleSet {
                foregroundColor = DEFAULT_THEME.secondaryBackgroundColor
                backgroundColor = DEFAULT_THEME.secondaryForegroundColor
            }
            disabledStyle = styleSet {
                foregroundColor = DEFAULT_THEME.secondaryForegroundColor
                backgroundColor = transparent()
            }
            focusedStyle = styleSet {
                foregroundColor = DEFAULT_THEME.primaryBackgroundColor
                backgroundColor = DEFAULT_THEME.primaryForegroundColor
            }
        }

    @Before
    override fun setUp() {
        rendererStub = ComponentRendererStub(DefaultTextAreaRenderer())
        componentStub = ComponentStub(Position.create(1, 1), Size.create(2, 2))
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
        assertThat(target.text).isEqualTo(TEXT)
    }

    @Test
    fun shouldUseProperFont() {
        assertThat(target.tileset.id)
            .isEqualTo(TILESET_REX_PAINT_20X20.id)
    }

    @Test
    fun shouldAcceptFocus() {
        assertThat(target.acceptsFocus()).isTrue()
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

        // TODO:
//        assertThat(target.textBuffer().cursor.position).isEqualTo(Position.create(1, 0))
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

        // TODO:
//        assertThat(target.textBuffer().cursor.position).isEqualTo(Position.create(1, 0))
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

        // TODO:
//        assertThat(target.textBuffer().cursor.position).isEqualTo(Position.create(0, 1))
    }

    @Test
    fun shouldProperlyMoveCursorUpWhenArrowUpPressed() {
        target = initializeMultiLineTextArea()

        target.keyPressed(DOWN, TARGET)
        target.keyPressed(DOWN, TARGET)
        target.keyPressed(UP, TARGET)

        // TODO:
//        assertThat(target.textBuffer().cursor.position).isEqualTo(Position.create(0, 1))
    }

    @Test
    fun shouldProperlyDeleteWhenDeleteIsPressed() {
        target.keyPressed(DELETE, TARGET)

        assertThat(target.text).isEqualTo("ext")
    }

    @Test
    fun shouldProperlyDeleteWhenBackspaceIsPressed() {
        target.keyPressed(RIGHT, TARGET)
        target.keyPressed(RIGHT, TARGET)
        target.keyPressed(BACKSPACE, TARGET)

        assertThat(target.text).isEqualTo("txt")
    }

    @Test
    fun shouldProperlyAddLineBreakWhenEnterPressedAt0x0Position() {
        target.keyPressed(ENTER, TARGET)

        assertThat(target.text).isEqualTo("${SEP}text")
    }

    @Test
    fun shouldProperlyAddLineBreakWhenEnterPressedAt1x0Position() {
        target.keyPressed(RIGHT, TARGET)
        target.keyPressed(ENTER, TARGET)

        assertThat(target.text).isEqualTo("t${SEP}ext")
    }

    @Test
    fun shouldProperlyInsertCharacter() {
        target.keyPressed(X, TARGET)

        assertThat(target.text).isEqualTo("xtext")
    }

    @Test
    fun shouldProperlyGiveFocus() {
        target.convertColorTheme(DEFAULT_THEME)
        target.focusGiven()

        assertThat(target.componentState)
            .isEqualTo(FOCUSED)
    }

    @Test
    fun shouldProperlyTakeFocus() {
        target.focusTaken()

        assertThat(target.componentState)
            .isEqualTo(DEFAULT)
    }

    @Test
    fun shouldRefreshDrawSurfaceIfSetText() {
        target.text = UPDATE_TEXT.toString()
        rendererStub.render(drawWindow, ComponentRenderContext(target))
        val character = drawWindow.getTileAtOrNull(Position.defaultPosition())

        assertThat(character?.asCharacterTileOrNull()?.character)
            .isEqualTo(UPDATE_TEXT)
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
