package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions
import org.hexworks.zircon.api.Modifiers
import org.hexworks.zircon.api.builder.component.CheckBoxBuilder
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.input.MouseActionType
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.api.resource.TilesetResource
import org.junit.Before
import org.junit.Test

class DefaultCheckBoxTest {

    lateinit var target: DefaultCheckBox
    lateinit var tileset: TilesetResource

    @Before
    fun setUp() {
        tileset = FONT
        target = CheckBoxBuilder.newBuilder()
                .withComponentStyleSet(COMPONENT_STYLES)
                .withTileset(tileset)
                .withPosition(POSITION)
                .withText(TEXT)
                .build() as DefaultCheckBox
    }

    @Test
    fun shouldProperlyAddCheckBoxText() {
        val surface = target.tileGraphics
        val offset = 4
        TEXT.forEachIndexed { i, char ->
            Assertions.assertThat(surface.getTileAt(Position.create(i + offset, 0)).get())
                    .isEqualTo(TileBuilder.newBuilder()
                            .withCharacter(char)
                            .withStyleSet(DEFAULT_STYLE)
                            .build())
        }
    }

    @Test
    fun shouldUseProperFont() {
        Assertions.assertThat(target.currentTileset().id)
                .isEqualTo(tileset.id)
    }

    @Test
    fun shouldProperlyReturnText() {
        Assertions.assertThat(target.text).isEqualTo(TEXT)
    }

    @Test
    fun shouldProperlyApplyTheme() {
        target.applyColorTheme(THEME)
        val styles = target.componentStyleSet
        Assertions.assertThat(styles.fetchStyleFor(ComponentState.DEFAULT))
                .isEqualTo(EXPECTED_DEFAULT_STYLE)
        Assertions.assertThat(styles.fetchStyleFor(ComponentState.MOUSE_OVER))
                .isEqualTo(EXPECTED_MOUSE_OVER_STYLE)
        Assertions.assertThat(styles.fetchStyleFor(ComponentState.FOCUSED))
                .isEqualTo(EXPECTED_FOCUSED_STYLE)
        Assertions.assertThat(styles.fetchStyleFor(ComponentState.ACTIVE))
                .isEqualTo(EXPECTED_ACTIVE_STYLE)
        Assertions.assertThat(styles.fetchStyleFor(ComponentState.DISABLED))
                .isEqualTo(EXPECTED_DEFAULT_STYLE)
    }

    @Test
    fun shouldAcceptFocus() {
        Assertions.assertThat(target.acceptsFocus()).isTrue()
    }

    @Test
    fun shouldProperlyGiveFocus() {
        target.applyColorTheme(THEME)

        val result = target.giveFocus()

        Assertions.assertThat(result).isTrue()
        Assertions.assertThat(target.componentStyleSet.currentStyle()).isEqualTo(EXPECTED_FOCUSED_STYLE)
    }

    @Test
    fun shouldProperlyTakeFocus() {
        target.applyColorTheme(THEME)

        target.takeFocus()

        Assertions.assertThat(target.componentStyleSet.currentStyle()).isEqualTo(EXPECTED_DEFAULT_STYLE)
    }


    @Test
    fun shouldProperlyHandleMouseRelease() {
        target.applyColorTheme(THEME)

        target.mouseReleased(MouseAction(MouseActionType.MOUSE_RELEASED, 1, Position.defaultPosition()))

        Assertions.assertThat(target.componentStyleSet.currentStyle()).isEqualTo(EXPECTED_MOUSE_OVER_STYLE)
    }

    companion object {
        val THEME = ColorThemeResource.ADRIFT_IN_DREAMS.getTheme()
        val TEXT = "Button text"
        val POSITION = Position.create(4, 5)
        val FONT = BuiltInCP437TilesetResource.WANDERLUST_16X16
        val DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .withBackgroundColor(ANSITileColor.RED)
                .withForegroundColor(ANSITileColor.GREEN)
                .withModifiers(Modifiers.crossedOut())
                .build()
        val COMPONENT_STYLES = ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(DEFAULT_STYLE)
                .build()

        val EXPECTED_DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .withForegroundColor(THEME.accentColor)
                .withBackgroundColor(TileColor.transparent())
                .build()

        val EXPECTED_MOUSE_OVER_STYLE = StyleSetBuilder.newBuilder()
                .withForegroundColor(THEME.primaryBackgroundColor)
                .withBackgroundColor(THEME.accentColor)
                .build()

        val EXPECTED_FOCUSED_STYLE = StyleSetBuilder.newBuilder()
                .withForegroundColor(THEME.secondaryBackgroundColor)
                .withBackgroundColor(THEME.accentColor)
                .build()

        val EXPECTED_ACTIVE_STYLE = StyleSetBuilder.newBuilder()
                .withForegroundColor(THEME.secondaryForegroundColor)
                .withBackgroundColor(THEME.accentColor)
                .build()
    }
}
