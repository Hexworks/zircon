package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentAlignments.positionalAlignment
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.progressBar
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import kotlin.time.TimeSource

/** Two progress bars filling up over time, one of them showing the percentage. */
public val progressBarExample: Example = Example("Progress bar") { context ->
    val root = panel { preferredSize = context.contentSize }
    val panel = panel {
        decorations { +box(title = "Progress Bars on panel"); +shadow() }
        preferredSize = Size(30, 28)
        alignment = positionalAlignment(29, 1)
    }
    root.addComponent(panel)
    val progressBar = progressBar {
        range = 100
        numberOfSteps = 25
        position = Position(0, 5)
        decorations { +box() }
        preferredSize = Size(25, 3)
    }
    val progressBarWithPercentValue = progressBar {
        range = 100
        numberOfSteps = 23
        position = Position(0, 10)
        displayPercentValueOfProgress = true
        decorations { +box() }
    }
    panel.addComponent(progressBar)
    panel.addComponent(progressBarWithPercentValue)
    progressBar.progress = 1.0
    progressBarWithPercentValue.progress = 1.0

    var count = 0
    var last = TimeSource.Monotonic.markNow()
    ExampleView(root, onFrame = {
        if (progressBar.progress < 100 && last.elapsedNow().inWholeMilliseconds >= 200) {
            last = TimeSource.Monotonic.markNow()
            if (count % 2 == 0) progressBar.increment()
            progressBarWithPercentValue.increment()
            count++
        }
    })
}
