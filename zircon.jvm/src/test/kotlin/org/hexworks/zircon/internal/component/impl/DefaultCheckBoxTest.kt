package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions
import org.hexworks.zircon.api.builder.component.CheckBoxBuilder
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
import org.hexworks.zircon.api.resource.BuiltInCP437Tileset
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.api.Modifiers
import org.junit.Before
import org.junit.Test

class DefaultCheckBoxTest {

    lateinit var target: DefaultCheckBox
    lateinit var tileset: TilesetResource

    @Before
    fun setUp() {
        tileset = FONT
        target = CheckBoxBuilder.newBuilder()
                .componentStyles(COMPONENT_STYLES)
                .tileset(tileset)
                .position(POSITION)
                .text(TEXT)
                .build() as DefaultCheckBox
    }

    @Test
    fun shouldProperlyAddCheckBoxText() {
        val surface = target.getDrawSurface()
        val offset = 4
        TEXT.forEachIndexed { i, char ->
            Assertions.assertThat(surface.getTileAt(Position.create(i + offset, 0)).get())
                    .isEqualTo(TileBuilder.newBuilder()
                            .character(char)
                            .styleSet(DEFAULT_STYLE)
                            .build())
        }
    }

    @Test
    fun shouldUseProperFont() {
        Assertions.assertThat(target.tileset().id)
                .isEqualTo(tileset.id)
    }

    @Test
    fun shouldProperlyReturnText() {
        Assertions.assertThat(target.getText()).isEqualTo(TEXT)
    }

    @Test
    fun shouldProperlyApplyTheme() {
        target.applyColorTheme(THEME)
        val styles = target.getComponentStyles()
        Assertions.assertThat(styles.getStyleFor(ComponentState.DEFAULT))
                .isEqualTo(EXPECTED_DEFAULT_STYLE)
        Assertions.assertThat(styles.getStyleFor(ComponentState.MOUSE_OVER))
                .isEqualTo(EXPECTED_MOUSE_OVER_STYLE)
        Assertions.assertThat(styles.getStyleFor(ComponentState.FOCUSED))
                .isEqualTo(EXPECTED_FOCUSED_STYLE)
        Assertions.assertThat(styles.getStyleFor(ComponentState.ACTIVE))
                .isEqualTo(EXPECTED_ACTIVE_STYLE)
        Assertions.assertThat(styles.getStyleFor(ComponentState.DISABLED))
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
        Assertions.assertThat(target.getComponentStyles().getCurrentStyle()).isEqualTo(EXPECTED_FOCUSED_STYLE)
    }

    @Test
    fun shouldProperlyTakeFocus() {
        target.applyColorTheme(THEME)

        target.takeFocus()

        Assertions.assertThat(target.getComponentStyles().getCurrentStyle()).isEqualTo(EXPECTED_DEFAULT_STYLE)
    }

//    @Test TODO: re-enable in next release
//    fun shouldProperlyHandleMousePress() {
//        target.applyColorTheme(THEME)
//        val componentChanged = AtomicBoolean(false)
//        EventBus.subscribe(EventType.ComponentChange, {
//            componentChanged.set(true)
//        })
//
//        EventBus.broadcast(
//                type = EventType.MousePressed(target.id),
//                data = MouseAction(MouseActionType.MOUSE_PRESSED, 1, Position.defaultPosition()))
//
//        Assertions.assertThat(componentChanged.get()).isTrue()
//        Assertions.assertThat(target.getComponentStyles().getCurrentStyle()).isEqualTo(EXPECTED_ACTIVE_STYLE)
//    }

    @Test
    fun shouldProperlyHandleMouseRelease() {
        target.applyColorTheme(THEME)

        EventBus.sendTo(
                identifier = target.id,
                event = ZirconEvent.MouseReleased(MouseAction(MouseActionType.MOUSE_RELEASED, 1, Position.defaultPosition())))

        Assertions.assertThat(target.getComponentStyles().getCurrentStyle()).isEqualTo(EXPECTED_MOUSE_OVER_STYLE)
    }

    companion object {
        val THEME = ColorThemeResource.ADRIFT_IN_DREAMS.getTheme()
        val TEXT = "Button text"
        val POSITION = Position.create(4, 5)
        val FONT = BuiltInCP437Tileset.WANDERLUST_16X16
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
