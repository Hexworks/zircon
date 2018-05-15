package org.codetome.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Modifiers
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.color.TextColorFactory
import org.codetome.zircon.api.component.builder.RadioButtonGroupBuilder
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.api.input.MouseActionType
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.api.resource.ColorThemeResource
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import org.codetome.zircon.internal.font.FontLoaderRegistry
import org.codetome.zircon.internal.font.impl.FontSettings
import org.codetome.zircon.internal.font.impl.TestFontLoader
import org.codetome.zircon.internal.font.impl.VirtualFontLoader
import org.junit.Before
import org.junit.Test
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.function.Consumer

class DefaultRadioButtonGroupTest {

    lateinit var target: DefaultRadioButtonGroup
    lateinit var font: Font

    @Before
    fun setUp() {
        FontLoaderRegistry.setFontLoader(TestFontLoader())
        font = DefaultLabelTest.FONT.toFont()
        target = DefaultRadioButtonGroup(
                wrappers = LinkedList(),
                size = SIZE,
                position = POSITION,
                componentStyles = COMPONENT_STYLES,
                initialFont = font)
    }

    @Test
    fun shouldNotAcceptFocus() {
        assertThat(target.acceptsFocus()).isFalse()
    }

    @Test
    fun shouldUseProperFont() {
        assertThat(target.getCurrentFont().getId())
                .isEqualTo(font.getId())
    }

    @Test
    fun shouldNotTakeGivenFocus() {
        assertThat(target.giveFocus()).isFalse()
    }

    @Test
    fun shouldProperlySignalComponentChangeOnMouseRelease() {
        val componentChanged = AtomicBoolean(false)
        EventBus.subscribe(EventType.ComponentChange, {
            componentChanged.set(true)
        })

        EventBus.emit(EventType.MouseReleased(target.getId()), MouseAction(MouseActionType.MOUSE_RELEASED, 1, POSITION))

        assertThat(componentChanged.get()).isTrue()
    }

    @Test
    fun shouldSelectChildButtonWhenClicked() {
        val button = target.addOption("foo", "bar")

        EventBus.emit(EventType.MouseReleased(button.getId()), MouseAction(MouseActionType.MOUSE_RELEASED, 1, POSITION))

        assertThat(button.isSelected()).isTrue()
    }

    @Test
    fun shouldProperlyDeselectPreviouslySelectedButton() {
        val oldButton = target.addOption("foo", "bar")
        val newButton = target.addOption("baz", "qux")


        EventBus.emit(EventType.MouseReleased(oldButton.getId()), MouseAction(MouseActionType.MOUSE_RELEASED, 1, POSITION))
        EventBus.emit(EventType.MouseReleased(newButton.getId()), MouseAction(MouseActionType.MOUSE_RELEASED, 1, POSITION))

        assertThat(oldButton.isSelected()).isFalse()
        assertThat(newButton.isSelected()).isTrue()
    }

    @Test
    fun shouldProperlyNotifyListenersWhenAButtonIsClicked() {
        val button = target.addOption("foo", "bar")

        val selected = AtomicBoolean(false)
        target.onSelection(Consumer {
            selected.set(true)
        })

        EventBus.emit(EventType.MouseReleased(button.getId()), MouseAction(MouseActionType.MOUSE_RELEASED, 1, POSITION))

        assertThat(selected.get()).isTrue()
    }

    companion object {
        val THEME = ColorThemeResource.ADRIFT_IN_DREAMS.getTheme()
        val TEXT = "Button text"
        val FONT = CP437TilesetResource.WANDERLUST_16X16
        val POSITION = Position.create(4, 5)
        val SIZE = Size.create(10, 20)
        val DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .backgroundColor(ANSITextColor.RED)
                .foregroundColor(ANSITextColor.GREEN)
                .modifiers(Modifiers.CROSSED_OUT)
                .build()
        val COMPONENT_STYLES = ComponentStylesBuilder.newBuilder()
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
