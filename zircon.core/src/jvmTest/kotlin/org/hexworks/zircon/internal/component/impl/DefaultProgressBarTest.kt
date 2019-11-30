package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.ProgressBar
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.ComponentState.DEFAULT
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
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
        target.progress = PROGRESS
        val surface = target.graphics
        val offset = target.contentOffset.x
        val styleSet = target.componentStyleSet.fetchStyleFor(DEFAULT)
        val invertedStyleSet = styleSet
                .withBackgroundColor(styleSet.foregroundColor)
                .withForegroundColor(styleSet.backgroundColor)

        (0 until EXPECTED_PROGRESSBAR_SIZE).forEachIndexed { i, _ ->
            assertThat(surface.getTileAt(Position.create(i + offset, 0)).get())
                    .isEqualTo(TileBuilder.newBuilder()
                            .withCharacter(' ')
                            .withStyleSet(invertedStyleSet)
                            .build())
        }
    }

    @Test
    fun shouldProperlyCalculateProgressBarState() {
        target.progress = PROGRESS
        val state = target.getProgressBarState()
        assertThat(state.currentProgressInPercent)
                .isEqualTo(EXPECTED_CURRENT_PROGRESS_IN_PERCENT)
        assertThat(state.width)
                .isEqualTo(EXPECTED_PROGRESSBAR_SIZE)
    }

    companion object {
        val SIZE_10X1 = Size.create(10, 1)
        const val RANGE = 100
        const val STEPS = 10
        const val PROGRESS = 50.0
        const val EXPECTED_PROGRESSBAR_SIZE = 5
        const val EXPECTED_CURRENT_PROGRESS_IN_PERCENT = 50
    }
}
