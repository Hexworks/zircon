package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.ProgressBar
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.DefaultProgressBarRenderer
import org.junit.Before
import org.junit.Test

@Suppress("UNCHECKED_CAST")
class DefaultProgressBarTest : ComponentImplementationTest<DefaultProgressBar>() {


    override lateinit var target: DefaultProgressBar

    override val expectedComponentStyles: ComponentStyleSet
        get() = ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(DEFAULT_THEME.secondaryForegroundColor)
                        .withBackgroundColor(TileColor.transparent())
                        .build())
                .build()

    @Before
    override fun setUp() {
        rendererStub = ComponentRendererStub(DefaultProgressBarRenderer())
        target = DefaultProgressBar(
                componentMetadata = ComponentMetadata(
                        relativePosition = POSITION_2_3,
                        componentStyleSet = COMPONENT_STYLES,
                        size = SIZE_10X1,
                        tileset = TILESET_REX_PAINT_20X20),
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = listOf(),
                        componentRenderer = rendererStub as ComponentRenderer<ProgressBar>),
                numberOfSteps = STEPS,
                range = RANGE,
                displayPercentValueOfProgress = false
        )
    }


    @Test
    fun shouldProperlyRenderProgressBar() {
        target.progress = PROGRESS_50_PERCENT
        val surface = target.graphics
        val offset = target.contentOffset.x
        val css = target.currentStyle
        val invertedStyleSet = css
                .withBackgroundColor(css.foregroundColor)
                .withForegroundColor(css.backgroundColor)

        (0 until PROGRESS_BAR_SIZE_5).forEachIndexed { i, _ ->
            assertThat(surface.getTileAt(Position.create(i + offset, 0)).get())
                    .isEqualTo(TileBuilder.newBuilder()
                            .withCharacter(' ')
                            .withStyleSet(invertedStyleSet)
                            .build())
        }
    }

    @Test
    fun shouldProperlyCalculateProgressBarState() {
        target.progress = PROGRESS_50_PERCENT
        val state = target.getProgressBarState()
        assertThat(state.currentProgressInPercent)
                .isEqualTo(EXPECTED_PROGRESS_50_PERCENT)
        assertThat(state.width)
                .isEqualTo(PROGRESS_BAR_SIZE_5)
    }

    companion object {
        val SIZE_10X1 = Size.create(10, 1)
        const val RANGE = 100
        const val STEPS = 10
        const val PROGRESS_50_PERCENT = 50.0
        const val EXPECTED_PROGRESS_50_PERCENT = 50
        const val PROGRESS_BAR_SIZE_5 = 5
    }
}
