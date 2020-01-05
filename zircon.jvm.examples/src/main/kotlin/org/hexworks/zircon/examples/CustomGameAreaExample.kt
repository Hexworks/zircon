package org.hexworks.zircon.examples


import org.hexworks.zircon.api.GameComponents
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.base.BaseGameArea
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.screen.Screen

object CustomGameAreaExample {

    class CustomGameArea(visibleSize: Size3D,
                         actualSize: Size3D) : BaseGameArea<Tile, Block<Tile>>(
            initialVisibleSize = visibleSize,
            initialActualSize = actualSize)

    @JvmStatic
    fun main(args: Array<String>) {

        val gameArea = CustomGameArea(VISIBLE_SIZE, ACTUAL_SIZE)

        makeCaves(gameArea)

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withSize(VISIBLE_SIZE.to2DSize())
                .enableBetaFeatures()
                .build())

        val screen = Screen.create(tileGrid)

        screen.addComponent(GameComponents.newGameComponentBuilder<Tile, Block<Tile>>()
                .withSize(VISIBLE_SIZE.to2DSize())
                .withGameArea(gameArea)
                .build())

        screen.display()

    }

    private fun makeCaves(gameArea: GameArea<Tile, Block<Tile>>, smoothTimes: Int = 8) {
        val width = gameArea.actualSize.xLength
        val height = gameArea.actualSize.yLength
        var tiles: MutableMap<Position, Tile> = mutableMapOf()
        gameArea.actualSize.to2DSize().fetchPositions().forEach { pos ->
            tiles[pos] = if (Math.random() < 0.5) FLOOR else WALL
        }
        val newTiles: MutableMap<Position, Tile> = mutableMapOf()
        for (time in 0 until smoothTimes) {

            for (x in 0 until width) {
                for (y in 0 until height) {
                    var floors = 0
                    var rocks = 0

                    for (ox in -1..1) {
                        for (oy in -1..1) {
                            if (x + ox < 0 || x + ox >= width || y + oy < 0
                                    || y + oy >= height)
                                continue

                            if (tiles[Position.create(x + ox, y + oy)] === FLOOR)
                                floors++
                            else
                                rocks++
                        }
                    }
                    newTiles[Position.create(x, y)] = if (floors >= rocks) FLOOR else WALL
                }
            }
            tiles = newTiles
        }
        tiles.forEach { pos, tile ->
            val pos3D = pos.to3DPosition(0)
            gameArea.setBlockAt(pos3D, Block.newBuilder<Tile>()
                    .withContent(tile)
                    .withEmptyTile(Tile.empty())
                    .build())
        }
    }

    private val FLOOR = Tile.newBuilder()
            .withCharacter(Symbols.INTERPUNCT)
            .withForegroundColor(ANSITileColor.YELLOW)
            .buildCharacterTile()

    private val WALL = Tile.newBuilder()
            .withCharacter('#')
            .withForegroundColor(TileColor.fromString("#999999"))
            .buildCharacterTile()

    private val VISIBLE_SIZE = Size3D.create(60, 30, 10)
    private val ACTUAL_SIZE = Size3D.create(100, 100, 100)


}
