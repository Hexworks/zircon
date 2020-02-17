package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.ComponentState.*
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.internal.component.renderer.DefaultRadioButtonRenderer
import org.junit.Before
import org.junit.Test

class DefaultRadioButtonTest : ComponentImplementationTest<DefaultRadioButton>() {

    override lateinit var target: DefaultRadioButton

    override val expectedComponentStyles: ComponentStyleSet
        get() = ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(DEFAULT_THEME.accentColor)
                        .withBackgroundColor(TileColor.transparent())
                        .build())
                .withMouseOverStyle(StyleSetBuilder.newBuilder()
                        .withBackgroundColor(DEFAULT_THEME.accentColor)
                        .build())
                .withFocusedStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(DEFAULT_THEME.secondaryBackgroundColor)
                        .withBackgroundColor(DEFAULT_THEME.accentColor)
                        .build())
                .withActiveStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(DEFAULT_THEME.secondaryForegroundColor)
                        .withBackgroundColor(DEFAULT_THEME.accentColor)
                        .build())
                .build()


    @Before
    override fun setUp() {
        rendererStub = ComponentRendererStub(DefaultRadioButtonRenderer())
        target = DefaultRadioButton(
                componentMetadata = ComponentMetadata(
                        size = SIZE_20X1,
                        relativePosition = POSITION_2_3,
                        componentStyleSet = COMPONENT_STYLES,
                        tileset = TILESET_REX_PAINT_20X20),
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = listOf(),
                        componentRenderer = rendererStub),
                initialText = DefaultCheckBoxTest.TEXT,
                key = "key")
    }

    @Test
    fun shouldProperlyAddRadioButtonText() {
        val surface = target.graphics
        val offset = 4
        TEXT.forEachIndexed { i, char ->
            assertThat(surface.getTileAt(Position.create(i + offset, 0)).get())
                    .isEqualTo(TileBuilder.newBuilder()
                            .withCharacter(char)
                            .withStyleSet(target.componentStyleSet.fetchStyleFor(DEFAULT))
                            .build())
        }
    }

    @Test
    fun shouldProperlyReturnText() {
        assertThat(target.text).isEqualTo(TEXT)
    }

    @Test
    fun shouldAcceptFocus() {
        assertThat(target.acceptsFocus()).isTrue()
    }

    @Test
    fun shouldProperlyGiveFocus() {
        val result = target.focusGiven()

        assertThat(result).isEqualTo(Processed)
        assertThat(target.componentState).isEqualTo(FOCUSED)
    }

    @Test
    fun shouldProperlyTakeFocus() {
        target.focusTaken()

        assertThat(target.componentState).isEqualTo(DEFAULT)
    }

    @Test
    fun shouldProperlySelect() {
        target.isSelected = true

        assertThat(getButtonChar()).isEqualTo('O')
        assertThat(target.isSelected).isTrue()
    }

    @Test
    fun shouldProperlyRemoveSelection() {
        target.isSelected = true
        target.isSelected = false

        assertThat(target.isSelected).isFalse()
        assertThat(getButtonChar()).isEqualTo(' ')
        assertThat(target.componentState).isEqualTo(DEFAULT)
    }

    private fun getButtonChar() = target.graphics.getTileAt(Position.create(1, 0))
            .get().asCharacterTile().get().character

    companion object {
        val SIZE_20X1 = Size.create(20, 1)
        const val TEXT = "Button text"
    }
}
