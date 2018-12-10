package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.*
import java.util.*

object ScrollingLogAreaExample {

    private val tileset = CP437TilesetResources.rogueYun16x16()
    private val theme = ColorThemes.nord()
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
            "Dig on the 'X' for buried treasure... ARRR!")

    @JvmStatic
    fun main(args: Array<String>) {
        val tileGrid = LibgdxApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(70, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel()
                .wrapWithBox(true)
                .withSize(Sizes.create(60, 25))
                .withTitle("Log")
                .build()

        screen.addComponent(panel)
        val logArea = Components.logArea()
                .withSize(Sizes.create(58, 23))
                .build()
        panel.addComponent(logArea)

        screen.display()
        screen.applyColorTheme(theme)

        val random = Random()

        (0..40).forEach { _ ->
            Thread.sleep(random.nextInt(500).toLong())
            logArea.addParagraph(texts[random.nextInt(texts.size)], withNewLine = false, withTypingEffectSpeedInMs = 100)
        }
    }

}
