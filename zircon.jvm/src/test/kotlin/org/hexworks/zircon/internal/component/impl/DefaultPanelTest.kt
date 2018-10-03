package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.component.LabelBuilder
import org.hexworks.zircon.api.builder.component.PanelBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.ComponentState
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.api.resource.TilesetResource
import org.junit.Before
import org.junit.Test

class DefaultPanelTest {

    lateinit var target: DefaultPanel
    lateinit var tileset: TilesetResource

    @Before
    fun setUp() {
        tileset = DefaultLabelTest.FONT
        target = PanelBuilder.newBuilder()
                .wrapWithShadow(true)
                .withBoxType(BOX_TYPE)
                .withTitle(TITLE)
                .withTileset(tileset)
                .withSize(SIZE)
                .wrapWithBox(true)
                .withPosition(POSITION)
                .build() as DefaultPanel
    }

    @Test
    fun shouldHaveProperTitle() {
        assertThat(target.title).isEqualTo(TITLE)
    }

    @Test
    fun shouldUseProperFont() {
        assertThat(target.currentTileset().id)
                .isEqualTo(tileset.id)
    }

    @Test
    fun shouldProperlySetPosition() {
        assertThat(target.position).isEqualTo(POSITION)
    }

    @Test
    fun shouldProperlySetSize() {
        assertThat(target.size).isEqualTo(SIZE)
    }

    @Test
    fun shouldProperlyApplyTheme() {
        target.applyColorTheme(THEME)

        ComponentState.values().forEach {
            assertThat(target.componentStyleSet.fetchStyleFor(it)).isEqualTo(EXPECTED_STYLE)
        }
    }

    @Test
    fun shouldProperlyApplyThemeToChildren() {
        val component = LabelBuilder.newBuilder()
                .text("fo")
                .build()
        target.addComponent(component)
        target.applyColorTheme(THEME)

        assertThat(component.componentStyleSet.currentStyle())
                .isEqualTo(DefaultLabelTest.DEFAULT_STYLE)
    }

    companion object {
        val BOX_TYPE = BoxType.LEFT_RIGHT_DOUBLE
        val TITLE = "TITLE"
        val FONT = BuiltInCP437TilesetResource.WANDERLUST_16X16
        val SIZE = Size.create(5, 6)
        val POSITION = Position.create(2, 3)
        val THEME = ColorThemeResource.ADRIFT_IN_DREAMS.getTheme()
        val EXPECTED_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.secondaryForegroundColor)
                .backgroundColor(THEME.primaryBackgroundColor)
                .build()
    }
}
