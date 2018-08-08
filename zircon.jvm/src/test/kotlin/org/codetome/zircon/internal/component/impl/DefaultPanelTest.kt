package org.codetome.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.builder.component.LabelBuilder
import org.codetome.zircon.api.builder.component.PanelBuilder
import org.codetome.zircon.api.builder.graphics.StyleSetBuilder
import org.codetome.zircon.api.builder.modifier.BorderBuilder
import org.codetome.zircon.api.component.ComponentState
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.graphics.BoxType
import org.codetome.zircon.api.modifier.BorderType
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.api.resource.ColorThemeResource
import org.codetome.zircon.api.resource.TilesetResource
import org.junit.Before
import org.junit.Test

class DefaultPanelTest {

    lateinit var target: DefaultPanel
    lateinit var tileset: TilesetResource<out Tile>

    @Before
    fun setUp() {
        tileset = DefaultLabelTest.FONT
        target = PanelBuilder.newBuilder()
                .wrapWithShadow()
                .boxType(BOX_TYPE)
                .title(TITLE)
                .tileset(tileset)
                .addBorder(BorderBuilder.newBuilder().borderType(BorderType.DASHED).build())
                .size(SIZE)
                .wrapWithBox()
                .position(POSITION)
                .build() as DefaultPanel
    }

    @Test
    fun shouldHaveProperTitle() {
        assertThat(target.getTitle()).isEqualTo(TITLE)
    }

    @Test
    fun shouldUseProperFont() {
        assertThat(target.tileset().id)
                .isEqualTo(tileset.id)
    }

    @Test
    fun shouldProperlySetPosition() {
        assertThat(target.getPosition()).isEqualTo(POSITION)
    }

    @Test
    fun shouldProperlySetSize() {
        assertThat(target.getBoundableSize()).isEqualTo(SIZE)
    }

    @Test
    fun shouldProperlyApplyTheme() {
        target.applyColorTheme(THEME)

        ComponentState.values().forEach {
            assertThat(target.getComponentStyles().getStyleFor(it)).isEqualTo(EXPECTED_STYLE)
        }
    }

    @Test
    fun shouldProperlyApplyThemeToChildren() {
        val component = LabelBuilder.newBuilder()
                .text("text")
                .build()
        target.addComponent(component)
        target.applyColorTheme(THEME)

        assertThat(component.getComponentStyles().getCurrentStyle())
                .isEqualTo(DefaultLabelTest.DEFAULT_STYLE)
    }

    companion object {
        val BOX_TYPE = BoxType.LEFT_RIGHT_DOUBLE
        val TITLE = "TITLE"
        val FONT = CP437TilesetResource.WANDERLUST_16X16
        val SIZE = Size.create(5, 6)
        val POSITION = Position.create(2, 3)
        val THEME = ColorThemeResource.ADRIFT_IN_DREAMS.getTheme()
        val EXPECTED_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.getBrightForegroundColor())
                .backgroundColor(THEME.getBrightBackgroundColor())
                .build()
    }
}
