package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.api.component.Slider
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.uievent.*
import kotlin.math.roundToInt

public class DefaultVerticalSlider internal constructor(
    componentMetadata: ComponentMetadata,
    renderingStrategy: ComponentRenderingStrategy<Slider>,
    minValue: Int,
    maxValue: Int,
    numberOfSteps: Int
) : BaseSlider(
    componentMetadata = componentMetadata,
    renderer = renderingStrategy,
    minValue = minValue,
    maxValue = maxValue,
    numberOfSteps = numberOfSteps
) {

    override fun getMousePosition(event: MouseEvent): Int {
        return event.position.minus(position + contentOffset).y
    }

    override fun keyPressed(event: KeyboardEvent, phase: UIEventPhase): UIEventResponse = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            when (event.code) {
                KeyCode.UP -> {
                    incrementCurrentValue()
                    navigatedByKey()
                    Processed
                }
                KeyCode.DOWN -> {
                    decrementCurrentValue()
                    navigatedByKey()
                    Processed
                }
                KeyCode.RIGHT -> {
                    addToCurrentValue(valuePerStep.roundToInt())
                    navigatedByKey()
                    Processed
                }
                KeyCode.LEFT -> {
                    subtractToCurrentValue(valuePerStep.roundToInt())
                    navigatedByKey()
                    Processed
                }
                else -> Pass
            }
        } else Pass
    }
}
