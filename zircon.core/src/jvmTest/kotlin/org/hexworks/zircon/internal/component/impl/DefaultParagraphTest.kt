package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.color.TileColor.Companion.transparent
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Paragraph
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.DefaultParagraphRenderer
import org.junit.Before
import org.junit.Test

@Suppress("UNCHECKED_CAST")
class DefaultParagraphTest : ComponentImplementationTest<DefaultParagraph>() {

    override lateinit var target: DefaultParagraph
    override lateinit var drawWindow: DrawWindow

    override val expectedComponentStyles: ComponentStyleSet
        get() = componentStyleSet {
            defaultStyle = styleSet {
                foregroundColor = DEFAULT_THEME.secondaryForegroundColor
                backgroundColor = transparent()
            }
        }

    @Before
    override fun setUp() {
        rendererStub = ComponentRendererStub(DefaultParagraphRenderer())
        drawWindow = tileGraphics {
            size = SIZE
        }.toDrawWindow()
        target = DefaultParagraph(
            componentMetadata = ComponentMetadata(
                size = SIZE,
                relativePosition = POSITION,
                componentStyleSetProperty = COMPONENT_STYLES.toProperty(),
                tilesetProperty = TILESET_REX_PAINT_20X20.toProperty()
            ),
            renderingStrategy = DefaultComponentRenderingStrategy(
                decorationRenderers = listOf(),
                componentRenderer = rendererStub as ComponentRenderer<Paragraph>
            ),
            initialText = TEXT
        )
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

        const val TEXT = "TEXT"
        val SIZE = Size.create(5, 6)
        val POSITION = Position.create(2, 3)
    }

}
