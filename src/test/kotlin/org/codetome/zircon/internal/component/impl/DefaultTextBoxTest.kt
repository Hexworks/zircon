package org.codetome.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.component.ComponentState
import org.codetome.zircon.api.component.builder.TextBoxBuilder
import org.codetome.zircon.api.input.InputType
import org.codetome.zircon.api.input.KeyStroke
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.api.resource.ColorThemeResource
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import org.junit.Before
import org.junit.Test

class DefaultTextBoxTest {

    lateinit var target: DefaultTextBox

    @Before
    fun setUp() {
        target = TextBoxBuilder.newBuilder()
                .componentStyles(COMPONENT_STYLES)
                .size(SIZE)
                .font(FONT)
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
                .isEqualTo(FONT.getId())
    }

    @Test
    fun shouldProperlyHandleRightArrowWhenFocused() {
        target.giveFocus()

        EventBus.emit(EventType.KeyPressed, KeyStroke(type = InputType.ArrowRight))

        assertThat(target.getCursorPosition()).isEqualTo(Position.DEFAULT_POSITION.withRelativeColumn(1))
    }

    @Test
    fun shouldProperlyHandleLeftArrowWhenFocusedAndMustScrollToEndOfPrevLine() {
        target.setText("Foo${System.lineSeparator()}bar")
        target.giveFocus()

        target.putCursorAt(Position.of(0, 1))
        EventBus.emit(EventType.KeyPressed, KeyStroke(type = InputType.ArrowLeft))

        assertThat(target.getCursorPosition()).isEqualTo(Position.of(3, 0))
    }

    @Test
    fun shouldProperlyHandleLeftArrowWhenFocusedAndCanMoveLeftInLine() {
        target.giveFocus()

        target.putCursorAt(Position.of(1, 0))
        EventBus.emit(EventType.KeyPressed, KeyStroke(type = InputType.ArrowLeft))

        assertThat(target.getCursorPosition()).isEqualTo(Position.DEFAULT_POSITION)
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

    companion object {
        val THEME = ColorThemeResource.ADRIFT_IN_DREAMS.getTheme()
        val TEXT = "text"
        val FONT = CP437TilesetResource.WANDERLUST_16X16.toFont()
        val SIZE = Size.of(10, 6)
        val POSITION = Position.of(4, 5)
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
        val COMPONENT_STYLES = ComponentStylesBuilder.newBuilder()
                .defaultStyle(DEFAULT_STYLE)
                .build()
    }
}