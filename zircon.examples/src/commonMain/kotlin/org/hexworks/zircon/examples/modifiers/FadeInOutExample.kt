package org.hexworks.zircon.examples.modifiers

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.modifier.FadeInOut
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** Text fading in, staying for a few seconds, then fading out. Restarts when shown again. */
public val fadeInOutExample: Example = Example("Fade in+out") { context ->
    val layer = context.contentLayer()
    ExampleView(
        content = context.emptyContent(),
        layers = listOf(layer),
        onShown = {
            layer.clear()
            layer.drawText("This text fades in and then after a few seconds out", Position(1, 1), ColorThemes.nord().accentColor) {
                FadeInOut()
            }
        },
    )
}
