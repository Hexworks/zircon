package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.component.Slider
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.uievent.MouseEvent

class DefaultVerticalSlider(componentMetadata: ComponentMetadata,
                            renderingStrategy: ComponentRenderingStrategy<Slider>,
                            range: Int,
                            numberOfSteps: Int
) : DefaultSlider(
        componentMetadata = componentMetadata,
        renderingStrategy = renderingStrategy,
        range = range,
        numberOfSteps = numberOfSteps) {

    override fun getMousePosition(event: MouseEvent): Int {
       return event.position.minus(absolutePosition + contentPosition).y
    }
}