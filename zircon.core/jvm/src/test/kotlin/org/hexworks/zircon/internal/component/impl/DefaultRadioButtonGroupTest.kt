package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.RadioButtonGroup
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.input.MouseActionType
import org.hexworks.zircon.api.kotlin.onSelection
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.component.renderer.DefaultRadioButtonGroupRenderer
import org.junit.Before
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean

@Suppress("MemberVisibilityCanBePrivate", "UNCHECKED_CAST")
class DefaultRadioButtonGroupTest : ComponentImplementationTest<DefaultRadioButtonGroup>() {

    override lateinit var target: DefaultRadioButtonGroup

    override val expectedComponentStyles: ComponentStyleSet
        get() = ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(DEFAULT_THEME.secondaryForegroundColor)
                        .withBackgroundColor(TileColor.transparent())
                        .build())
                .build()

    @Before
    override fun setUp() {
        rendererStub = ComponentRendererStub(DefaultRadioButtonGroupRenderer())
        target = DefaultRadioButtonGroup(
                componentMetadata = ComponentMetadata(
                        size = SIZE,
                        position = POSITION,
                        componentStyleSet = COMPONENT_STYLES,
                        tileset = TILESET_REX_PAINT_20X20),
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = listOf(),
                        componentRenderer = rendererStub as ComponentRenderer<RadioButtonGroup>))
    }

    @Test
    fun shouldNotAcceptFocus() {
        assertThat(target.acceptsFocus()).isFalse()
    }

    @Test
    fun shouldNotTakeGivenFocus() {
        assertThat(target.giveFocus(Maybe.empty())).isFalse()
    }

    @Test
    fun shouldSelectChildButtonWhenClicked() {
        target.addOption("qux", "baz") as DefaultRadioButton
        val button = target.addOption("foo", "bar") as DefaultRadioButton

        button.mouseReleased(MOUSE_RELEASED)

        assertThat(button.isSelected()).isTrue()
        assertThat(target.fetchSelectedOption().get()).isEqualTo("foo")
    }

    @Test
    fun shouldSelectChildButtonWhenSelected() {
        target.addOption("qux", "baz") as DefaultRadioButton
        val button = target.addOption("foo", "bar") as DefaultRadioButton

        button.select()

        assertThat(button.isSelected()).isTrue()
        assertThat(target.fetchSelectedOption().get()).isEqualTo("foo")
    }

    @Test
    fun shouldProperlyDeselectPreviouslySelectedButton() {
        val oldButton = target.addOption("foo", "bar") as DefaultRadioButton
        val newButton = target.addOption("baz", "qux") as DefaultRadioButton

        // this is necessary because ComponentContainer handles this but it is not present in this test
        newButton.mouseReleased(MOUSE_RELEASED)

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

        button.mouseReleased(MOUSE_RELEASED)

        assertThat(selected.get()).isTrue()
    }

    @Test
    fun shouldProperlySelectButtonWhenSelectIsCalledOnButton() {
        val key = "foo"
        val btn = target.addOption(key, "bar")
        btn.select()

        assertThat(btn.isSelected()).isTrue()
        assertThat(target.fetchSelectedOption().get()).isEqualTo(key)
    }

    @Test
    fun shouldProperlyClearSelectionWhenAnItemIsSelected() {
        val key = "foo"
        val btn = target.addOption(key, "bar")
        btn.select()

        target.clearSelection()

        assertThat(btn.isSelected()).isFalse()
        assertThat(target.fetchSelectedOption().isPresent).isFalse()
    }


    companion object {
        const val TEXT = "Button text"
        val POSITION = Position.create(4, 5)
        val SIZE = Size.create(10, 20)
        val MOUSE_RELEASED = MouseAction(MouseActionType.MOUSE_RELEASED, 1, POSITION)
    }
}
