package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.logArea
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.paragraph
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import kotlin.random.Random
import kotlin.time.TimeSource

private val TEXTS = listOf(
    "Locating the required gigapixels to render...",
    "Spinning up the hamster...",
    "Shovelling coal into the server...",
    "Programming the flux capacitor",
    "640K ought to be enough for anybody",
    "The architects are still drafting",
    "The bits are breeding",
    "We're building the buildings as fast as we can",
    "Would you prefer chicken, steak, or tofu?",
    "Pay no attention to the man behind the curtain",
    "A few bits tried to escape, but we caught them",
    "Checking the gravitational constant in your locale",
    "Go ahead -- hold your breath",
    "Hum something loud while others stare",
    "You're not in Kansas any more",
    "The server is powered by a lemon and two electrodes",
    "We're testing your patience",
    "Don't think of purple hippos",
    "Follow the white rabbit",
    "Why don't you order a sandwich?",
    "The bits are flowing slowly today",
    "Dig on the 'X' for buried treasure... ARRR!",
)

/** A log area that keeps receiving rows at random intervals and scrolls as it fills. */
public val scrollingLogAreaExample: Example = Example("Scrolling log") { context ->
    val root = panel { preferredSize = context.contentSize }
    val logArea = logArea {
        decorations { +box(title = "Log") }
        preferredSize = Size(60, 25)
    }
    root.addComponent(logArea)
    val random = Random(1)
    var added = 0
    var nextAt = TimeSource.Monotonic.markNow()
    var wait = 0L
    ExampleView(root, onFrame = {
        if (added <= 40 && nextAt.elapsedNow().inWholeMilliseconds >= wait) {
            logArea.addRow(paragraph { +TEXTS[random.nextInt(TEXTS.size)]; preferredSize = Size(58, 1) })
            added++
            wait = random.nextInt(500).toLong()
            nextAt = TimeSource.Monotonic.markNow()
        }
    })
}
