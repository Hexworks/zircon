package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.api.graphics.TextWrap
import org.hexworks.zircon.api.modifier.SimpleModifiers
import org.hexworks.zircon.internal.event.ZirconEvent

object LogAreaExample {

    private val theme = ColorThemes.linuxMintDark()
    private val tileset = CP437TilesetResources.rogueYun16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(tileset)
                .defaultSize(Sizes.create(80, 20))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel()
                .wrapWithBox(true)
                .size(Sizes.create(60, 15))
                .title("Log")
                .build()

        screen.addComponent(panel)
        val logArea = Components.logArea()
                .size(Sizes.create(50, 10))
                .position(Positions.create(0, 0))
                .textWrap(TextWrap.WORD_WRAP)
                .build()

        logArea.addText("This is a log row")
        logArea.addNewRows()
        logArea.addText("This is a further log row with a modifier", setOf(Modifiers.crossedOut()))
        logArea.addNewRows(2)
        logArea.addHyperLink("Click me, I am a Hyperlink", "IdOfHyperlink")
        logArea.addNewRows(2)
        logArea.addText("This is a long log row, which gets wrapped, since it is long")
        logArea.addNewRows(4)
        logArea.addText("ScrollTest")
        logArea.scrollDownBy(2  )

        panel.addComponent(logArea)

        //event handler if user has clicked on the hyper link
        EventBus.subscribe<ZirconEvent.TriggeredHyperLink> {
            logArea.addNewRows(2)
            logArea.addText("You clicked on Hyperlink with ID ${it.linkId}", setOf(SimpleModifiers.Blink))
            screen.display()
        }

        screen.display()
        screen.applyColorTheme(theme)

    }

}
