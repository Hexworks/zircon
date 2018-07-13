package org.codetome.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.component.ComponentState
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.graphics.BoxType
import org.codetome.zircon.api.modifier.BorderBuilder
import org.codetome.zircon.api.modifier.BorderType
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.api.resource.ColorThemeResource
import org.codetome.zircon.internal.component.builder.LabelBuilder
import org.codetome.zircon.internal.component.builder.PanelBuilder
import org.codetome.zircon.internal.font.FontLoaderRegistry
import org.codetome.zircon.internal.font.impl.TestFontLoader
import org.junit.Before
import org.junit.Test

class DefaultPanelTest {

    lateinit var target: DefaultPanel
    lateinit var font: Font

    @Before
    fun setUp() {
        FontLoaderRegistry.setFontLoader(TestFontLoader())
        font = DefaultLabelTest.FONT.toFont()
        target = PanelBuilder.newBuilder()
                .wrapWithShadow()
                .boxType(BOX_TYPE)
                .title(TITLE)
                .font(font)
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
        assertThat(target.getCurrentFont().getId())
                .isEqualTo(font.getId())
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
