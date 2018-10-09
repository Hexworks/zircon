package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.Modifiers
import org.hexworks.zircon.api.builder.component.ButtonBuilder
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.input.MouseActionType
import org.hexworks.zircon.api.input.MouseActionType.MOUSE_PRESSED
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.api.resource.TilesetResource
import org.junit.Before
import org.junit.Test

class DefaultButtonTest {

    lateinit var target: DefaultButton
    lateinit var tileset: TilesetResource

    @Before
    fun setUp() {
        tileset = FONT
        target = ButtonBuilder.newBuilder()
                .withComponentStyleSet(COMPONENT_STYLES)
                .withPosition(POSITION)
                .withTileset(tileset)
                .withText(TEXT)
                .build() as DefaultButton
    }

    @Test
    fun shouldProperlyAddButtonText() {
        val surface = target.tileGraphics
        val offset = target.contentPosition.x
        TEXT.forEachIndexed { i, char ->
            assertThat(surface.getTileAt(Position.create(i + offset, 0)).get())
                    .isEqualTo(TileBuilder.newBuilder()
                            .withCharacter(char)
                            .withStyleSet(DEFAULT_STYLE)
                            .build())
        }
    }

    @Test
    fun shouldUseProperFont() {
        assertThat(target.currentTileset().id)
                .isEqualTo(tileset.id)
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
    fun shouldProperlyHandleMousePress() {
        target.applyColorTheme(THEME)

        target.mousePressed(MouseAction(MOUSE_PRESSED, 1, Position.defaultPosition()))

        assertThat(target.componentStyleSet.currentStyle()).isEqualTo(EXPECTED_ACTIVE_STYLE)
    }

    @Test
    fun shouldProperlyHandleMouseRelease() {
        target.applyColorTheme(THEME)

        target.mouseReleased(MouseAction(MouseActionType.MOUSE_RELEASED, 1, Position.defaultPosition()))

        assertThat(target.componentStyleSet.currentStyle()).isEqualTo(EXPECTED_MOUSE_OVER_STYLE)
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
