package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.TextWrap

object LogAreaExample {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.rogueYun16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(tileset)
                .defaultSize(Sizes.create(80, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val logArea = Components.logArea()
                .size(Sizes.create(60, 20))
                .position(Positions.create(2, 2))
                .textWrap(TextWrap.WORD_WRAP)
                .build()

        logArea.addText("This is a log row")
        logArea.addNewRow()
        logArea.addText("This is a further log row with a modifier", setOf(Modifiers.blink()))
        logArea.addNewRow()
        logArea.addNewRow()
        logArea.addHyperLink("Click me, I am a Hyperlink", "IdOfHyperlink")
        logArea.addNewRow()
        logArea.addNewRow()
        logArea.addText("This is a long log row, which gets wrapped, since it is long")
        screen.addComponent(logArea)

        screen.display()
        screen.applyColorTheme(theme)
    }

}
