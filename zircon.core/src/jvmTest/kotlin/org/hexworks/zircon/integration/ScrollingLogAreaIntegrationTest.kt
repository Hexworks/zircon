package org.hexworks.zircon.integration

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.screen.Screen
import java.util.*

@Suppress("unused")
class ScrollingLogAreaIntegrationTest : ComponentIntegrationTestBase(size = Size.create(70, 30)) {

    var delayMs: Int = 1

    private val texts = arrayOf(
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
        "Dig on the 'X' for buried treasure... ARRR!"
    )

    override fun buildScreenContent(screen: Screen) {
        val panel = Components.panel()
            .withDecorations(box(title = "Log"))
            .withPreferredSize(60, 25)
            .build()

        screen.addComponent(panel)
        val logArea = Components.logArea()
            .withPreferredSize(Size.create(58, 23))
            .build()
        panel.addComponent(logArea)

        screen.display()
        screen.theme = theme

        val random = Random()

        (0..40).forEach { _ ->
            Thread.sleep(random.nextInt(delayMs).toLong())
            logArea.addParagraph(texts[random.nextInt(texts.size)], withNewLine = false)
        }
    }

}
