package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.Modifiers
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.input.MouseActionType
import org.hexworks.zircon.api.kotlin.onSelection
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.component.renderer.DefaultRadioButtonGroupRenderer
import org.junit.Before
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean

@Suppress("MemberVisibilityCanBePrivate")
class DefaultRadioButtonGroupTest {

    lateinit var target: DefaultRadioButtonGroup
    lateinit var tileset: TilesetResource

    @Before
    fun setUp() {
        tileset = DefaultLabelTest.FONT
        target = DefaultRadioButtonGroup(
                size = SIZE,
                position = POSITION,
                componentStyleSet = COMPONENT_STYLES,
                tileset = tileset,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = listOf(),
                        componentRenderer = DefaultRadioButtonGroupRenderer()))
    }

    @Test
    fun shouldNotAcceptFocus() {
        assertThat(target.acceptsFocus()).isFalse()
    }

    @Test
    fun shouldUseProperFont() {
        assertThat(target.currentTileset().id)
                .isEqualTo(tileset.id)
    }

    @Test
    fun shouldNotTakeGivenFocus() {

        assertThat(target.giveFocus(Maybe.empty())).isFalse()
    }

    @Test
    fun shouldSelectChildButtonWhenClicked() {
        val button = target.addOption("foo", "bar") as DefaultRadioButton

        button.mouseReleased(MouseAction(MouseActionType.MOUSE_RELEASED, 1, POSITION))

        assertThat(button.isSelected()).isTrue()
    }

    @Test
    fun shouldProperlyDeselectPreviouslySelectedButton() {
        val oldButton = target.addOption("foo", "bar") as DefaultRadioButton
        val newButton = target.addOption("baz", "qux") as DefaultRadioButton

        oldButton.inputEmitted(MouseAction(MouseActionType.MOUSE_RELEASED, 1, POSITION))
        newButton.inputEmitted(MouseAction(MouseActionType.MOUSE_RELEASED, 1, POSITION))
        // this is necessary because ComponentContainer handles this but it is not present in this test
        newButton.mouseReleased(MouseAction(MouseActionType.MOUSE_RELEASED, 1, POSITION))

        assertThat(oldButton.isSelected()).isFalse()
        assertThat(newButton.isSelected()).isTrue()
    }

    @Test
    fun shouldProperlyNotifyListenersWhenAButtonIsClicked() {
        val button = target.addOption("foo", "bar") as DefaultRadioButton

        val selected = AtomicBoolean(false)
        target.onSelection {
            selected.set(true)
        }

        button.inputEmitted(MouseAction(MouseActionType.MOUSE_RELEASED, 1, POSITION))

        assertThat(selected.get()).isTrue()
    }

    companion object {
        val THEME = ColorThemeResource.ADRIFT_IN_DREAMS.getTheme()
        const val TEXT = "Button text"
        val FONT = BuiltInCP437TilesetResource.WANDERLUST_16X16
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
