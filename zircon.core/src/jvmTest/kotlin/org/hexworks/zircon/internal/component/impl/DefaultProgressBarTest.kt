package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.color.TileColor.Companion.transparent
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.ProgressBar
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.DefaultProgressBarRenderer
import org.junit.Before
import org.junit.Test

@Suppress("UNCHECKED_CAST")
class DefaultProgressBarTest : ComponentImplementationTest<DefaultProgressBar>() {


    override lateinit var target: DefaultProgressBar
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
        rendererStub = ComponentRendererStub(DefaultProgressBarRenderer())
        drawWindow = tileGraphics {
            size = SIZE_10X1
        }.toDrawWindow()
        target = DefaultProgressBar(
            componentMetadata = ComponentMetadata(
                relativePosition = POSITION_2_3,
                size = SIZE_10X1,
                componentStyleSetProperty = COMPONENT_STYLES.toProperty(),
                tilesetProperty = TILESET_REX_PAINT_20X20.toProperty()
            ),
            renderingStrategy = DefaultComponentRenderingStrategy(
                decorationRenderers = listOf(),
                componentRenderer = rendererStub as ComponentRenderer<ProgressBar>
            ),
            numberOfSteps = STEPS,
            range = RANGE,
            displayPercentValueOfProgress = false
        )
        rendererStub.render(drawWindow, ComponentRenderContext(target))
    }


    @Test
    fun shouldProperlyRenderProgressBar() {
        target.progress = PROGRESS_50_PERCENT
        val offset = target.contentOffset.x
        val css = target.currentStyle
        val invertedStyleSet = css
            .withBackgroundColor(css.foregroundColor)
            .withForegroundColor(css.backgroundColor)

        rendererStub.render(drawWindow, ComponentRenderContext(target))

        (0 until PROGRESS_BAR_SIZE_5).forEachIndexed { i, _ ->
            assertThat(drawWindow.getTileAtOrNull(Position.create(i + offset, 0)))
                .isEqualTo(
                    characterTile {
                        character = ' '
                        styleSet = invertedStyleSet
                    }
                )
        }
    }

    @Test
    fun shouldProperlyCalculateProgressBarState() {
        target.progress = PROGRESS_50_PERCENT
        val state = target.getProgressBarState()
        assertThat(state.currentProgressInPercent)
            .isEqualTo(EXPECTED_PROGRESS_50_PERCENT)
        assertThat(state.currentProgression)
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
