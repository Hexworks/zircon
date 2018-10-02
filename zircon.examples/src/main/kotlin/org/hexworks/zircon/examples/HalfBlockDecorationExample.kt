package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.modifier.Crop
import org.hexworks.zircon.api.shape.FilledRectangleFactory
import org.hexworks.zircon.api.shape.LineFactory

object HalfBlockDecorationExample {

    private val theme = ColorThemes.gamebookers()
    private val tileset = CP437TilesetResources.rexPaint20x20()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultSize(Sizes.create(23, 24))
                .defaultTileset(tileset)
                .build())

        tileGrid.size.fetchPositions().forEach {
            tileGrid.setTileAt(it, Tiles.defaultTile()
                    .withBackgroundColor(theme.secondaryBackgroundColor()))
        }

        val filler = Tiles.defaultTile()
                .withBackgroundColor(theme.secondaryBackgroundColor())
        val rightHalf = Tiles.defaultTile()
                .withCharacter(Symbols.RIGHT_HALF_BLOCK)
                .withBackgroundColor(theme.secondaryBackgroundColor())
                .withForegroundColor(theme.primaryBackgroundColor())
        val leftHalf = rightHalf
                .withCharacter(Symbols.LEFT_HALF_BLOCK)
        val topHalf = rightHalf
                .withCharacter(Symbols.LOWER_HALF_BLOCK)
        val botHalf = rightHalf
                .withCharacter(Symbols.UPPER_HALF_BLOCK)

        LineFactory.buildLine(Positions.create(2, 1), Positions.create(6, 1))
                .positions().forEach {
                    tileGrid.setTileAt(it, topHalf)
                }
        LineFactory.buildLine(Positions.create(1, 2), Positions.create(1, 6))
                .positions().forEach {
                    tileGrid.setTileAt(it, rightHalf)
                }
        LineFactory.buildLine(Positions.create(7, 2), Positions.create(7, 6))
                .positions().forEach {
                    tileGrid.setTileAt(it, leftHalf)
                }
        LineFactory.buildLine(Positions.create(2, 7), Positions.create(6, 7))
                .positions().forEach {
                    tileGrid.setTileAt(it, botHalf)
                }
        FilledRectangleFactory.buildFilledRectangle(
                topLeft = Positions.create(2, 2),
                size = Sizes.create(5, 5))
                .positions().forEach {
                    tileGrid.setTileAt(it, filler)
                }

        val cropRight = Crop(
                x = tileset.width.div(2),
                y = 0,
                width = tileset.width.div(2),
                height = tileset.height)
        val cropLeft = Crop(
                x = 0,
                y = 0,
                width = tileset.width.div(2),
                height = tileset.height)

        listOf(Positions.create(1, 1) to topHalf
                        .withCharacter(Symbols.LOWER_HALF_BLOCK)
                        .withModifiers(cropRight),
                Positions.create(7, 1) to topHalf
                        .withCharacter(Symbols.LOWER_HALF_BLOCK)
                        .withModifiers(cropLeft),
                Positions.create(1, 7) to botHalf
                        .withCharacter(Symbols.UPPER_HALF_BLOCK)
                        .withModifiers(cropRight),
                Positions.create(7, 7) to topHalf
                        .withCharacter(Symbols.UPPER_HALF_BLOCK)
                        .withModifiers(cropLeft)).forEach { (pos, tile) ->
            tileGrid.setTileAt(
                    position = pos,
                    tile = tile)
        }


    }

}
