package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.RadioButtonGroup
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.input.MouseActionType
import org.hexworks.zircon.api.resource.CP437Tilesets
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Consumer
import org.hexworks.zircon.internal.event.InternalEvent
import org.hexworks.zircon.api.Modifiers
import org.hexworks.zircon.platform.factory.ThreadSafeQueueFactory
import org.junit.Before
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean

class DefaultRadioButtonGroupTest {

    lateinit var target: DefaultRadioButtonGroup
    lateinit var tileset: TilesetResource

    @Before
    fun setUp() {
        tileset = DefaultLabelTest.FONT
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
        assertThat(target.tileset().id)
                .isEqualTo(tileset.id)
    }

    @Test
    fun shouldNotTakeGivenFocus() {
        assertThat(target.giveFocus()).isFalse()
    }

    @Test
    fun shouldSelectChildButtonWhenClicked() {
        val button = target.addOption("foo", "bar")

        EventBus.sendTo(button.id, InternalEvent.MouseReleased(MouseAction(MouseActionType.MOUSE_RELEASED, 1, POSITION)))

        assertThat(button.isSelected()).isTrue()
    }

    @Test
    fun shouldProperlyDeselectPreviouslySelectedButton() {
        val oldButton = target.addOption("foo", "bar")
        val newButton = target.addOption("baz", "qux")


        EventBus.sendTo(oldButton.id, InternalEvent.MouseReleased(MouseAction(MouseActionType.MOUSE_RELEASED, 1, POSITION)))
        EventBus.sendTo(newButton.id, InternalEvent.MouseReleased(MouseAction(MouseActionType.MOUSE_RELEASED, 1, POSITION)))

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

        EventBus.sendTo(button.id, InternalEvent.MouseReleased(MouseAction(MouseActionType.MOUSE_RELEASED, 1, POSITION)))

        assertThat(selected.get()).isTrue()
    }

    companion object {
        val THEME = ColorThemeResource.ADRIFT_IN_DREAMS.getTheme()
        val TEXT = "Button text"
        val FONT = CP437Tilesets.WANDERLUST_16X16
        val POSITION = Position.create(4, 5)
        val SIZE = Size.create(10, 20)
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
