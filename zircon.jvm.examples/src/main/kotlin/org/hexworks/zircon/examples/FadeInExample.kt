package org.hexworks.zircon.examples


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Modifiers
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.FadeIn

object FadeInExample {

    private val tileset = CP437TilesetResources.taffer20x20()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(40, 10))
                .withDebugMode(true)
                .build())

        val text = "This text fades in with a glow"

        tileGrid.cursorPosition = Position.create(1, 1)
        text.forEach { c ->
            tileGrid.putTile(Tile.defaultTile()
                    .withBackgroundColor(TileColor.transparent())
                    .withForegroundColor(ColorThemes.nord().accentColor)
                    .withCharacter(c)
                    .withModifiers(Modifiers.fadeIn(10, 2000)))
        }

        val textWithoutGlow = "This text fades in without a glow"

        tileGrid.cursorPosition = Position.create(1, 3)
        textWithoutGlow.forEach { c ->
            tileGrid.putTile(Tile.defaultTile()
                    .withBackgroundColor(TileColor.transparent())
                    .withForegroundColor(ColorThemes.nord().accentColor)
                    .withCharacter(c)
                    .withModifiers(FadeIn(10, 2000, false)))
        }

    }

}












