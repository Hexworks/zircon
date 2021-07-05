package org.hexworks.zircon.examples.other


import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.GameComponents
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.base.BaseGameArea
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.util.BSPTree

object BSPExample {

    private val VISIBLE_SIZE = Size3D.create(50, 50, 1)
    private val ACTUAL_SIZE = Size3D.create(100, 50, 1)

    @JvmStatic
    fun main(args: Array<String>) {

        val gameArea = CustomGameArea(VISIBLE_SIZE, ACTUAL_SIZE)

        createMap(gameArea, ACTUAL_SIZE.xLength, ACTUAL_SIZE.yLength)

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withSize(Size.create(ACTUAL_SIZE.xLength + 1, ACTUAL_SIZE.yLength + 1))
                .enableBetaFeatures()
                .build())

        val screen = Screen.create(tileGrid)

        screen.addComponent(Components.label()
                .withComponentRenderer(GameComponents.newGameAreaComponentRenderer(gameArea))
                .withPreferredSize(VISIBLE_SIZE.xLength, VISIBLE_SIZE.yLength)
                .build())

        screen.onShutdown {
            gameArea.dispose()
        }

        screen.display()
    }

    fun createMap(gameArea: GameArea<Tile, Block<Tile>>, width: Int, height: Int) {
        val r = Rect.create(Position.create(0, 0), Size.create(width, height))
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
                        val tile = Tile.newBuilder()
                                .withCharacter(char)
                                .withForegroundColor(TileColor.fromString("#999999"))
                                .buildCharacterTile()
                        gameArea.setBlockAt(Position3D.create(x, y, 0), Block.newBuilder<Tile>()
                                .withContent(tile)
                                .withEmptyTile(Tile.empty())
                                .build())
                    }
                }
            }
            nbr++
        }

    }

    class CustomGameArea(
            visibleSize: Size3D,
            actualSize: Size3D
    ) : BaseGameArea<Tile, Block<Tile>>(
            initialVisibleSize = visibleSize,
            initialActualSize = actualSize,
            initialFilters = listOf()
    )

}


