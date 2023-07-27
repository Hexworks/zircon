package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.DefaultPanelRenderer
import org.junit.Before
import org.junit.Test

@Suppress("UNCHECKED_CAST")
class DefaultPanelTest : ComponentImplementationTest<DefaultPanel>() {

    override lateinit var target: DefaultPanel
    override lateinit var drawWindow: DrawWindow

    override val expectedComponentStyles: ComponentStyleSet
        get() = componentStyleSet {
            defaultStyle = styleSet {
                foregroundColor = DEFAULT_THEME.secondaryForegroundColor
                backgroundColor = DEFAULT_THEME.primaryBackgroundColor
            }
        }

    @Before
    override fun setUp() {
        componentStub = ComponentStub(Position.create(1, 1), Size.create(2, 2))
        rendererStub = ComponentRendererStub(DefaultPanelRenderer())
        drawWindow = tileGraphics {
            size = SIZE
        }.toDrawWindow()
        target = DefaultPanel(
            componentMetadata = ComponentMetadata(
                size = SIZE,
                relativePosition = POSITION,
                componentStyleSetProperty = COMPONENT_STYLES.toProperty(),
                tilesetProperty = TILESET_REX_PAINT_20X20.toProperty()
            ),
            renderingStrategy = DefaultComponentRenderingStrategy(
                decorationRenderers = listOf(),
                componentRenderer = rendererStub as ComponentRenderer<Panel>
            ),
            initialTitle = TITLE
        )
    }

    @Test
    fun shouldHaveProperTitle() {
        assertThat(target.title).isEqualTo(TITLE)
    }

    companion object {
        const val TITLE = "TITLE"
        val SIZE = Size.create(5, 6)
        val POSITION = Position.create(2, 3)
    }
}
