package org.codetome.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.component.RadioButtonGroup
import org.codetome.zircon.api.builder.component.ComponentStyleSetBuilder
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.builder.graphics.StyleSetBuilder
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.api.input.MouseActionType
import org.codetome.zircon.api.interop.Modifiers
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.api.resource.ColorThemeResource
import org.codetome.zircon.api.util.Consumer
import org.codetome.zircon.internal.event.Event
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.tileset.impl.TilesetLoaderRegistry
import org.codetome.zircon.internal.tileset.impl.TestTilesetLoader
import org.codetome.zircon.platform.factory.ThreadSafeQueueFactory
import org.junit.Before
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean

class DefaultRadioButtonGroupTest {

    lateinit var target: DefaultRadioButtonGroup
    lateinit var tileset: Tileset

    @Before
    fun setUp() {
        TilesetLoaderRegistry.setFontLoader(TestTilesetLoader())
        tileset = DefaultLabelTest.FONT.toFont()
        target = DefaultRadioButtonGroup(
                wrappers = ThreadSafeQueueFactory.create(),
                size = SIZE,
                position = POSITION,
                componentStyleSet = COMPONENT_STYLES,
                initialTileset = tileset)
    }

    @Test
    fun shouldNotAcceptFocus() {
        assertThat(target.acceptsFocus()).isFalse()
    }

    @Test
    fun shouldUseProperFont() {
        assertThat(target.getCurrentFont().getId())
                .isEqualTo(tileset.getId())
    }

    @Test
    fun shouldNotTakeGivenFocus() {
        assertThat(target.giveFocus()).isFalse()
    }

    @Test
    fun shouldProperlySignalComponentChangeOnMouseRelease() {
        val componentChanged = AtomicBoolean(false)
        EventBus.subscribe<Event.ComponentChange> {
            componentChanged.set(true)
        }

        EventBus.sendTo(target.getId(), Event.MouseReleased(MouseAction(MouseActionType.MOUSE_RELEASED, 1, POSITION)))

        assertThat(componentChanged.get()).isTrue()
    }

    @Test
    fun shouldSelectChildButtonWhenClicked() {
        val button = target.addOption("foo", "bar")

        EventBus.sendTo(button.getId(), Event.MouseReleased(MouseAction(MouseActionType.MOUSE_RELEASED, 1, POSITION)))

        assertThat(button.isSelected()).isTrue()
    }

    @Test
    fun shouldProperlyDeselectPreviouslySelectedButton() {
        val oldButton = target.addOption("foo", "bar")
        val newButton = target.addOption("baz", "qux")


        EventBus.sendTo(oldButton.getId(), Event.MouseReleased(MouseAction(MouseActionType.MOUSE_RELEASED, 1, POSITION)))
        EventBus.sendTo(newButton.getId(), Event.MouseReleased(MouseAction(MouseActionType.MOUSE_RELEASED, 1, POSITION)))

        assertThat(oldButton.isSelected()).isFalse()
        assertThat(newButton.isSelected()).isTrue()
    }

    @Test
    fun shouldProperlyNotifyListenersWhenAButtonIsClicked() {
        val button = target.addOption("foo", "bar")

        val selected = AtomicBoolean(false)
        target.onSelection(object : Consumer<RadioButtonGroup.Selection> {
            override fun accept(t: RadioButtonGroup.Selection) {
                selected.set(true)
            }
        })

        EventBus.sendTo(button.getId(), Event.MouseReleased(MouseAction(MouseActionType.MOUSE_RELEASED, 1, POSITION)))

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
                .modifiers(Modifiers.crossedOut())
                .build()
        val COMPONENT_STYLES = ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(DEFAULT_STYLE)
                .build()

        val EXPECTED_DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.getAccentColor())
                .backgroundColor(TextColor.transparent())
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
