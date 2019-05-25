package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.component.ComponentAlignment.CENTER
import org.hexworks.zircon.api.extensions.box

object HBoxVBoxExample {

    private val theme = ColorThemes.nord()
    private val tileset = CP437TilesetResources.rexPaint20x20()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val contentWidth = 46

        val table = Components.vbox()
                .withSize(contentWidth + 2, 20)
                .withAlignmentWithin(screen, CENTER)
                .withDecorations(box(title = "Crew"))
                .build()

        val headerRow = Components.hbox()
                .withSize(contentWidth, 1)
                .build().apply {
                    addComponent(Components.header().withText("#").withSize(4, 1))
                    addComponent(Components.header().withText("First Name").withSize(12, 1))
                    addComponent(Components.header().withText("Last Name").withSize(12, 1))
                    addComponent(Components.header().withText("Job").withSize(12, 1))

                }

        val samLawrey = Components.hbox()
                .withSize(contentWidth, 1)
                .build().apply {
                    addComponent(Components.label().withText("0").withSize(4, 1))
                    addComponent(Components.label().withText("Sam").withSize(12, 1))
                    addComponent(Components.label().withText("Lawrey").withSize(12, 1))
                    addComponent(Components.label().withText("Stoneworker").withSize(12, 1))
                }

        val janeFisher = Components.hbox()
                .withSize(contentWidth, 1)
                .build().apply {
                    addComponent(Components.label().withText("1").withSize(4, 1))
                    addComponent(Components.label().withText("Jane").withSize(12, 1))
                    addComponent(Components.label().withText("Fisher").withSize(12, 1))
                    addComponent(Components.label().withText("Woodcutter").withSize(12, 1))
                }

        val johnSmith = Components.hbox()
                .withSize(contentWidth, 1)
                .build().apply {
                    addComponent(Components.label().withText("2").withSize(4, 1))
                    addComponent(Components.label().withText("John").withSize(12, 1))
                    addComponent(Components.label().withText("Smith").withSize(12, 1))
                    addComponent(Components.label().withText("Mason").withSize(12, 1))
                }

        val steveThrush = Components.hbox()
                .withSize(contentWidth, 1)
                .build().apply {
                    addComponent(Components.label().withText("3").withSize(4, 1))
                    addComponent(Components.label().withText("Steve").withSize(12, 1))
                    addComponent(Components.label().withText("Thrush").withSize(12, 1))
                    addComponent(Components.label().withText("Farmer").withSize(12, 1))
                }

        table.addComponent(headerRow)
        table.addComponent(samLawrey)
        table.addComponent(janeFisher)
        table.addComponent(johnSmith)
        table.addComponent(steveThrush)

        screen.addComponent(table)

        screen.display()
        screen.applyColorTheme(theme)
    }

}
