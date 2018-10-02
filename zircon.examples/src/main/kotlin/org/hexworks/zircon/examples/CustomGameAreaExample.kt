package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.game.BaseGameArea
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.util.Maybe
import java.util.*

object CustomGameAreaExample {

    class CustomGameArea(override val size: Size3D, private val layersPerBlock: Int) : BaseGameArea() {
        private val blocks = java.util.TreeMap<Position3D, Block>()
        private val filler = Blocks.newBuilder()
                .layer(Tiles.empty())


        override fun layersPerBlock(): Int {
            return layersPerBlock
        }

        override fun hasBlockAt(position: Position3D): Boolean {
            return blocks.containsKey(position)
        }

        override fun fetchBlockAt(position: Position3D): Maybe<Block> {
            return Maybes.ofNullable(blocks[position])
        }

        override fun fetchBlockOrDefault(position: Position3D): Block {
            return blocks.getOrDefault(position, filler.position(position).build())
        }

        override fun fetchBlocks(): Iterable<Block> {
            return ArrayList(blocks.values)
        }

        override fun setBlockAt(position: Position3D, block: Block) {
            if (!size.containsPosition(position)) {
                throw IllegalArgumentException("The supplied position (\$position) is not within the size (\$size) of this game area.")
            }
            val layerCount = block.layers.size
            if (layerCount != layersPerBlock()) {
                throw IllegalArgumentException("The number of layers per block for this game area is \${getLayersPerBlock()}." + " The supplied layers have a size of \$layerCount.")
            }
            blocks[position] = block
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {

        val gameArea = CustomGameArea(Sizes.create3DSize(100, 100, 100), 1)

        makeCaves(gameArea)

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultSize(Sizes.create(60, 30))
                .enableBetaFeatures()
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        screen.addComponent(GameComponents.newGameComponentBuilder()
                .visibleSize(Sizes.create3DSize(60, 30, 1))
                .gameArea(gameArea)
                .build())

        screen.display()

    }

    fun makeCaves(gameArea: GameArea, smoothTimes: Int = 8) {
        val width = gameArea.size.xLength
        val height = gameArea.size.yLength
        var tiles: MutableMap<Position, Tile> = mutableMapOf()
        gameArea.size.to2DSize().fetchPositions().forEach { pos ->
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
            gameArea.setBlockAt(pos3D, Blocks.newBuilder()
                    .layer(tile)
                    .position(pos3D)
                    .build())
        }
    }

    val FLOOR = Tiles.newBuilder()
            .character(Symbols.INTERPUNCT)
            .foregroundColor(ANSITileColor.YELLOW)
            .buildCharacterTile()

    val WALL = Tiles.newBuilder()
            .character('#')
            .foregroundColor(TileColors.fromString("#999999"))
            .buildCharacterTile()

}
