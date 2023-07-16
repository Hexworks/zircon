package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
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
        get() = ComponentStyleSetBuilder.newBuilder()
            .withDefaultStyle(
                StyleSetBuilder.newBuilder()
                    .withForegroundColor(DEFAULT_THEME.secondaryForegroundColor)
                    .withBackgroundColor(DEFAULT_THEME.primaryBackgroundColor)
                    .build()
            )
            .build()

    @Before
    override fun setUp() {
        componentStub = ComponentStub(Position.create(1, 1), Size.create(2, 2))
        rendererStub = ComponentRendererStub(DefaultPanelRenderer())
        drawWindow = DrawSurfaces.tileGraphicsBuilder().withSize(SIZE).build().toDrawWindow(
            Rect.create(size = SIZE)
        )
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
