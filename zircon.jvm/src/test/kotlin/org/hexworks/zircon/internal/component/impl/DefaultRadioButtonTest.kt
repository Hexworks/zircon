package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.Modifiers
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.internal.component.renderer.DefaultRadioButtonRenderer
import org.junit.Before
import org.junit.Test

class DefaultRadioButtonTest {

    lateinit var target: DefaultRadioButton

    @Before
    fun setUp() {
        target = DefaultRadioButton(
                componentMetadata = ComponentMetadata(
                        size = Size.create(WIDTH, 1),
                        position = POSITION,
                        componentStyleSet = COMPONENT_STYLES,
                        tileset = TILESET),
                text = TEXT,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = listOf(),
                        componentRenderer = DefaultRadioButtonRenderer()))
    }

    @Test
    fun shouldProperlyAddRadioButtonText() {
        val surface = target.tileGraphics
        val offset = 4
        TEXT.forEachIndexed { i, char ->
            assertThat(surface.getTileAt(Position.create(i + offset, 0)).get())
                    .isEqualTo(TileBuilder.newBuilder()
                            .character(char)
                            .styleSet(DEFAULT_STYLE)
                            .build())
        }
    }

    @Test
    fun shouldProperlyReturnText() {
        assertThat(target.text).isEqualTo(TEXT)
    }

    @Test
    fun shouldProperlyApplyTheme() {
        target.applyColorTheme(THEME)
        val styles = target.componentStyleSet
        assertThat(styles.fetchStyleFor(ComponentState.DEFAULT))
                .isEqualTo(EXPECTED_DEFAULT_STYLE)
        assertThat(styles.fetchStyleFor(ComponentState.MOUSE_OVER))
                .isEqualTo(EXPECTED_MOUSE_OVER_STYLE)
        assertThat(styles.fetchStyleFor(ComponentState.FOCUSED))
                .isEqualTo(EXPECTED_FOCUSED_STYLE)
        assertThat(styles.fetchStyleFor(ComponentState.ACTIVE))
                .isEqualTo(EXPECTED_ACTIVE_STYLE)
        assertThat(styles.fetchStyleFor(ComponentState.DISABLED))
                .isEqualTo(EXPECTED_DEFAULT_STYLE)
    }

    @Test
    fun shouldAcceptFocus() {
        assertThat(target.acceptsFocus()).isTrue()
    }

    @Test
    fun shouldProperlyGiveFocus() {
        target.applyColorTheme(THEME)

        val result = target.giveFocus()

        assertThat(result).isTrue()
        assertThat(target.componentStyleSet.currentStyle()).isEqualTo(EXPECTED_FOCUSED_STYLE)
    }

    @Test
    fun shouldProperlyTakeFocus() {
        target.applyColorTheme(THEME)

        target.takeFocus()

        assertThat(target.componentStyleSet.currentStyle()).isEqualTo(EXPECTED_DEFAULT_STYLE)
    }

    @Test
    fun shouldProperlySelect() {
        target.applyColorTheme(THEME)
        target.select()

        assertThat(getButtonChar()).isEqualTo('O')
        assertThat(target.isSelected()).isTrue()
        assertThat(target.componentStyleSet.currentStyle()).isEqualTo(EXPECTED_MOUSE_OVER_STYLE)
    }

    @Test
    fun shouldProperlyRemoveSelection() {
        target.applyColorTheme(THEME)
        target.select()
        target.removeSelection()

        assertThat(getButtonChar()).isEqualTo(' ')
        assertThat(target.isSelected()).isFalse()
        assertThat(target.componentStyleSet.currentStyle()).isEqualTo(EXPECTED_DEFAULT_STYLE)
    }

    private fun getButtonChar() = target.tileGraphics.getTileAt(Position.create(1, 0))
            .get().asCharacterTile().get().character

    companion object {
        val TILESET = BuiltInCP437TilesetResource.REX_PAINT_10X10
        val THEME = ColorThemeResource.ADRIFT_IN_DREAMS.getTheme()
        val TEXT = "Button text"
        val WIDTH = 20
        val POSITION = Position.create(4, 5)
        val DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .backgroundColor(ANSITileColor.RED)
                .foregroundColor(ANSITileColor.GREEN)
                .modifiers(Modifiers.crossedOut())
                .build()
        val COMPONENT_STYLES = ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(DEFAULT_STYLE)
                .build()

        val EXPECTED_DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.accentColor)
                .backgroundColor(TileColor.transparent())
                .build()

        val EXPECTED_MOUSE_OVER_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.primaryBackgroundColor)
                .backgroundColor(THEME.accentColor)
                .build()

        val EXPECTED_FOCUSED_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.secondaryBackgroundColor)
                .backgroundColor(THEME.accentColor)
                .build()

        val EXPECTED_ACTIVE_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.secondaryForegroundColor)
                .backgroundColor(THEME.accentColor)
                .build()
    }
}
