package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.cobalt.events.api.subscribe
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.component.data.ComponentState.DEFAULT
import org.hexworks.zircon.api.component.data.ComponentState.FOCUSED
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.UIEventPhase.TARGET
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.component.renderer.DefaultTextAreaRenderer
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.platform.util.SystemUtils
import org.junit.Before
import org.junit.Test

@Suppress("UNCHECKED_CAST", "UsePropertyAccessSyntax", "unused")
class DefaultTextAreaTest : ComponentImplementationTest<DefaultTextArea>() {

    override lateinit var target: DefaultTextArea

    override val expectedComponentStyles: ComponentStyleSet
        get() = ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(DEFAULT_THEME.secondaryBackgroundColor)
                        .withBackgroundColor(DEFAULT_THEME.secondaryForegroundColor)
                        .build())
                .withDisabledStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(DEFAULT_THEME.secondaryForegroundColor)
                        .withBackgroundColor(TileColor.transparent())
                        .build())
                .withFocusedStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(DEFAULT_THEME.primaryBackgroundColor)
                        .withBackgroundColor(DEFAULT_THEME.primaryForegroundColor)
                        .build())
                .build()

    @Before
    override fun setUp() {
        rendererStub = ComponentRendererStub(DefaultTextAreaRenderer())
        componentStub = ComponentStub(Position.create(1, 1), Size.create(2, 2))
        target = DefaultTextArea(
                componentMetadata = COMMON_COMPONENT_METADATA,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        componentRenderer = rendererStub as ComponentRenderer<TextArea>),
                initialText = TEXT)
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
                        key = "${KeyCode.RIGHT.toChar()}",
                        code = KeyCode.RIGHT),
                phase = TARGET)

        assertThat(target.textBuffer().cursor.position).isEqualTo(Position.create(1, 0))
    }

    @Test
    fun shouldProperlyMoveCursorLeftWhenArrowLeftPressed() {
        target = initializeMultiLineTextArea()

        target.keyPressed(
                event = KeyboardEvent(
                        type = KeyboardEventType.KEY_PRESSED,
                        key = "${KeyCode.RIGHT.toChar()}",
                        code = KeyCode.RIGHT),
                phase = TARGET)
        target.keyPressed(
                event = KeyboardEvent(
                        type = KeyboardEventType.KEY_PRESSED,
                        key = "${KeyCode.RIGHT.toChar()}",
                        code = KeyCode.RIGHT),
                phase = TARGET)
        target.keyPressed(
                event = KeyboardEvent(
                        type = KeyboardEventType.KEY_PRESSED,
                        key = "${KeyCode.LEFT.toChar()}",
                        code = KeyCode.LEFT),
                phase = TARGET)

        assertThat(target.textBuffer().cursor.position).isEqualTo(Position.create(1, 0))
    }

    @Test
    fun shouldProperlyMoveCursorDownWhenArrowDownPressed() {
        target = initializeMultiLineTextArea()

        target.keyPressed(
                event = KeyboardEvent(
                        type = KeyboardEventType.KEY_PRESSED,
                        key = "${KeyCode.DOWN.toChar()}",
                        code = KeyCode.DOWN),
                phase = TARGET)

        assertThat(target.textBuffer().cursor.position).isEqualTo(Position.create(0, 1))
    }

    @Test
    fun shouldProperlyMoveCursorUpWhenArrowUpPressed() {
        target = initializeMultiLineTextArea()

        target.keyPressed(DOWN, TARGET)
        target.keyPressed(DOWN, TARGET)
        target.keyPressed(UP, TARGET)

        assertThat(target.textBuffer().cursor.position).isEqualTo(Position.create(0, 1))
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
        var cursorVisible = false
        Zircon.eventBus.subscribe<ZirconEvent.RequestCursorAt>(ZirconScope) {
            cursorVisible = true
        }

        target.focusGiven()

        assertThat(target.componentStyleSet.currentState())
                .isEqualTo(FOCUSED)
        assertThat(cursorVisible).isTrue()
    }

    @Test
    fun shouldProperlyTakeFocus() {
        var cursorHidden = false
        Zircon.eventBus.subscribe<ZirconEvent.HideCursor>(ZirconScope) {
            cursorHidden = true
        }
        target.focusTaken()

        assertThat(target.componentStyleSet.currentState())
                .isEqualTo(DEFAULT)
        assertThat(cursorHidden).isTrue()
    }

    @Test
    fun shouldRefreshDrawSurfaceIfSetText() {
        target.text = UPDATE_TEXT.toString()
        val character = target.graphics.getTileAt(Position.defaultPosition())
        assertThat(character.get().asCharacterTile().get().character)
                .isEqualTo(UPDATE_TEXT)
    }

    private fun initializeMultiLineTextArea(): DefaultTextArea {
        return DefaultTextArea(
                componentMetadata = COMMON_COMPONENT_METADATA,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        componentRenderer = rendererStub as ComponentRenderer<TextArea>),
                initialText = MULTI_LINE_TEXT)
    }

    companion object {
        val SEP = SystemUtils.getLineSeparator()!!
        const val TEXT = "text"
        const val UPDATE_TEXT = 'U'
        val MULTI_LINE_TEXT = "text${SEP}text$SEP"

        val DOWN = KeyboardEvent(
                type = KeyboardEventType.KEY_PRESSED,
                key = "${KeyCode.DOWN.toChar()}",
                code = KeyCode.DOWN)

        val UP = DOWN.copy(key = " ", code = KeyCode.UP)
        val LEFT = DOWN.copy(key = " ", code = KeyCode.LEFT)
        val RIGHT = DOWN.copy(key = " ", code = KeyCode.RIGHT)
        val ENTER = DOWN.copy(key = " ", code = KeyCode.ENTER)
        val BACKSPACE = DOWN.copy(key = " ", code = KeyCode.BACKSPACE)
        val DELETE = DOWN.copy(key = " ", code = KeyCode.DELETE)
        val X = DOWN.copy(key = "x", code = KeyCode.KEY_X)
    }
}
