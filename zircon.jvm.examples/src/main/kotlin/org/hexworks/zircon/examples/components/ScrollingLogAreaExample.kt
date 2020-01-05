package org.hexworks.zircon.examples.components


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.screen.Screen
import java.util.*

// TODO: this can get into a deadlock!
// TODO: it happens because the renderer tries to fetch the layers while
// TODO: we try to add a paragraph and they are waiting for each other to
// TODO: unlock
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
        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(70, 30))
                .build())

        val screen = Screen.create(tileGrid)

        val logArea = Components.logArea()
                .withDecorations(box(title = "Log"))
                .withSize(Size.create(60, 25))
                .build()
        screen.addComponent(logArea)

        screen.display()
        screen.theme = theme

        val random = Random()

        (0..40).forEach { _ ->
            Thread.sleep(random.nextInt(500).toLong())
            logArea.addParagraph(texts[random.nextInt(texts.size)], withNewLine = false, withTypingEffectSpeedInMs = 100)
        }
    }

}
