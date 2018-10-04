package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.modifier.Delay
import org.hexworks.zircon.api.resource.ColorThemeResource

object DelayedExample {

    private val tileset = CP437TilesetResources.taffer20x20()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(tileset)
                .defaultSize(Sizes.create(50, 10))
                .debugMode(true)
                .build())

        val text = "This text is typed like on a typewriter"

        tileGrid.putCursorAt(Positions.create(1, 1))
        text.forEachIndexed { index, c ->
            val delayTime = 250 + index * 250
            tileGrid.putTile(Tiles.defaultTile()
                    .withBackgroundColor(TileColor.transparent())
                    .withForegroundColor(ColorThemeResource.NORD.getTheme().accentColor)
                    .withCharacter(c)
                    .withModifiers(Delay(delayTime.toLong())))
        }

    }

}












