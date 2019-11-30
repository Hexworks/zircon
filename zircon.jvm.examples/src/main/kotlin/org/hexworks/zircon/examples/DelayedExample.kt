package org.hexworks.zircon.examples


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.Delay

object DelayedExample {

    private val tileset = CP437TilesetResources.taffer20x20()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(50, 10))
                .withDebugMode(true)
                .build())

        val text = "This text is typed like on a typewriter"

        tileGrid.cursorPosition = Position.create(1, 1)
        text.forEachIndexed { index, c ->
            val delayTime = 250 + index * 250
            tileGrid.putTile(Tile.defaultTile()
                    .withBackgroundColor(TileColor.transparent())
                    .withForegroundColor(ColorThemes.nord().accentColor)
                    .withCharacter(c)
                    .withModifiers(Delay(delayTime.toLong())))
        }

    }

}












