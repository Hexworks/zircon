package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.resource.BuiltInTrueTypeFontResource
import org.hexworks.zircon.api.tileset.lookup.CP437TileMetadataLoader

object ComponentsExample {

    private val theme = ColorThemes.hexworks()
    private val tileset = CP437TilesetResources.rogueYun16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(tileset)
                .defaultSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel()
                .wrapWithBox()
                .wrapWithShadow()
                .size(Sizes.create(20, 20))
                .position(Positions.create(30, 1))
                .title("Title")


        screen.addComponent(Components.button().text("Button").position(Positions.create(1, 1)))
        screen.addComponent(Components.label().text("Label").position(Positions.create(1, 3)))
        screen.addComponent(Components.header().text("Header").position(Positions.create(1, 5)))
        screen.addComponent(Components.checkBox().text("Check").position(Positions.create(1, 7)))
        screen.addComponent(Components.textArea().size(Sizes.create(20, 10)).position(Positions.create(1, 9)).text("Edit me"))
        screen.addComponent(panel)
        screen.display()

        screen.applyColorTheme(theme)
    }

}
