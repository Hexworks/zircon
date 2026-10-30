package org.hexworks.zircon.examples.modifiers

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.modifier.FadeOut
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** Text fading out to nothing. Restarts when shown again. */
public val fadeOutExample: Example = Example("Fade out") { context ->
    val layer = context.contentLayer()
    ExampleView(
        content = context.emptyContent(),
        layers = listOf(layer),
        onShown = {
            layer.clear()
            layer.drawText("This text fades out", Position(1, 1), ColorThemes.nord().accentColor) { FadeOut(20, 2000) }
        },
    )
}
