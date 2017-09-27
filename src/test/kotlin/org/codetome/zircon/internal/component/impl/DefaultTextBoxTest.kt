package org.codetome.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.component.ComponentState
import org.codetome.zircon.api.component.builder.TextBoxBuilder
import org.codetome.zircon.api.resource.ColorThemeResource
import org.junit.Before
import org.junit.Test

class DefaultTextBoxTest {

    lateinit var target: DefaultTextBox

    @Before
    fun setUp() {
        target = TextBoxBuilder.newBuilder()
                .componentStyles(COMPONENT_STYLES)
                .position(POSITION)
                .text(TEXT)
                .build() as DefaultTextBox
    }

    @Test
    fun shouldProperlyReturnText() {
        assertThat(target.getText()).isEqualTo(TEXT)
    }

    @Test
    fun shouldProperlyApplyTheme() {
        target.applyTheme(THEME)
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
                .isEqualTo(DEFAULT_STYLE)
    }

    companion object {
        val THEME = ColorThemeResource.ADRIFT_IN_DREAMS.getTheme()
        val TEXT = "Button text"
        val POSITION = Position.of(4, 5)
        val DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.getDarkBackgroundColor())
                .backgroundColor(THEME.getDarkForegroundColor())
                .build()
        val FOCUSED_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.getBrightBackgroundColor())
                .backgroundColor(THEME.getBrightForegroundColor())
                .build()
        val COMPONENT_STYLES = ComponentStylesBuilder.newBuilder()
                .defaultStyle(DEFAULT_STYLE)
                .build()
    }
}