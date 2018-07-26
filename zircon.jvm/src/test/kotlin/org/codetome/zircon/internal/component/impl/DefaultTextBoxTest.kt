package org.codetome.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.component.ComponentState
import org.codetome.zircon.api.builder.component.ComponentStyleSetBuilder
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.builder.graphics.StyleSetBuilder
import org.codetome.zircon.api.input.InputType
import org.codetome.zircon.api.input.KeyStroke
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.api.resource.ColorThemeResource
import org.codetome.zircon.api.builder.component.TextBoxBuilder
import org.codetome.zircon.internal.event.Event
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.font.impl.FontLoaderRegistry
import org.codetome.zircon.internal.font.impl.TestFontLoader
import org.junit.Before
import org.junit.Test

class DefaultTextBoxTest {

    lateinit var target: DefaultTextBox
    lateinit var font: Font

    @Before
    fun setUp() {
        FontLoaderRegistry.setFontLoader(TestFontLoader())
        font = DefaultLabelTest.FONT.toFont()
        target = TextBoxBuilder.newBuilder()
                .componentStyles(COMPONENT_STYLES)
                .size(SIZE)
                .font(font)
                .position(POSITION)
                .text(TEXT)
                .build() as DefaultTextBox
    }

    @Test
    fun shouldProperlyReturnText() {
        assertThat(target.getText()).isEqualTo(TEXT)
    }

    @Test
    fun shouldUseProperFont() {
        assertThat(target.getCurrentFont().getId())
                .isEqualTo(font.getId())
    }

    @Test
    fun shouldProperlyHandleRightArrowWhenFocused() {
        target.giveFocus()

        EventBus.broadcast(Event.KeyPressed(KeyStroke(type = InputType.ArrowRight)))

        assertThat(target.getCursorPosition()).isEqualTo(Position.defaultPosition().withRelativeX(1))
    }

    @Test
    fun shouldProperlyHandleLeftArrowWhenFocusedAndMustScrollToEndOfPrevLine() {
        target.setText("Foo${System.lineSeparator()}bar")
        target.giveFocus()

        target.putCursorAt(Position.create(0, 1))
        EventBus.broadcast(Event.KeyPressed(KeyStroke(type = InputType.ArrowLeft)))

        assertThat(target.getCursorPosition()).isEqualTo(Position.create(3, 0))
    }

    @Test
    fun shouldProperlyHandleLeftArrowWhenFocusedAndCanMoveLeftInLine() {
        target.giveFocus()

        target.putCursorAt(Position.create(1, 0))
        EventBus.broadcast(Event.KeyPressed(KeyStroke(type = InputType.ArrowLeft)))

        assertThat(target.getCursorPosition()).isEqualTo(Position.defaultPosition())
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
        val character = target.getDrawSurface().getCharacterAt(Position.defaultPosition())
        assertThat(character.get().getCharacter())
                .isEqualTo(UPDATE_TEXT)
    }

    companion object {
        val THEME = ColorThemeResource.ADRIFT_IN_DREAMS.getTheme()
        val TEXT = "text"
        val UPDATE_TEXT = 'U'
        val FONT = CP437TilesetResource.WANDERLUST_16X16
        val SIZE = Size.create(10, 6)
        val POSITION = Position.create(4, 5)
        val DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.getDarkBackgroundColor())
                .backgroundColor(THEME.getDarkForegroundColor())
                .build()
        val FOCUSED_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.getBrightBackgroundColor())
                .backgroundColor(THEME.getBrightForegroundColor())
                .build()
        val DISABLED_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.getDarkForegroundColor())
                .backgroundColor(THEME.getDarkBackgroundColor())
                .build()
        val COMPONENT_STYLES = ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(DEFAULT_STYLE)
                .build()
    }
}
