package org.hexworks.zircon.examples.modifiers

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.modifier.Delay
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** Each character appears after its own delay, like a typewriter. Restarts when shown again. */
public val delayExample: Example = Example("Delay") { context ->
    val layer = context.contentLayer()
    ExampleView(
        content = context.emptyContent(),
        layers = listOf(layer),
        onShown = {
            layer.clear()
            layer.drawText("This text is typed like on a typewriter", Position(1, 1), ColorThemes.nord().accentColor) { index ->
                Delay(250L + index * 250L)
            }
        },
    )
}
