package org.hexworks.zircon.examples.modifiers

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.modifier.FadeIn
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** Text fading in from black, in a few and in many steps. Restarts when shown again. */
public val fadeInExample: Example = Example("Fade in") { context ->
    val layer = context.contentLayer()
    val color = ColorThemes.nord().accentColor
    ExampleView(
        content = context.emptyContent(),
        layers = listOf(layer),
        onShown = {
            layer.clear()
            layer.drawText("This text fades in in 10 steps", Position(1, 1), color) { FadeIn(10, 2000) }
            layer.drawText("This text fades in in 40 steps", Position(1, 3), color) { FadeIn(40, 2000) }
        },
    )
}
