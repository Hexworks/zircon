package org.hexworks.zircon.examples.utilities


import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.base.BaseGameArea
import org.hexworks.zircon.api.util.BSPTree

object BSPExample {

    private val VISIBLE_SIZE = Sizes.create3DSize(50, 50, 1)
    private val ACTUAL_SIZE = Sizes.create3DSize(100, 50, 1)

    @JvmStatic
    fun main(args: Array<String>) {

        val gameArea = BSPExample.CustomGameArea(VISIBLE_SIZE, ACTUAL_SIZE)

        createMap(gameArea, ACTUAL_SIZE.xLength, ACTUAL_SIZE.yLength)

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withSize(Sizes.create(ACTUAL_SIZE.xLength + 1, ACTUAL_SIZE.yLength + 1))
                .enableBetaFeatures()
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        screen.addComponent(GameComponents.newGameComponentBuilder<Tile, Block<Tile>>()
                .withSize(VISIBLE_SIZE.xLength, VISIBLE_SIZE.yLength)
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
                                .withContent(tile)
                                .withEmptyTile(Tiles.empty())
                                .build())
                    }
                }
            }
            nbr++
        }

    }

    class CustomGameArea(visibleSize: Size3D,
                         actualSize: Size3D) : BaseGameArea<Tile, Block<Tile>>(
            initialVisibleSize = visibleSize,
            initialActualSize = actualSize)

}


