package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.component.TextAreaBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.ComponentState
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.input.KeyStroke
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.event.ZirconEvent
import org.junit.Before
import org.junit.Test

class DefaultTextAreaTest {

    lateinit var target: DefaultTextArea
    lateinit var tileset: TilesetResource

    @Before
    fun setUp() {
        tileset = DefaultLabelTest.FONT
        target = TextAreaBuilder.newBuilder()
                .componentStyleSet(COMPONENT_STYLES)
                .size(SIZE)
                .tileset(tileset)
                .position(POSITION)
                .text(TEXT)
                .build() as DefaultTextArea
    }

    @Test
    fun shouldProperlyReturnText() {
        assertThat(target.getText()).isEqualTo(TEXT)
    }

    @Test
    fun shouldUseProperFont() {
        assertThat(target.tileset().id)
                .isEqualTo(tileset.id)
    }

    @Test
    fun shouldProperlyHandleRightArrowWhenFocused() {
        target.giveFocus()

        EventBus.broadcast(ZirconEvent.KeyPressed(KeyStroke(type = InputType.ArrowRight)))

        assertThat(target.cursorPosition()).isEqualTo(Position.defaultPosition().withRelativeX(1))
    }

    @Test
    fun shouldProperlyHandleLeftArrowWhenFocusedAndMustScrollToEndOfPrevLine() {
        target.setText("Foo${System.lineSeparator()}bar")
        target.giveFocus()

        target.putCursorAt(Position.create(0, 1))
        EventBus.broadcast(ZirconEvent.KeyPressed(KeyStroke(type = InputType.ArrowLeft)))

        assertThat(target.cursorPosition()).isEqualTo(Position.create(3, 0))
    }

    @Test
    fun shouldProperlyHandleLeftArrowWhenFocusedAndCanMoveLeftInLine() {
        target.giveFocus()

        target.putCursorAt(Position.create(1, 0))
        EventBus.broadcast(ZirconEvent.KeyPressed(KeyStroke(type = InputType.ArrowLeft)))

        assertThat(target.cursorPosition()).isEqualTo(Position.defaultPosition())
    }

    @Test
    fun shouldProperlyTakeFocus() {
        target.giveFocus()

        target.takeFocus()

        // TODO: assert
    }

    @Test
    fun shouldProperlyApplyTheme() {
        target.applyColorTheme(THEME)
        val styles = target.getComponentStyles()
        assertThat(styles.getStyleFor(ComponentState.DEFAULT))
                .isEqualTo(DEFAULT_STYLE)
        assertThat(styles.getStyleFor(ComponentState.MOUSE_OVER))
                .isEqualTo(DEFAULT_STYLE)
        assertThat(styles.getStyleFor(ComponentState.FOCUSED))
                .isEqualTo(FOCUSED_STYLE)
        assertThat(styles.getStyleFor(ComponentState.ACTIVE))
                .isEqualTo(DEFAULT_STYLE)
        assertThat(styles.getStyleFor(ComponentState.DISABLED))
                .isEqualTo(DISABLED_STYLE)
    }

    @Test
    fun shouldRefreshDrawSurfaceIfSetText() {
        target.setText(UPDATE_TEXT.toString())
        val character = target.getDrawSurface().getTileAt(Position.defaultPosition())
        assertThat(character.get().asCharacterTile().get().character)
                .isEqualTo(UPDATE_TEXT)
    }

    companion object {
        val THEME = ColorThemeResource.ADRIFT_IN_DREAMS.getTheme()
        val TEXT = "text"
        val UPDATE_TEXT = 'U'
        val FONT = BuiltInCP437TilesetResource.WANDERLUST_16X16
        val SIZE = Size.create(10, 6)
        val POSITION = Position.create(4, 5)
        val DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.secondaryBackgroundColor())
                .backgroundColor(THEME.secondaryForegroundColor())
                .build()
        val FOCUSED_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.primaryBackgroundColor())
                .backgroundColor(THEME.primaryForegroundColor())
                .build()
        val DISABLED_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.secondaryForegroundColor())
                .backgroundColor(THEME.secondaryBackgroundColor())
                .build()
        val COMPONENT_STYLES = ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(DEFAULT_STYLE)
                .build()
    }
}
