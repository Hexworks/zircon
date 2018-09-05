package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.component.ButtonBuilder
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ComponentState
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.input.MouseActionType
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.api.Modifiers
import org.junit.Before
import org.junit.Test

class DefaultButtonTest {

    lateinit var target: DefaultButton
    lateinit var tileset: TilesetResource

    @Before
    fun setUp() {
        tileset = FONT
        target = ButtonBuilder.newBuilder()
                .componentStyleSet(COMPONENT_STYLES)
                .position(POSITION)
                .tileset(tileset)
                .text(TEXT)
                .build() as DefaultButton
    }

    @Test
    fun shouldProperlyAddButtonText() {
        val surface = target.getDrawSurface()
        val offset = target.getWrapperOffset().x
        TEXT.forEachIndexed { i, char ->
            assertThat(surface.getTileAt(Position.create(i + offset, 0)).get())
                    .isEqualTo(TileBuilder.newBuilder()
                            .character(char)
                            .styleSet(DEFAULT_STYLE)
                            .build())
        }
    }

    @Test
    fun shouldUseProperFont() {
        assertThat(target.tileset().id)
                .isEqualTo(tileset.id)
    }

    @Test
    fun shouldProperlyReturnText() {
        assertThat(target.getText()).isEqualTo(TEXT)
    }

    @Test
    fun shouldProperlyApplyTheme() {
        target.applyColorTheme(THEME)
        val styles = target.getComponentStyles()
        assertThat(styles.getStyleFor(ComponentState.DEFAULT))
                .isEqualTo(EXPECTED_DEFAULT_STYLE)
        assertThat(styles.getStyleFor(ComponentState.MOUSE_OVER))
                .isEqualTo(EXPECTED_MOUSE_OVER_STYLE)
        assertThat(styles.getStyleFor(ComponentState.FOCUSED))
                .isEqualTo(EXPECTED_FOCUSED_STYLE)
        assertThat(styles.getStyleFor(ComponentState.ACTIVE))
                .isEqualTo(EXPECTED_ACTIVE_STYLE)
        assertThat(styles.getStyleFor(ComponentState.DISABLED))
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
        assertThat(target.getComponentStyles().getCurrentStyle()).isEqualTo(EXPECTED_FOCUSED_STYLE)
    }

    @Test
    fun shouldProperlyTakeFocus() {
        target.applyColorTheme(THEME)

        target.takeFocus()

        assertThat(target.getComponentStyles().getCurrentStyle()).isEqualTo(EXPECTED_DEFAULT_STYLE)
    }

    @Test
    fun shouldProperlyHandleMousePress() {
        target.applyColorTheme(THEME)

        EventBus.sendTo(
                identifier = target.id,
                event = ZirconEvent.MousePressed(MouseAction(MouseActionType.MOUSE_PRESSED, 1, Position.defaultPosition())))

        assertThat(target.getComponentStyles().getCurrentStyle()).isEqualTo(EXPECTED_ACTIVE_STYLE)
    }

    @Test
    fun shouldProperlyHandleMouseRelease() {
        target.applyColorTheme(THEME)

        EventBus.sendTo(
                identifier = target.id,
                event = ZirconEvent.MouseReleased(MouseAction(MouseActionType.MOUSE_RELEASED, 1, Position.defaultPosition())))

        assertThat(target.getComponentStyles().getCurrentStyle()).isEqualTo(EXPECTED_MOUSE_OVER_STYLE)
    }

    companion object {
        val THEME = ColorThemeResource.ADRIFT_IN_DREAMS.getTheme()
        val TEXT = "Button text"
        val POSITION = Position.create(4, 5)
        val FONT = BuiltInCP437TilesetResource.WANDERLUST_16X16
        val DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .backgroundColor(ANSITileColor.RED)
                .foregroundColor(ANSITileColor.GREEN)
                .modifiers(Modifiers.crossedOut())
                .build()
        val COMPONENT_STYLES = ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(DEFAULT_STYLE)
                .build()

        val EXPECTED_DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.accentColor())
                .backgroundColor(TileColor.transparent())
                .build()

        val EXPECTED_MOUSE_OVER_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.primaryBackgroundColor())
                .backgroundColor(THEME.accentColor())
                .build()

        val EXPECTED_FOCUSED_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.secondaryBackgroundColor())
                .backgroundColor(THEME.accentColor())
                .build()

        val EXPECTED_ACTIVE_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.secondaryForegroundColor())
                .backgroundColor(THEME.accentColor())
                .build()
    }
}
