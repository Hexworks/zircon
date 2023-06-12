package org.hexworks.zircon.integration

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.buildLogArea
import org.hexworks.zircon.api.builder.component.buildPanel
import org.hexworks.zircon.api.component.addParagraph
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.component.builder.base.withPreferredSize
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

        screen.display()
        screen.theme = theme

        val logArea = buildLogArea {
            withPreferredSize {
                width = 58
                height = 23
            }
        }

        screen.addComponent(buildPanel {
            decorations {
                +box(title = "Log")
            }
            withPreferredSize {
                width = 60
                height = 25
            }

            children {
                +logArea
            }

        })

        val random = Random()

        (0..40).forEach { _ ->
            Thread.sleep(random.nextInt(delayMs).toLong())
            logArea.addParagraph(texts[random.nextInt(texts.size)])
        }
    }

}
