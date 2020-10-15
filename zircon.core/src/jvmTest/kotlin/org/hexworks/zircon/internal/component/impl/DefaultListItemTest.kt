package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.DrawSurfaces
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.ListItem
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.DefaultListItemRenderer
import org.junit.Before
import org.junit.Test

@Suppress("UNCHECKED_CAST")
class DefaultListItemTest : ComponentImplementationTest<DefaultListItem>() {

    override lateinit var target: DefaultListItem
    override lateinit var graphics: TileGraphics

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
        graphics = DrawSurfaces.tileGraphicsBuilder().withSize(SIZE_3_4).build()
        target = DefaultListItem(
                componentMetadata = ComponentMetadata(
                        size = SIZE_3_4,
                        relativePosition = POSITION_2_3,
                        componentStyleSet = COMPONENT_STYLES,
                        tileset = TILESET_REX_PAINT_20X20),
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = listOf(),
                        componentRenderer = rendererStub as ComponentRenderer<ListItem>),
                initialText = TEXT)
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
        assertThat(target.focusGiven()).isEqualTo(Pass)
    }

    companion object {

        const val TEXT = "FOO"
    }

}
