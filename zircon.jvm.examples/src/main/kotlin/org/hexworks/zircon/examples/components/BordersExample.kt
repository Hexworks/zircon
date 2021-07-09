package org.hexworks.zircon.examples.components


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.Border
import org.hexworks.zircon.api.modifier.BorderPosition
import org.hexworks.zircon.api.modifier.BorderType

object BordersExample {

    private val tileset = CP437TilesetResources.hack64x64()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(
            AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(10, 5))
                .build()
        )

        val tiles = listOf(
            Tile.defaultTile()
                .withBackgroundColor(ANSITileColor.GREEN)
                .withCharacter('a')
                .withModifiers(
                    Border.newBuilder()
                        .withBorderPositions(BorderPosition.TOP)
                        .withBorderType(BorderType.DOTTED)
                        .withBorderColor(TileColor.fromString("#ffaadd"))
                        .withBorderWidth(5)
                        .build()
                ),
            Tile.defaultTile()
                .withBackgroundColor(ANSITileColor.BLUE)
                .withCharacter('b')
                .withModifiers(
                    Border.newBuilder()
                        .withBorderPositions(BorderPosition.RIGHT)
                        .withBorderType(BorderType.SOLID)
                        .withBorderColor(TileColor.fromString("#caacaa"))
                        .withBorderWidth(10)
                        .build()
                ),
            Tile.defaultTile()
                .withBackgroundColor(ANSITileColor.GRAY)
                .withCharacter('c')
                .withModifiers(
                    Border.newBuilder()
                        .withBorderPositions(BorderPosition.BOTTOM)
                        .withBorderType(BorderType.DASHED)
                        .withBorderColor(TileColor.fromString("#caacaa"))
                        .withBorderWidth(15)
                        .build()
                ),
            Tile.defaultTile()
                .withBackgroundColor(ANSITileColor.RED)
                .withCharacter('d')
                .withModifiers(
                    Border.newBuilder()
                        .withBorderPositions(BorderPosition.LEFT)
                        .withBorderType(BorderType.SOLID)
                        .withBorderColor(TileColor.create(80, 80, 80, 80))
                        .withBorderWidth(20)
                        .build()
                ),
            Tile.defaultTile()
                .withBackgroundColor(ANSITileColor.RED)
                .withCharacter('e')
                .withModifiers(
                    Border.newBuilder()
                        .withBorderPositions(BorderPosition.TOP)
                        .withBorderColor(TileColor.create(0, 0, 0, 20))
                        .withBorderWidth(30)
                        .build(),
                    Border.newBuilder()
                        .withBorderPositions(BorderPosition.TOP)
                        .withBorderColor(TileColor.create(0, 0, 0, 20))
                        .withBorderWidth(20)
                        .build(),
                    Border.newBuilder()
                        .withBorderPositions(BorderPosition.TOP)
                        .withBorderColor(TileColor.create(0, 0, 0, 20))
                        .withBorderWidth(10)
                        .build()
                )
        )

        repeat(tiles.size) {
            tileGrid.draw(tiles[it], Position.create(1 + it, 1))
        }
    }

}
