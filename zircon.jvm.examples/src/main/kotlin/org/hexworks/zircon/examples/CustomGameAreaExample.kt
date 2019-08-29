package org.hexworks.zircon.examples

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.Blocks
import org.hexworks.zircon.api.GameComponents
import org.hexworks.zircon.api.Maybes
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.TileColors
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D
import org.hexworks.zircon.api.game.Cell3D
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.base.BaseGameArea
import org.hexworks.zircon.api.graphics.Symbols
import java.util.*

// TODO: not working
object CustomGameAreaExample {

    class CustomGameArea(visibleSize: Size3D,
                         actualSize: Size3D,
                         private val layersPerBlock: Int,
                         override val defaultBlock: Block<Tile> = Blocks.newBuilder<Tile>()
                                 .addLayer(Tiles.empty())
                                 .withEmptyTile(Tiles.empty())
                                 .build()) : BaseGameArea<Tile, Block<Tile>>(
            initialVisibleSize = visibleSize,
            initialActualSize = actualSize) {

        private val blocks = java.util.TreeMap<Position3D, Block<Tile>>()

        override fun layersPerBlock(): Int {
            return layersPerBlock
        }

        override fun hasBlockAt(position: Position3D): Boolean {
            return blocks.containsKey(position)
        }

        override fun fetchBlockAt(position: Position3D): Maybe<Block<Tile>> {
            return Maybes.ofNullable(blocks[position])
        }

        override fun fetchBlockOrDefault(position: Position3D): Block<Tile> {
            return blocks.getOrDefault(position, defaultBlock)
        }

        override fun fetchBlocks(): Iterable<Cell3D<Tile, Block<Tile>>> {
            return ArrayList(blocks.map { Cell3D.create(it.key, it.value) })
        }

        override fun setBlockAt(position: Position3D, block: Block<Tile>) {
            if (!actualSize.containsPosition(position)) {
                throw IllegalArgumentException("The supplied position ($position) is not within the size (${actualSize}) of this game area.")
            }
            val layerCount = block.layers.size
            if (layerCount != layersPerBlock()) {
                throw IllegalArgumentException("The number of layers per block for this game area is $layersPerBlock. The supplied layers have a size of $layerCount.")
            }
            blocks[position] = block
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {

        val gameArea = CustomGameArea(VISIBLE_SIZE, ACTUAL_SIZE, 1)

        makeCaves(gameArea)

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withSize(Sizes.create(60, 30))
                .enableBetaFeatures()
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        screen.addComponent(GameComponents.newGameComponentBuilder<Tile, Block<Tile>>()
                .withVisibleSize(Sizes.create3DSize(60, 30, 1))
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

                            if (tiles[Positions.create(x + ox, y + oy)] === FLOOR)
                                floors++
                            else
                                rocks++
                        }
                    }
                    newTiles[Positions.create(x, y)] = if (floors >= rocks) FLOOR else WALL
                }
            }
            tiles = newTiles
        }
        tiles.forEach { pos, tile ->
            val pos3D = Positions.from2DTo3D(pos)
            gameArea.setBlockAt(pos3D, Blocks.newBuilder<Tile>()
                    .addLayer(tile)
                    .withEmptyTile(Tiles.empty())
                    .build())
        }
    }

    private val FLOOR = Tiles.newBuilder()
            .withCharacter(Symbols.INTERPUNCT)
            .withForegroundColor(ANSITileColor.YELLOW)
            .buildCharacterTile()

    private val WALL = Tiles.newBuilder()
            .withCharacter('#')
            .withForegroundColor(TileColors.fromString("#999999"))
            .buildCharacterTile()

    private val VISIBLE_SIZE = Sizes.create3DSize(100, 100, 100)
    private val ACTUAL_SIZE = Sizes.create3DSize(100, 100, 200)


}
