package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.component.Slider
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.internal.component.renderer.DefaultLabelRenderer
import org.hexworks.zircon.internal.component.renderer.HorizontalSliderGutterRenderer

class DefaultHorizontalSlider(componentMetadata: ComponentMetadata,
                              renderingStrategy: ComponentRenderingStrategy<Slider>,
                              range: Int,
                              numberOfSteps: Int,
                              isDecorated: Boolean
) : DefaultSlider(
        componentMetadata = componentMetadata,
        renderingStrategy = renderingStrategy,
        range = range,
        numberOfSteps = numberOfSteps,
        isDecorated = isDecorated) {

    override val decrementButtonChar = Symbols.TRIANGLE_LEFT_POINTING_BLACK
    override val incrementButtonChar = Symbols.TRIANGLE_RIGHT_POINTING_BLACK

    override val actualSize = when(isDecorated) {
        true -> Sizes.create(size.width - 2, size.height - 2)
        false -> size
    }

    override val labelSize = Sizes.create(range.toString().length, 1)

    override val root = Components.hbox()
            .withSize(actualSize)
            .withDecorations()
            .withSpacing(0)
            .build()

    override val labelRenderer = DefaultLabelRenderer()
    override val gutterRenderer = HorizontalSliderGutterRenderer(::getCurrentValueState)
    override val gutterPanelSize = Sizes.create(numberOfSteps + 1, 1)

    init {
        finishInit()
    }

    override fun getMousePosition(event: MouseEvent): Int {
        val clickPosition = event.position.minus(this.absolutePosition).x
        return when(isDecorated) {
            true -> clickPosition - 1
            false -> clickPosition
        }
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(Slider::class)
    }
}