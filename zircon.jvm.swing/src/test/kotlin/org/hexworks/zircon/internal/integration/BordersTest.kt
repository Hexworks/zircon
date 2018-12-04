package org.hexworks.zircon.internal.integration

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.modifier.BorderPosition
import org.hexworks.zircon.api.modifier.BorderType

object BordersTest {

    private val tileset = CP437TilesetResources.hack64x64()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(10, 5))
                .build())

        val tiles = listOf(
                Tiles.defaultTile()
                        .withBackgroundColor(ANSITileColor.GREEN)
                        .withCharacter('a')
                        .withModifiers(Borders.newBuilder()
                                .withBorderPositions(BorderPosition.TOP)
                                .withBorderType(BorderType.DOTTED)
                                .withBorderColor(TileColor.fromString("#ffaadd"))
                                .withBorderWidth(5)
                                .build()),
                Tiles.defaultTile()
                        .withBackgroundColor(ANSITileColor.BLUE)
                        .withCharacter('b')
                        .withModifiers(Borders.newBuilder()
                                .withBorderPositions(BorderPosition.RIGHT)
                                .withBorderType(BorderType.SOLID)
                                .withBorderColor(TileColor.fromString("#caacaa"))
                                .withBorderWidth(10)
                                .build()),
                Tiles.defaultTile()
                        .withBackgroundColor(ANSITileColor.GRAY)
                        .withCharacter('c')
                        .withModifiers(Borders.newBuilder()
                                .withBorderPositions(BorderPosition.BOTTOM)
                                .withBorderType(BorderType.DASHED)
                                .withBorderColor(TileColor.fromString("#caacaa"))
                                .withBorderWidth(15)
                                .build()),
                Tiles.defaultTile()
                        .withBackgroundColor(ANSITileColor.RED)
                        .withCharacter('d')
                        .withModifiers(Borders.newBuilder()
                                .withBorderPositions(BorderPosition.LEFT)
                                .withBorderType(BorderType.SOLID)
                                .withBorderColor(TileColor.create(80, 80, 80, 80))
                                .withBorderWidth(20)
                                .build()),
                Tiles.defaultTile()
                        .withBackgroundColor(ANSITileColor.RED)
                        .withCharacter('e')
                        .withModifiers(
                                Borders.newBuilder()
                                        .withBorderPositions(BorderPosition.TOP)
                                        .withBorderColor(TileColor.create(0, 0, 0, 20))
                                        .withBorderWidth(30)
                                        .build(),
                                Borders.newBuilder()
                                        .withBorderPositions(BorderPosition.TOP)
                                        .withBorderColor(TileColor.create(0, 0, 0, 20))
                                        .withBorderWidth(20)
                                        .build(),
                                Borders.newBuilder()
                                        .withBorderPositions(BorderPosition.TOP)
                                        .withBorderColor(TileColor.create(0, 0, 0, 20))
                                        .withBorderWidth(10)
                                        .build())
        )

        repeat(tiles.size) {
            tileGrid.setTileAt(Positions.create(1 + it, 1), tiles[it])
        }
    }

}
