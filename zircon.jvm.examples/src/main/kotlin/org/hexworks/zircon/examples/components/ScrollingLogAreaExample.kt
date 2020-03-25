package org.hexworks.zircon.examples.components


import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.platform.util.SystemUtils
import java.util.*

// TODO: The previous version of this example could get into a deaclock!
// TODO: It happened because the renderer tried to fetch the layers while
// TODO: we tried to add a paragraph from another thread and they were waiting for each other. See:
// TODO: https://cdn.discordapp.com/attachments/390913999505719308/663473133826736138/unknown.png
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
        val app = SwingApplications.startApplication(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(70, 30))
                .build())

        val screen = app.tileGrid.toScreen()

        val logArea = Components.logArea()
                .withDecorations(box(title = "Log"))
                .withSize(screen.size)
                .build()
        screen.addComponent(logArea)

        screen.display()
        screen.theme = theme

        val random = Random()

        val interval = 500
        val maxCount = 50
        val finished = false.toProperty()
        var time = SystemUtils.getCurrentTimeMs()
        var count = 0
        app.beforeRender {
            val currentTime = SystemUtils.getCurrentTimeMs()
            if (time + interval < currentTime && count < maxCount) {
                logArea.addParagraph(texts[random.nextInt(texts.size)], withNewLine = false, withTypingEffectSpeedInMs = 100)
                time = currentTime
                count++
            }
            if (count == maxCount) finished.value = true
        }.disposeWhen(finished)
    }

}
