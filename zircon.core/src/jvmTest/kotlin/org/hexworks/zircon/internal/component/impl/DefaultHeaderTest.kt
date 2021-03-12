package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.DrawSurfaces
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Header
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.convertCharacterTilesToString
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.DefaultHeaderRenderer
import org.hexworks.zircon.padLineEnd
import org.junit.Before
import org.junit.Test

@Suppress("UNCHECKED_CAST")
class DefaultHeaderTest : ComponentImplementationTest<DefaultHeader>() {

    override lateinit var target: DefaultHeader
    override lateinit var graphics: TileGraphics

    override val expectedComponentStyles: ComponentStyleSet
        get() = ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(DEFAULT_THEME.primaryForegroundColor)
                        .withBackgroundColor(TileColor.transparent())
                        .build())
                .build()

    @Before
    override fun setUp() {
        rendererStub = ComponentRendererStub(DefaultHeaderRenderer())
        graphics = DrawSurfaces.tileGraphicsBuilder().withSize(SIZE_10_4).build()
        target = DefaultHeader(
                componentMetadata = ComponentMetadata(
                        size = SIZE_10_4,
                        relativePosition = POSITION_2_3,
                        componentStyleSet = COMPONENT_STYLES,
                        tileset = TILESET_REX_PAINT_20X20),
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = listOf(),
                        componentRenderer = rendererStub as ComponentRenderer<Header>),
                initialText = TEXT)
    }

    @Test
    fun shouldProperlyReturnText() {
        assertThat(target.text).isEqualTo(TEXT)
    }

    @Test
    fun shouldNotAcceptFocus() {
        assertThat(target.acceptsFocus()).isFalse()
    }

    @Test
    fun shouldNotAcceptGivenFocus() {
        assertThat(target.focusGiven()).isEqualTo(Pass)
    }

    @Test
    fun shouldGenerateProperTiles() {
        rendererStub.clear()
        rendererStub.render(graphics, ComponentRenderContext(target))
        // Careful, the last line has a trailing space
        assertThat(graphics.convertCharacterTilesToString()).isEqualTo("""
            Button tex
            t
             
             
        """.trimIndent().padLineEnd(SIZE_10_4.width))
    }

    companion object {
        const val TEXT = "Button text"
    }
}
