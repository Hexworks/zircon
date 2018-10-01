package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.*

object TextBoxesExample {

    private val theme = ColorThemes.oliveLeafTea()
    private val tileset = CP437TilesetResources.rogueYun16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(tileset)
                .defaultSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel()
                .wrapWithBox(true)
                .withSize(Sizes.create(28, 28))
                .withPosition(Positions.create(30, 1))
                .build()
        screen.addComponent(panel)

        screen.addComponent(Components.textBox()
                .withPosition(Positions.create(2, 2))
                .contentWidth(26)
                .header("Header!")
                .paragraph("This is a plain text box.")
                .newLine()
                .listItem("This is a list item")
                .listItem("And another list item"))
        panel.addComponent(Components.textBox()
                .withPosition(Positions.zero())
                .contentWidth(26)
                .header("Header!")
                .paragraph("This is a plain text box.")
                .newLine()
                .listItem("This is a list item")
                .listItem("And another list item"))

        screen.addComponent(Components.textBox()
                .withPosition(Positions.create(1, 14))
                .contentWidth(22)
                .header("Decorated!")
                .wrapWithBox(true)
                .wrapWithShadow(true)
                .paragraph("This is a paragraph which won't fit on one line."))
        panel.addComponent(Components.textBox()
                .withPosition(Positions.create(0, 12))
                .contentWidth(22)
                .wrapWithBox(true)
                .wrapWithShadow(true)
                .header("Decorated!")
                .paragraph("This is a paragraph which won't fit on one line."))

        screen.display()
        screen.applyColorTheme(theme)
    }

}
