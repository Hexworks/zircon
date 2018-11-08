package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.component.data.ComponentState.DEFAULT
import org.hexworks.zircon.api.component.data.ComponentState.FOCUSED
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.input.InputType.*
import org.hexworks.zircon.api.input.KeyStroke
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.component.renderer.DefaultTextAreaRenderer
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.platform.util.SystemUtils
import org.junit.Before
import org.junit.Test

@Suppress("UNCHECKED_CAST")
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
        assertThat(target.currentTileset().id)
                .isEqualTo(TILESET_REX_PAINT_20X20.id)
    }

    @Test
    fun shouldAcceptFocus() {
        assertThat(target.acceptsFocus()).isTrue()
    }

    @Test
    fun shouldProperlyMoveCursorRightWhenArrowRightPressed() {
        target = initializeMultiLineTextArea()

        target.keyStroked(KeyStroke(type = ArrowRight))

        assertThat(target.textBuffer().cursor.position).isEqualTo(Position.create(1, 0))
    }

    @Test
    fun shouldProperlyMoveCursorLeftWhenArrowLeftPressed() {
        target = initializeMultiLineTextArea()

        target.keyStroked(KeyStroke(type = ArrowRight))
        target.keyStroked(KeyStroke(type = ArrowRight))
        target.keyStroked(KeyStroke(type = ArrowLeft))

        assertThat(target.textBuffer().cursor.position).isEqualTo(Position.create(1, 0))
    }

    @Test
    fun shouldProperlyMoveCursorDownWhenArrowDownPressed() {
        target = initializeMultiLineTextArea()

        target.keyStroked(KeyStroke(type = ArrowDown))

        assertThat(target.textBuffer().cursor.position).isEqualTo(Position.create(0, 1))
    }

    @Test
    fun shouldProperlyMoveCursorUpWhenArrowUpPressed() {
        target = initializeMultiLineTextArea()

        target.keyStroked(KeyStroke(type = ArrowDown))
        target.keyStroked(KeyStroke(type = ArrowDown))
        target.keyStroked(KeyStroke(type = ArrowUp))

        assertThat(target.textBuffer().cursor.position).isEqualTo(Position.create(0, 1))
    }

    @Test
    fun shouldProperlyDeleteWhenDeleteIsPressed() {
        target.keyStroked(KeyStroke(type = Delete))

        assertThat(target.text).isEqualTo("ext")
    }

    @Test
    fun shouldProperlyDeleteWhenBackspaceIsPressed() {
        target.keyStroked(KeyStroke(type = ArrowRight))
        target.keyStroked(KeyStroke(type = ArrowRight))
        target.keyStroked(KeyStroke(type = Backspace))

        assertThat(target.text).isEqualTo("txt")
    }

    @Test
    fun shouldProperlyAddLineBreakWhenEnterPressedAt0x0Position() {
        target.keyStroked(KeyStroke(type = Enter))

        assertThat(target.text).isEqualTo("${SEP}text")
    }

    @Test
    fun shouldProperlyAddLineBreakWhenEnterPressedAt1x0Position() {
        target.keyStroked(KeyStroke(type = ArrowRight))
        target.keyStroked(KeyStroke(type = Enter))

        assertThat(target.text).isEqualTo("t${SEP}ext")
    }

    @Test
    fun shouldProperlyInsertCharacter() {
        target.keyStroked(KeyStroke(character = 'x'))

        assertThat(target.text).isEqualTo("xtext")
    }

    @Test
    fun shouldProperlyGiveFocus() {
        target.applyColorTheme(DEFAULT_THEME)
        val pos = Position.create(2, 3)
        val tile = Tile.createCharacterTile('x', StyleSet.defaultStyle())
        target.setTileAt(pos, tile)
        var cursorVisible = false
        Zircon.eventBus.subscribe<ZirconEvent.RequestCursorAt>(ZirconScope) {
            cursorVisible = true
        }

        target.giveFocus()

        assertThat(target.componentStyleSet.currentState())
                .isEqualTo(FOCUSED)
        assertThat(target.getTileAt(pos)).isNotEqualTo(tile)
        assertThat(cursorVisible).isTrue()
    }

    @Test
    fun shouldProperlyTakeFocus() {
        var cursorHidden = false
        Zircon.eventBus.subscribe<ZirconEvent.HideCursor>(ZirconScope) {
            cursorHidden = true
        }
        target.takeFocus()

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
        val SEP = SystemUtils.getLineSeparator()
        const val TEXT = "text"
        const val UPDATE_TEXT = 'U'
        val MULTI_LINE_TEXT = "text${SEP}text$SEP"
    }
}
