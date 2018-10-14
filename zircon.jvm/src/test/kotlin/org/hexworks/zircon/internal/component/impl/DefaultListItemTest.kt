package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.DefaultListItemRenderer
import org.junit.Before
import org.junit.Test

class DefaultListItemTest : ComponentImplementationTest<DefaultListItem>() {

    override lateinit var target: DefaultListItem

    override val expectedComponentStyles: ComponentStyleSet
        get() = ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(DEFAULT_THEME.secondaryForegroundColor)
                        .withBackgroundColor(TileColor.transparent())
                        .build())
                .build()

    @Before
    override fun setUp() {
        rendererStub = ComponentRendererStub(DefaultListItemRenderer())
        target = DefaultListItem(
                componentMetadata = ComponentMetadata(
                        size = SIZE_3_4,
                        position = POSITION_2_3,
                        componentStyleSet = COMPONENT_STYLES,
                        tileset = TILESET_REX_PAINT_20X20),
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = listOf(),
                        componentRenderer = rendererStub),
                text = TEXT)
    }

    @Test
    fun shouldSetTextProperly() {
        assertThat(target.text).isEqualTo(TEXT)
    }

    @Test
    fun shouldNotAcceptFocus() {
        assertThat(target.acceptsFocus()).isFalse()
    }

    @Test
    fun shouldNotAcceptFocusWhenGiven() {
        assertThat(target.giveFocus()).isFalse()
    }

    companion object {

        const val TEXT = "FOO"
    }

}
