package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.handleKeyboardEvents
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.uievent.KeyCode.*
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.Processed
import kotlin.random.Random

object TopDownObliqueWorldExample {

    private val WORLD_SIZE = Sizes.create3DSize(100, 100, 100)
    private val VISIBLE_Z_LEVELS = 5
    private val random = Random(5643218)

    private val BLACK = TileColors.fromString("#140c1c")
    private val DARK_BROWN = TileColors.fromString("#442434")
    private val PURPLE = TileColors.fromString("#30346d")
    private val DARK_GREY = TileColors.fromString("#4e4a4e")
    private val BROWN = TileColors.fromString("#854c30")
    private val DARK_GREEN = TileColors.fromString("#346524")
    private val RED = TileColors.fromString("#d04648")
    private val GREY = TileColors.fromString("#757161")
    private val BLUE = TileColors.fromString("#597dce")
    private val ORANGE = TileColors.fromString("#d27d2c")
    private val LIGHT_GREY = TileColors.fromString("#8595a1")
    private val LIGHT_GREEN = TileColors.fromString("#6daa2c")
    private val CREAM = TileColors.fromString("#d2aa99")
    private val TEAL = TileColors.fromString("#6dc2ca")
    private val YELLOW = TileColors.fromString("#dad45e")
    private val BRIGHT_GREEN = TileColors.fromString("#deeed6")
    private val TRANSPARENT = TileColors.transparent()


    private val EMPTY = Tiles.empty()
    private val FLOOR = Tiles.defaultTile()
            .withCharacter(Symbols.BLOCK_SPARSE)
            .withBackgroundColor(BROWN)
            .withForegroundColor(CREAM)

    private val WALL_TOP = Tiles.defaultTile()
            .withCharacter(Symbols.BLOCK_SOLID)
            .withForegroundColor(DARK_GREY)

    private val GLASS = Tiles.defaultTile()
            .withCharacter(' ')
            .withBackgroundColor(TileColors.create(
                    red = BLUE.red,
                    green = BLUE.green,
                    blue = BLUE.blue,
                    alpha = 125))

    private val ROOF_TOP = Tiles.defaultTile()
            .withCharacter(Symbols.DOUBLE_LINE_HORIZONTAL)
            .withModifiers(Borders.newBuilder().withBorderColor(BROWN).build())
            .withBackgroundColor(BROWN)
            .withForegroundColor(RED)

    private val ROOF_FRONT = Tiles.defaultTile()
            .withCharacter(Symbols.DOUBLE_LINE_HORIZONTAL)
            .withBackgroundColor(DARK_BROWN)
            .withForegroundColor(BROWN)

    private val BLOCK_BASE = Blocks.newBuilder<Tile>()
            .withLayers(EMPTY)
            .withEmptyTile(EMPTY)
            .build()

    private fun roof() = Blocks.newBuilder<Tile>()
            .withLayers(EMPTY)
            .withTop(ROOF_TOP)
            .withFront(ROOF_FRONT)
            .withEmptyTile(EMPTY)
            .build()

    private val GRASS_TILES = listOf(
            Tiles.defaultTile()
                    .withCharacter(',')
                    .withBackgroundColor(DARK_GREEN)
                    .withForegroundColor(LIGHT_GREEN),
            Tiles.defaultTile()
                    .withCharacter('.')
                    .withBackgroundColor(DARK_GREEN)
                    .withForegroundColor(BRIGHT_GREEN),
            Tiles.defaultTile()
                    .withCharacter('"')
                    .withBackgroundColor(DARK_GREEN)
                    .withForegroundColor(LIGHT_GREEN))

    private fun grass() = Blocks.newBuilder<Tile>()
            .withEmptyTile(Tiles.empty())
            .withLayers(Tiles.empty())
            .withBottom(GRASS_TILES[random.nextInt(GRASS_TILES.size)].let {
                it.withForegroundColor(it.foregroundColor.darkenByPercent(random.nextDouble(.1)))
                        .withBackgroundColor(it.backgroundColor.lightenByPercent(random.nextDouble(.1)))
            })
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

    private fun wallOutside() = Tiles.defaultTile()
            .withCharacter(Symbols.DOUBLE_LINE_HORIZONTAL_SINGLE_LINE_CROSS)
            .withBackgroundColor(BROWN.darkenByPercent(random.nextDouble(.15)))
            .withForegroundColor(CREAM.lightenByPercent(random.nextDouble(.15)))

    private fun wallInside() = Tiles.defaultTile()
            .withCharacter(Symbols.DOUBLE_LINE_VERTICAL_SINGLE_LINE_CROSS)
            .withBackgroundColor(DARK_GREY.darkenByPercent(random.nextDouble(.25)))
            .withForegroundColor(GREY.lightenByPercent(random.nextDouble(.25)))

    private fun wallFront() = Blocks.newBuilder<Tile>()
            .withLayers(EMPTY)
            .withTop(WALL_TOP)
            .withFront(wallOutside())
            .withBack(wallInside())
            .withEmptyTile(EMPTY)
            .build()

    private fun wallBack() = wallFront().withFlippedAroundY()

    @JvmStatic
    fun main(args: Array<String>) {

        val screen = Screens.createScreenFor(SwingApplications.startTileGrid(
                AppConfigs.newConfig()
                        .enableBetaFeatures()
                        .withDefaultTileset(CP437TilesetResources.rexPaint20x20())
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
                .withVisibleSize(Sizes.from2DTo3D(panel.contentSize, VISIBLE_Z_LEVELS))
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
        screen.handleKeyboardEvents(KeyboardEventType.KEY_PRESSED) { event, _ ->
            if (event.code == LEFT) {
                ga.scrollOneLeft()
            }
            if (event.code == RIGHT) {
                ga.scrollOneRight()
            }
            if (event.code == UP) {
                ga.scrollOneForward()
            }
            if (event.code == DOWN) {
                ga.scrollOneBackward()
            }
            if (event.code == PAGE_UP) {
                ga.scrollOneUp()
            }
            if (event.code == PAGE_DOWN) {
                ga.scrollOneDown()
            }
            Processed
        }

        addGrass(ga)

        val goblinFront = BLOCK_BASE.createCopy().apply {
            front = Tiles.empty().withCharacter('g')
                    .withBackgroundColor(GREY)
                    .withForegroundColor(BLACK)
            top = Tiles.empty().withCharacter(Symbols.ARROW_DOWN)
                    .withBackgroundColor(LIGHT_GREY)
                    .withForegroundColor(CREAM)
        }
        val trollLegs = BLOCK_BASE.createCopy().apply {
            front = Tiles.empty().withCharacter(Symbols.DOUBLE_LINE_VERTICAL)
                    .withBackgroundColor(TileColor.transparent())
                    .withForegroundColor(ORANGE)
        }
        val trollTorso = BLOCK_BASE.createCopy().apply {
            front = Tiles.empty().withCharacter('t')
                    .withBackgroundColor(GREY)
                    .withForegroundColor(BLACK)
            top = Tiles.empty().withCharacter(Symbols.ARROW_DOWN)
                    .withBackgroundColor(LIGHT_GREY)
                    .withForegroundColor(CREAM)
        }
        ga.setBlockAt(Positions.create3DPosition(30, 10, 0), goblinFront)

        ga.setBlockAt(Positions.create3DPosition(32, 10, 0), trollLegs)
        ga.setBlockAt(Positions.create3DPosition(32, 10, 1), trollTorso)


        addHouse(ga, Positions.create(5, 5), Sizes.create(12, 8))
        addHouse(ga, Positions.create(40, 0), Sizes.create(9, 8))
        addHouse(ga, Positions.create(25, 20), Sizes.create(14, 6))
    }

    private fun addHouse(ga: GameArea<Tile, Block<Tile>>, topLeft: Position, size: Size) {
        require(size.width > 2)
        require(size.height > 2)

        val bottomLeft = topLeft.withRelativeY(size.height - 1)
        val bottomRight = bottomLeft.withRelativeX(size.width - 1)
        val topRight = topLeft.withRelativeX(size.width - 1)

        val houseHeight = 4
        Shapes.buildFilledRectangle(topLeft, size).positions().forEach {
            ga.setBlockAt(Positions.from2DTo3D(it, 0), FLOOR_BLOCK)
        }
        repeat(houseHeight) { lvl ->
            Shapes.buildLine(bottomLeft, bottomRight).positions().forEach { pos ->
                ga.setBlockAt(Positions.from2DTo3D(pos, lvl), wallFront())
            }
            Shapes.buildLine(topLeft, topRight).positions().forEach { pos ->
                ga.setBlockAt(Positions.from2DTo3D(pos, lvl), wallBack())
            }
            Shapes.buildLine(topLeft.withRelativeY(1), bottomLeft.withRelativeY(-1)).positions().forEach { pos ->
                ga.setBlockAt(Positions.from2DTo3D(pos, lvl), wallBack())
            }
            Shapes.buildLine(topRight.withRelativeY(1), bottomRight.withRelativeY(-1)).positions().forEach { pos ->
                ga.setBlockAt(Positions.from2DTo3D(pos, lvl), wallBack())
            }
        }
        val doorPos = topLeft.withRelativeY(size.height - 1).withRelativeX((size.width - 1) / 2)
        ga.setBlockAt(doorPos.toPosition3D(0), FLOOR_BLOCK)
        ga.setBlockAt(doorPos.toPosition3D(1), EMPTY_BLOCK)
        if (size.width > 8 && houseHeight > 3) {
            val frontWindowPositions = listOf(doorPos.withRelativeX(-2).toPosition3D(1),
                    doorPos.withRelativeX(-2).toPosition3D(2),
                    doorPos.withRelativeX(-3).toPosition3D(1),
                    doorPos.withRelativeX(-3).toPosition3D(2),
                    doorPos.withRelativeX(2).toPosition3D(1),
                    doorPos.withRelativeX(2).toPosition3D(2),
                    doorPos.withRelativeX(3).toPosition3D(1),
                    doorPos.withRelativeX(3).toPosition3D(2))
            val backWindowPositions = frontWindowPositions.map { it.withRelativeY(-size.height + 1) }
            frontWindowPositions.forEach {
                ga.setBlockAt(it, GLASS_BLOCK_FRONT)
            }
            backWindowPositions.forEach {
                ga.setBlockAt(it, GLASS_BLOCK_BACK)
            }
        }
        val roofOffset = topLeft.withRelativeX(-1).withRelativeY(-1)
        val roofSize = size.withRelativeHeight(2).withRelativeWidth(2)
        repeat(Math.min(size.width, size.height) / 2 + 1) { idx ->
            Shapes.buildRectangle(roofOffset, roofSize.minus(Sizes.create(idx * 2, idx * 2))).positions().forEach {
                ga.setBlockAt(it.plus(roofOffset)
                        .withRelativeX(idx)
                        .withRelativeY(idx)
                        .toPosition3D(houseHeight + idx), roof())
            }
        }
    }

    private fun addGrass(ga: GameArea<Tile, Block<Tile>>) {
        ga.actualSize().to2DSize().fetchPositions().forEach { pos ->
            ga.setBlockAt(Positions.from2DTo3D(pos, 0), grass())
        }
    }

}
