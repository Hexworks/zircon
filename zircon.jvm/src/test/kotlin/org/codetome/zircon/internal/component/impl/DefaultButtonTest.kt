package org.codetome.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.builder.ComponentStyleSetBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.component.ComponentState
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.api.input.MouseActionType
import org.codetome.zircon.api.interop.Modifiers
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.api.resource.ColorThemeResource
import org.codetome.zircon.internal.component.builder.ButtonBuilder
import org.codetome.zircon.internal.event.Event
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.font.FontLoaderRegistry
import org.codetome.zircon.internal.font.impl.TestFontLoader
import org.codetome.zircon.internal.multiplatform.factory.TextColorFactory
import org.junit.Before
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean

class DefaultButtonTest {

    lateinit var target: DefaultButton
    lateinit var font: Font

    @Before
    fun setUp() {
        FontLoaderRegistry.setFontLoader(TestFontLoader())
        font = FONT.toFont()
        target = ButtonBuilder.newBuilder()
                .componentStyles(COMPONENT_STYLES)
                .position(POSITION)
                .font(font)
                .text(TEXT)
                .build() as DefaultButton
    }

    @Test
    fun shouldProperlyAddButtonText() {
        val surface = target.getDrawSurface()
        val offset = target.getWrapperOffset().x
        TEXT.forEachIndexed { i, char ->
            assertThat(surface.getCharacterAt(Position.create(i + offset, 0)).get())
                    .isEqualTo(TextCharacterBuilder.newBuilder()
                            .character(char)
                            .styleSet(DEFAULT_STYLE)
                            .build())
        }
    }

    @Test
    fun shouldUseProperFont() {
        assertThat(target.getCurrentFont().getId())
                .isEqualTo(font.getId())
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
        val componentChanged = AtomicBoolean(false)
        EventBus.subscribe<Event.ComponentChange> {
            componentChanged.set(true)
        }

        val result = target.giveFocus()

        assertThat(result).isTrue()
        assertThat(componentChanged.get()).isTrue()
        assertThat(target.getComponentStyles().getCurrentStyle()).isEqualTo(EXPECTED_FOCUSED_STYLE)
    }

    @Test
    fun shouldProperlyTakeFocus() {
        target.applyColorTheme(THEME)
        val componentChanged = AtomicBoolean(false)
        EventBus.subscribe<Event.ComponentChange> {
            componentChanged.set(true)
        }

        target.takeFocus()

        assertThat(componentChanged.get()).isTrue()
        assertThat(target.getComponentStyles().getCurrentStyle()).isEqualTo(EXPECTED_DEFAULT_STYLE)
    }

    @Test
    fun shouldProperlyHandleMousePress() {
        target.applyColorTheme(THEME)
        val componentChanged = AtomicBoolean(false)
        EventBus.subscribe<Event.ComponentChange> {
            componentChanged.set(true)
        }

        EventBus.sendTo(
                identifier = target.getId(),
                event = Event.MousePressed(MouseAction(MouseActionType.MOUSE_PRESSED, 1, Position.defaultPosition())))

        assertThat(componentChanged.get()).isTrue()
        assertThat(target.getComponentStyles().getCurrentStyle()).isEqualTo(EXPECTED_ACTIVE_STYLE)
    }

    @Test
    fun shouldProperlyHandleMouseRelease() {
        target.applyColorTheme(THEME)
        val componentChanged = AtomicBoolean(false)
        EventBus.subscribe<Event.ComponentChange> {
            componentChanged.set(true)
        }

        EventBus.sendTo(
                identifier = target.getId(),
                event = Event.MouseReleased(MouseAction(MouseActionType.MOUSE_RELEASED, 1, Position.defaultPosition())))

        assertThat(componentChanged.get()).isTrue()
        assertThat(target.getComponentStyles().getCurrentStyle()).isEqualTo(EXPECTED_MOUSE_OVER_STYLE)
    }

    companion object {
        val THEME = ColorThemeResource.ADRIFT_IN_DREAMS.getTheme()
        val TEXT = "Button text"
        val POSITION = Position.create(4, 5)
        val FONT = CP437TilesetResource.WANDERLUST_16X16
        val DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .backgroundColor(ANSITextColor.RED)
                .foregroundColor(ANSITextColor.GREEN)
                .modifiers(Modifiers.CROSSED_OUT)
                .build()
        val COMPONENT_STYLES = ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(DEFAULT_STYLE)
                .build()

        val EXPECTED_DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.getAccentColor())
                .backgroundColor(TextColorFactory.transparent())
                .build()

        val EXPECTED_MOUSE_OVER_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.getBrightBackgroundColor())
                .backgroundColor(THEME.getAccentColor())
                .build()

        val EXPECTED_FOCUSED_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.getDarkBackgroundColor())
                .backgroundColor(THEME.getAccentColor())
                .build()

        val EXPECTED_ACTIVE_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.getDarkForegroundColor())
                .backgroundColor(THEME.getAccentColor())
                .build()
    }
}
