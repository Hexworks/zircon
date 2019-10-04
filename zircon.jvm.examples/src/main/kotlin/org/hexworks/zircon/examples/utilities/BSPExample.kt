package org.hexworks.zircon.examples.utilities


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
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D
import org.hexworks.zircon.api.game.Cell3D
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.base.BaseGameArea
import org.hexworks.zircon.api.util.BSPTree
import java.util.*

// TODO: not working, fix game area
object BSPExample {

    private val VISIBLE_SIZE = Sizes.create3DSize(50, 50, 1)
    private val ACTUAL_SIZE = Sizes.create3DSize(100, 50, 1)

    @JvmStatic
    fun main(args: Array<String>) {

        val gameArea = BSPExample.CustomGameArea(VISIBLE_SIZE, ACTUAL_SIZE, 1)

        createMap(gameArea, ACTUAL_SIZE.xLength, ACTUAL_SIZE.yLength)

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withSize(Sizes.create(ACTUAL_SIZE.xLength + 1, ACTUAL_SIZE.yLength + 1))
                .enableBetaFeatures()
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        screen.addComponent(GameComponents.newGameComponentBuilder<Tile, Block<Tile>>()
                .withVisibleSize(Sizes.create3DSize(ACTUAL_SIZE.xLength, ACTUAL_SIZE.yLength, 1))
                .withGameArea(gameArea)
                .build())

        screen.display()
    }

    fun createMap(gameArea: GameArea<Tile, Block<Tile>>, width: Int, height: Int) {
        val r = Rect.create(Positions.create(0, 0), Sizes.create(width, height))
        val root = BSPTree(r)
        root.createRooms()

        val list = mutableListOf<BSPTree>()
        BSPTree.collectRooms(root, list)

        var nbr = 48
        for (BSPTree: BSPTree in list) {
            val char = nbr.toChar()
            BSPTree.whenHasRoom { rec ->
                for (y in rec.position.y until rec.position.y + rec.height) {
                    for (x in rec.position.x until rec.position.x + rec.width) {
                        val tile = Tiles.newBuilder()
                                .withCharacter(char)
                                .withForegroundColor(TileColors.fromString("#999999"))
                                .buildCharacterTile()
                        gameArea.setBlockAt(Position3D.create(x, y, 0), Blocks.newBuilder<Tile>()
                                .addLayer(tile)
                                .withEmptyTile(Tiles.empty())
                                .build())
                    }
                }
            }
            nbr++
        }

    }

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

}


