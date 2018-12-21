package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.ComponentState.*
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
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
                        .withForegroundColor(DEFAULT_THEME.primaryBackgroundColor)
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
                        position = POSITION_2_3,
                        componentStyleSet = COMPONENT_STYLES,
                        tileset = TILESET_REX_PAINT_20X20),
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = listOf(),
                        componentRenderer = rendererStub),
                initialText = DefaultCheckBoxTest.TEXT)
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
        val result = target.giveFocus()

        assertThat(result).isTrue()
        assertThat(target.componentStyleSet.currentState()).isEqualTo(FOCUSED)
    }

    @Test
    fun shouldProperlyTakeFocus() {
        target.takeFocus()

        assertThat(target.componentStyleSet.currentState()).isEqualTo(DEFAULT)
    }

    @Test
    fun shouldProperlySelect() {
        target.select()

        assertThat(getButtonChar()).isEqualTo('O')
        assertThat(target.isSelected()).isTrue()
        assertThat(target.componentStyleSet.currentState()).isEqualTo(MOUSE_OVER)
    }

    @Test
    fun shouldProperlyRemoveSelection() {
        target.select()
        target.removeSelection()

        assertThat(getButtonChar()).isEqualTo(' ')
        assertThat(target.isSelected()).isFalse()
        assertThat(target.componentStyleSet.currentState()).isEqualTo(DEFAULT)
    }

    private fun getButtonChar() = target.graphics.getTileAt(Position.create(1, 0))
            .get().asCharacterTile().get().character

    companion object {
        val SIZE_20X1 = Size.create(20, 1)
        const val TEXT = "Button text"
    }
}
