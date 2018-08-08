package org.codetome.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.builder.component.ComponentStyleSetBuilder
import org.codetome.zircon.api.builder.component.HeaderBuilder
import org.codetome.zircon.api.builder.graphics.StyleSetBuilder
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.component.ComponentState
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.api.resource.ColorThemeResource
import org.codetome.zircon.api.resource.TilesetResource
import org.junit.Before
import org.junit.Test

class DefaultHeaderTest {

    lateinit var target: DefaultHeader
    lateinit var tileset: TilesetResource<out Tile>

    @Before
    fun setUp() {
        tileset = FONT
        target = HeaderBuilder.newBuilder()
                .componentStyles(COMPONENT_STYLES)
                .position(POSITION)
                .tileset(tileset)
                .text(TEXT)
                .build() as DefaultHeader
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
    fun shouldProperlyApplyTheme() {
        target.applyColorTheme(THEME)
        val styles = target.getComponentStyles()
        assertThat(styles.getStyleFor(ComponentState.DEFAULT))
                .isEqualTo(DEFAULT_STYLE)
        assertThat(styles.getStyleFor(ComponentState.MOUSE_OVER))
                .isEqualTo(DEFAULT_STYLE)
        assertThat(styles.getStyleFor(ComponentState.FOCUSED))
                .isEqualTo(DEFAULT_STYLE)
        assertThat(styles.getStyleFor(ComponentState.ACTIVE))
                .isEqualTo(DEFAULT_STYLE)
        assertThat(styles.getStyleFor(ComponentState.DISABLED))
                .isEqualTo(DEFAULT_STYLE)
    }

    @Test
    fun shouldNotAcceptFocus() {
        assertThat(target.acceptsFocus()).isFalse()
    }

    @Test
    fun shouldNotAcceptGivenFocus() {
        assertThat(target.giveFocus()).isFalse()
    }

    companion object {
        val THEME = ColorThemeResource.ADRIFT_IN_DREAMS.getTheme()
        val TEXT = "Button text"
        val FONT = CP437TilesetResource.WANDERLUST_16X16
        val POSITION = Position.create(4, 5)
        val DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.getBrightForegroundColor())
                .backgroundColor(TextColor.transparent())
                .build()
        val COMPONENT_STYLES = ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(DEFAULT_STYLE)
                .build()
    }
}
