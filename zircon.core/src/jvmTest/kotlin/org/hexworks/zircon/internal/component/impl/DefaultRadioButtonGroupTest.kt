package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.RadioButtonGroup
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.Pass
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
                        relativePosition = POSITION,
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
        assertThat(target.focusGiven()).isEqualTo(Pass)
    }

    @Test
    fun shouldSelectChildButtonWhenClicked() {
        target.addOption("qux", "baz") as DefaultRadioButton
        val button = target.addOption("foo", "bar") as DefaultRadioButton

        button.activated()

        assertThat(button.isSelected).isTrue()
        assertThat(target.fetchSelectedOption().get()).isEqualTo("foo")
    }

    @Test
    fun shouldSelectChildButtonWhenSelected() {
        target.addOption("qux", "baz") as DefaultRadioButton
        val button = target.addOption("foo", "bar") as DefaultRadioButton

        button.isSelected = true

        assertThat(button.isSelected).isTrue()
        assertThat(target.fetchSelectedOption().get()).isEqualTo("foo")
    }

    @Test
    fun shouldProperlyDeselectPreviouslySelectedButton() {
        val oldButton = target.addOption("foo", "bar") as DefaultRadioButton
        val newButton = target.addOption("baz", "qux") as DefaultRadioButton

        newButton.activated()

        assertThat(oldButton.isSelected).isFalse()
        assertThat(newButton.isSelected).isTrue()
    }

    @Test
    fun shouldProperlyNotifyListenersWhenAButtonIsActivated() {
        val button = target.addOption("foo", "bar") as DefaultRadioButton

        val selected = AtomicBoolean(false)
        target.onSelection {
            selected.set(true)
        }

        button.activated()

        assertThat(selected.get()).isTrue()
    }

    @Test
    fun shouldProperlySelectButtonWhenSelectIsCalledOnButton() {
        val key = "foo"
        val btn = target.addOption(key, "bar")
        btn.isSelected = true

        assertThat(btn.isSelected).isTrue()
        assertThat(target.fetchSelectedOption().get()).isEqualTo(key)
    }

    @Test
    fun shouldProperlyClearSelectionWhenAnItemIsSelected() {
        val key = "foo"
        val btn = target.addOption(key, "bar")
        btn.isSelected = true

        target.clearSelection()

        assertThat(btn.isSelected).isFalse()
        assertThat(target.fetchSelectedOption().isPresent).isFalse()
    }


    companion object {
        const val TEXT = "Button text"
        val POSITION = Position.create(4, 5)
        val SIZE = Size.create(10, 20)
        val MOUSE_RELEASED = MouseEvent(MouseEventType.MOUSE_RELEASED, 1, POSITION)
    }
}
