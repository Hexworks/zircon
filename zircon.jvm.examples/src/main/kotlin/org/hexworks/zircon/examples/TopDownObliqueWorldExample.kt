package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols

object TopDownObliqueWorldExample {

    private val WORLD_SIZE = Sizes.create3DSize(100, 100, 100)

    private val BLACK = TileColors.fromString("#140c1c")
    private val DARK_BROWN = TileColors.fromString("#442434")
    private val PURPLE = TileColors.fromString("#30346d")
    private val DARK_GREY = TileColors.fromString("#4e4a4e")
    private val BROWN = TileColors.fromString("#854c30")
    private val DARK_GREEN = TileColors.fromString("#346524")
    private val RED = TileColors.fromString("#d04648")
    private val GRAY = TileColors.fromString("#757161")
    private val BLUE = TileColors.fromString("#597dce")
    private val ORANGE = TileColors.fromString("#d27d2c")
    private val LIGHT_GRAY = TileColors.fromString("#8595a1")
    private val LIGHT_GREEN = TileColors.fromString("#6daa2c")
    private val CREAM = TileColors.fromString("#d2aa99")
    private val TEAL = TileColors.fromString("#6dc2ca")
    private val YELLOW = TileColors.fromString("#dad45e")
    private val BRIGHT_GREEN = TileColors.fromString("#deeed6")
    private val TRANSPARENT = TileColors.transparent()


    private val EMPTY = Tiles.empty()
    private val GUY = Tiles.defaultTile()
            .withCharacter(Symbols.FACE_BLACK)
            .withForegroundColor(GRAY)
            .withBackgroundColor(TRANSPARENT)
    private val FLOOR = Tiles.defaultTile()
            .withCharacter(Symbols.BLOCK_SPARSE)
            .withBackgroundColor(BROWN)
            .withForegroundColor(CREAM)

    private val WALL_FRONT = Tiles.defaultTile()
            .withCharacter(Symbols.BLOCK_SOLID)
            .withBackgroundColor(DARK_GREY)
            .withForegroundColor(GRAY)
    private val WALL_TOP = Tiles.defaultTile()
            .withCharacter('#')
            .withBackgroundColor(BLACK)
            .withForegroundColor(DARK_GREY)

    private val GLASS = Tiles.defaultTile()
            .withCharacter(' ')
            .withBackgroundColor(TileColors.create(
                    red = BLUE.red,
                    green = BLUE.green,
                    blue = BLUE.blue,
                    alpha = 125))

    private val ROOFTOP = Tiles.defaultTile()
            .withCharacter(Symbols.DOUBLE_LINE_CROSS)
            .withBackgroundColor(BROWN)
            .withForegroundColor(GRAY)
    private val GRASS_TILE = Tiles.defaultTile()
            .withCharacter(',')
            .withBackgroundColor(TileColor.fromString("#346524"))
            .withForegroundColor(TileColor.fromString("#6daa2c"))

    private val GRASS_BLOCK_0 = Blocks.newBuilder<Tile>()
            .withEmptyTile(Tiles.empty())
            .withLayers(GRASS_TILE)
            .build()

    private val EMPTY_BLOCK = Blocks.newBuilder<Tile>()
            .withEmptyTile(EMPTY)
            .withLayers(EMPTY)
            .build()

    private val FLOOR_BLOCK = Blocks.newBuilder<Tile>()
            .withEmptyTile(Tiles.empty())
            .withLayers(EMPTY)
            .withBottom(FLOOR)
            .build()

    private val WALL_BLOCK = Blocks.newBuilder<Tile>()
            .withLayers(EMPTY)
            .withTop(WALL_TOP)
            .withFront(WALL_FRONT)
            .withEmptyTile(EMPTY)
            .build()

    private val GLASS_BLOCK_FRONT = Blocks.newBuilder<Tile>()
            .withLayers(EMPTY)
            .withFront(GLASS)
            .withEmptyTile(EMPTY)
            .build()

    private val GLASS_BLOCK_BACK = Blocks.newBuilder<Tile>()
            .withLayers(EMPTY)
            .withBack(GLASS)
            .withEmptyTile(EMPTY)
            .build()

    @JvmStatic
    fun main(args: Array<String>) {

        val screen = Screens.createScreenFor(LibgdxApplications.startTileGrid(
                AppConfigs.newConfig()
                        .enableBetaFeatures()
                        .withDebugMode(true)
                        .build()))

        val panel = Components.panel()
                .withSize(screen.size)
                .withBoxType(BoxType.DOUBLE)
                .wrapWithBox()
                .withTitle("World")
                .build()

        val ga = GameComponents.newGameAreaBuilder<Tile, Block<Tile>>()
                .withActualSize(WORLD_SIZE)
                .withVisibleSize(Sizes.from2DTo3D(panel.contentSize, 10))
                .withDefaultBlock(EMPTY_BLOCK)
                .withLayersPerBlock(1)
                .build()

        val gc = GameComponents.newGameComponentBuilder<Tile, Block<Tile>>()
                .withGameArea(ga)
                .withVisibleSize(ga.visibleSize())
                .withProjectionMode(ProjectionMode.TOP_DOWN_OBLIQUE)
                .build()

        panel.addComponent(gc)
        screen.addComponent(panel)
        screen.display()
        screen.applyColorTheme(ColorThemes.forest())

        addGrass(ga)

        val offset = Positions.create(5, 5)
        val houseSize = Sizes.create(12, 9)
        val houseHeight = 4
        houseSize.fetchPositions().forEach { pos ->
            ga.setBlockAt(Positions.from2DTo3D(pos + offset, 0), FLOOR_BLOCK)
        }
        repeat(houseHeight) { lvl ->
            Shapes.buildRectangle(offset, houseSize).positions().forEach { pos ->
                ga.setBlockAt(Positions.from2DTo3D(pos + offset, lvl), WALL_BLOCK)
            }
        }

        // bottom left windows
        ga.setBlockAt(Positions.create3DPosition(6, 13, 1), GLASS_BLOCK_FRONT)
        ga.setBlockAt(Positions.create3DPosition(7, 13, 1), GLASS_BLOCK_FRONT)
        ga.setBlockAt(Positions.create3DPosition(8, 13, 1), GLASS_BLOCK_FRONT)
        ga.setBlockAt(Positions.create3DPosition(6, 13, 2), GLASS_BLOCK_FRONT)
        ga.setBlockAt(Positions.create3DPosition(7, 13, 2), GLASS_BLOCK_FRONT)
        ga.setBlockAt(Positions.create3DPosition(8, 13, 2), GLASS_BLOCK_FRONT)

        // top left windows
        ga.setBlockAt(Positions.create3DPosition(6, 5, 1), GLASS_BLOCK_BACK)
        ga.setBlockAt(Positions.create3DPosition(7, 5, 1), GLASS_BLOCK_BACK)
        ga.setBlockAt(Positions.create3DPosition(8, 5, 1), GLASS_BLOCK_BACK)
        ga.setBlockAt(Positions.create3DPosition(6, 5, 2), GLASS_BLOCK_BACK)
        ga.setBlockAt(Positions.create3DPosition(7, 5, 2), GLASS_BLOCK_BACK)
        ga.setBlockAt(Positions.create3DPosition(8, 5, 2), GLASS_BLOCK_BACK)

        // door
        ga.setBlockAt(Positions.create3DPosition(10, 13, 0), FLOOR_BLOCK)
        ga.setBlockAt(Positions.create3DPosition(11, 13, 0), FLOOR_BLOCK)
        ga.setBlockAt(Positions.create3DPosition(10, 13, 1), EMPTY_BLOCK)
        ga.setBlockAt(Positions.create3DPosition(11, 13, 1), EMPTY_BLOCK)
        ga.setBlockAt(Positions.create3DPosition(10, 13, 2), EMPTY_BLOCK)
        ga.setBlockAt(Positions.create3DPosition(11, 13, 2), EMPTY_BLOCK)

        // bottom right windows
        ga.setBlockAt(Positions.create3DPosition(13, 13, 1), GLASS_BLOCK_FRONT)
        ga.setBlockAt(Positions.create3DPosition(14, 13, 1), GLASS_BLOCK_FRONT)
        ga.setBlockAt(Positions.create3DPosition(15, 13, 1), GLASS_BLOCK_FRONT)
        ga.setBlockAt(Positions.create3DPosition(13, 13, 2), GLASS_BLOCK_FRONT)
        ga.setBlockAt(Positions.create3DPosition(14, 13, 2), GLASS_BLOCK_FRONT)
        ga.setBlockAt(Positions.create3DPosition(15, 13, 2), GLASS_BLOCK_FRONT)

        // top right windows
        ga.setBlockAt(Positions.create3DPosition(13, 5, 1), GLASS_BLOCK_BACK)
        ga.setBlockAt(Positions.create3DPosition(14, 5, 1), GLASS_BLOCK_BACK)
        ga.setBlockAt(Positions.create3DPosition(15, 5, 1), GLASS_BLOCK_BACK)
        ga.setBlockAt(Positions.create3DPosition(13, 5, 2), GLASS_BLOCK_BACK)
        ga.setBlockAt(Positions.create3DPosition(14, 5, 2), GLASS_BLOCK_BACK)
        ga.setBlockAt(Positions.create3DPosition(15, 5, 2), GLASS_BLOCK_BACK)

    }

    private fun addGrass(gameArea: GameArea<Tile, Block<Tile>>) {
        gameArea.actualSize().to2DSize().fetchPositions().forEach { pos ->
            gameArea.setBlockAt(Positions.from2DTo3D(pos, 0), GRASS_BLOCK_0)
        }
    }

}
