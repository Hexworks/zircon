package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.builder.data.BlockBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.util.TreeMap
import org.hexworks.zircon.platform.factory.TreeMapFactory

object CustomGameAreaExample {

    data class CustomTile(val characterTile: CharacterTile) : CharacterTile by characterTile

    class CustomGameArea(private val size: Size3D) : GameArea {

        private val blocks: TreeMap<Position3D, Block> = TreeMapFactory.create()
        private val filler = BlockBuilder.create()
                .layer(Tiles.empty())

        override fun size() = size

        override fun getLayersPerBlock() = 1

        override fun hasBlockAt(position: Position3D) = blocks.containsKey(position)

        override fun fetchBlockAt(position: Position3D): Maybe<Block> {
            return Maybe.ofNullable(blocks[position])
        }

        override fun fetchBlockOrDefault(position: Position3D) =
                blocks.getOrDefault(position, filler.position(position).build())

        override fun fetchBlocks(): Iterable<Block> {
            return blocks.values.toList()
        }

        override fun setBlockAt(position: Position3D, block: Block) {
            require(size().containsPosition(position)) {
                "The supplied position ($position) is not within the size ($size) of this game area."
            }
            val layerCount = block.layers.size
            require(layerCount == getLayersPerBlock()) {
                "The number of layers per block for this game area is ${getLayersPerBlock()}." +
                        " The supplied layers have a size of $layerCount."
            }
            blocks[position] = block
        }

    }

    @JvmStatic
    fun main(args: Array<String>) {

        val gameArea = CustomGameArea(Sizes.create3DSize(100, 100, 100))

        makeCaves(gameArea)

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultSize(Size.create(80, 60))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        screen.addComponent(GameComponents.newGameComponentBuilder()
                .visibleSize(Sizes.create3DSize(80, 60, 1))
                .gameArea(gameArea)
                .build())

        screen.display()

    }

    fun makeCaves(gameArea: GameArea, smoothTimes: Int = 8) {
        val width = gameArea.size().xLength
        val height = gameArea.size().yLength
        var tiles: MutableMap<Position, Tile> = mutableMapOf()
        gameArea.size().to2DSize().fetchPositions().forEach { pos ->
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
            .foregroundColor(TileColor.fromString("#999999"))
            .buildCharacterTile()

}
