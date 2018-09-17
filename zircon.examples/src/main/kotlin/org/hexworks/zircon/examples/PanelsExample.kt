package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.graphics.BoxType

object PanelsExample {

    private val theme = ColorThemes.gamebookers()
    private val tileset = CP437TilesetResources.rogueYun16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(tileset)
                .defaultSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        screen.addComponent(Components.panel()
                .wrapWithBox(true)
                .size(Sizes.create(18, 5))
                .position(Positions.create(1, 1)))

        screen.addComponent(Components.panel()
                .wrapWithShadow(true)
                .size(Sizes.create(18, 5))
                .position(Positions.create(1, 8)))

        screen.addComponent(Components.panel()
                .wrapWithShadow(true)
                .wrapWithBox(true)
                .size(Sizes.create(18, 5))
                .position(Positions.create(1, 15)))

        screen.addComponent(Components.panel()
                .wrapWithBox(true)
                .boxType(BoxType.DOUBLE)
                .size(Sizes.create(18, 5))
                .position(Positions.create(1, 22)))

        screen.addComponent(Components.panel()
                .wrapWithBox(true)
                .boxType(BoxType.BASIC)
                .size(Sizes.create(18, 5))
                .position(Positions.create(21, 1)))

        screen.addComponent(Components.panel()
                .wrapWithBox(true)
                .title("Title")
                .size(Sizes.create(18, 5))
                .position(Positions.create(21, 8)))

        screen.addComponent(Components.panel()

                .size(Sizes.create(18, 5))
                .position(Positions.create(21, 15)))

        screen.addComponent(Components.panel()
                .size(Sizes.create(18, 5))
                .position(Positions.create(21, 22)))

        screen.addComponent(Components.panel()
                .size(Sizes.create(18, 5))
                .position(Positions.create(41, 1)))

        screen.addComponent(Components.panel()
                .size(Sizes.create(18, 5))
                .position(Positions.create(41, 8)))

        screen.addComponent(Components.panel()
                .size(Sizes.create(18, 5))
                .position(Positions.create(41, 15)))

        screen.addComponent(Components.panel()
                .size(Sizes.create(18, 5))
                .position(Positions.create(41, 22)))

        screen.display()
        screen.applyColorTheme(theme)
    }

}
