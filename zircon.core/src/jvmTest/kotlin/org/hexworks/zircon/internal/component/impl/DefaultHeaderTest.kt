package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.color.TileColor.Companion.transparent
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Header
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.impl.DrawWindow
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
    override lateinit var drawWindow: DrawWindow

    override val expectedComponentStyles: ComponentStyleSet
        get() = componentStyleSet {
            defaultStyle = styleSet {
                foregroundColor = DEFAULT_THEME.primaryForegroundColor
                backgroundColor = transparent()
            }
        }

    @Before
    override fun setUp() {
        rendererStub = ComponentRendererStub(DefaultHeaderRenderer())
        drawWindow = tileGraphics {
            size = SIZE_10_4
        }.toDrawWindow()
        target = DefaultHeader(
            componentMetadata = ComponentMetadata(
                size = SIZE_10_4,
                relativePosition = POSITION_2_3,
                componentStyleSetProperty = COMPONENT_STYLES.toProperty(),
                tilesetProperty = TILESET_REX_PAINT_20X20.toProperty()
            ),
            renderingStrategy = DefaultComponentRenderingStrategy(
                decorationRenderers = listOf(),
                componentRenderer = rendererStub as ComponentRenderer<Header>
            ),
            textProperty = TEXT.toProperty()
        )
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
        rendererStub.render(drawWindow, ComponentRenderContext(target))
        // 💀 Careful, the last line has a trailing space
        assertThat(drawWindow.convertCharacterTilesToString()).isEqualTo(
            """
            Button
            text
             
             
        """.trimIndent().padLineEnd(SIZE_10_4.width)
        )
    }

    companion object {
        const val TEXT = "Button text"
    }
}
