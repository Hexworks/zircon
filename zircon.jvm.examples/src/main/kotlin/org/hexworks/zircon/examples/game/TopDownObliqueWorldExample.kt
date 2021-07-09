@file:Suppress("unused")

package org.hexworks.zircon.examples.game


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.GameComponents
import org.hexworks.zircon.api.Shapes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.modifier.Border
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.KeyCode.DOWN
import org.hexworks.zircon.api.uievent.KeyCode.KEY_D
import org.hexworks.zircon.api.uievent.KeyCode.KEY_U
import org.hexworks.zircon.api.uievent.KeyCode.LEFT
import org.hexworks.zircon.api.uievent.KeyCode.RIGHT
import org.hexworks.zircon.api.uievent.KeyCode.UP
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.Processed
import kotlin.random.Random

object TopDownObliqueWorldExample {

    @JvmStatic
    fun main(args: Array<String>) {

        val screen = Screen.create(
            SwingApplications.startTileGrid(
                AppConfig.newBuilder()
                    .enableBetaFeatures()
                    .withDefaultTileset(CP437TilesetResources.rexPaint20x20())
                    .withDebugMode(true)
                    .build()
            )
        )

        val panel = Components.panel()
            .withSize(screen.size)
            .withDecorations(box(boxType = BoxType.DOUBLE, title = "World"))
            .build()

        val ga = GameComponents.newGameAreaBuilder<Tile, Block<Tile>>()
            .withActualSize(WORLD_SIZE)
            .withVisibleSize(panel.contentSize.to3DSize(VISIBLE_Z_LEVELS))
            .withProjectionMode(ProjectionMode.TOP_DOWN_OBLIQUE_FRONT)
            .build()
        screen.onShutdown {
            ga.dispose()
        }

        val gc = Components.label()
            .withComponentRenderer(GameComponents.newGameAreaComponentRenderer(ga))
            .withSize(panel.contentSize)
            .build()

        panel.addComponent(gc)
        screen.addComponent(panel)
        screen.display()
        screen.theme = ColorThemes.forest()
        screen.handleKeyboardEvents(KeyboardEventType.KEY_PRESSED) { event, _ ->
            if (event.code == LEFT) {
                ga.scrollOneLeft()
            }
            if (event.code == RIGHT) {
                ga.scrollOneRight()
            }
            if (event.code == UP) {
                ga.scrollOneBackward()
            }
            if (event.code == DOWN) {
                ga.scrollOneForward()
            }
            if (event.code == KEY_U) {
                ga.scrollOneUp()
            }
            if (event.code == KEY_D) {
                ga.scrollOneDown()
            }
            Processed
        }

        addGrass(ga)

        val goblinFront = BLOCK_BASE.createCopy().apply {
            front = Tile.empty().withCharacter('g')
                .withBackgroundColor(GREY)
                .withForegroundColor(BLACK)
            top = Tile.empty().withCharacter(Symbols.ARROW_DOWN)
                .withBackgroundColor(LIGHT_GREY)
                .withForegroundColor(CREAM)
        }
        val trollLegs = BLOCK_BASE.createCopy().apply {
            front = Tile.empty().withCharacter(Symbols.DOUBLE_LINE_VERTICAL)
                .withBackgroundColor(TileColor.transparent())
                .withForegroundColor(ORANGE)
        }
        val trollTorso = BLOCK_BASE.createCopy().apply {
            front = Tile.empty().withCharacter('t')
                .withBackgroundColor(GREY)
                .withForegroundColor(BLACK)
            top = Tile.empty().withCharacter(Symbols.ARROW_DOWN)
                .withBackgroundColor(LIGHT_GREY)
                .withForegroundColor(CREAM)
        }
        ga.setBlockAt(Position3D.create(30, 10, 0), goblinFront)

        ga.setBlockAt(Position3D.create(32, 10, 0), trollLegs)
        ga.setBlockAt(Position3D.create(32, 10, 1), trollTorso)


        addHouse(ga, Position.create(5, 5), Size.create(12, 8))
        addHouse(ga, Position.create(40, 0), Size.create(9, 8))
        addHouse(ga, Position.create(25, 20), Size.create(14, 6))
    }

    private fun addHouse(ga: GameArea<Tile, Block<Tile>>, topLeft: Position, size: Size) {
        require(size.width > 2)
        require(size.height > 2)

        val bottomLeft = topLeft.withRelativeY(size.height - 1)
        val bottomRight = bottomLeft.withRelativeX(size.width - 1)
        val topRight = topLeft.withRelativeX(size.width - 1)

        val houseHeight = 4
        Shapes.buildFilledRectangle(topLeft, size).positions.forEach {
            ga.setBlockAt(it.toPosition3D(0), FLOOR_BLOCK)
        }
        repeat(houseHeight) { lvl ->
            Shapes.buildLine(bottomLeft, bottomRight).positions.forEach { pos ->
                ga.setBlockAt(pos.toPosition3D(lvl), wallFront())
            }
            Shapes.buildLine(topLeft, topRight).positions.forEach { pos ->
                ga.setBlockAt(pos.toPosition3D(lvl), wallBack())
            }
            Shapes.buildLine(topLeft.withRelativeY(1), bottomLeft.withRelativeY(-1)).positions.forEach { pos ->
                ga.setBlockAt(pos.toPosition3D(lvl), wallBack())
            }
            Shapes.buildLine(topRight.withRelativeY(1), bottomRight.withRelativeY(-1)).positions.forEach { pos ->
                ga.setBlockAt(pos.toPosition3D(lvl), wallBack())
            }
        }
        val doorPos = topLeft.withRelativeY(size.height - 1).withRelativeX((size.width - 1) / 2)
        ga.setBlockAt(doorPos.toPosition3D(0), FLOOR_BLOCK)
        ga.setBlockAt(doorPos.toPosition3D(1), EMPTY_BLOCK)
        if (size.width > 8 && houseHeight > 3) {
            val frontWindowPositions = listOf(
                doorPos.withRelativeX(-2).toPosition3D(1),
                doorPos.withRelativeX(-2).toPosition3D(2),
                doorPos.withRelativeX(-3).toPosition3D(1),
                doorPos.withRelativeX(-3).toPosition3D(2),
                doorPos.withRelativeX(2).toPosition3D(1),
                doorPos.withRelativeX(2).toPosition3D(2),
                doorPos.withRelativeX(3).toPosition3D(1),
                doorPos.withRelativeX(3).toPosition3D(2)
            )
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
        repeat(size.width.coerceAtMost(size.height) / 2 + 1) { idx ->
            Shapes.buildRectangle(roofOffset, roofSize.minus(Size.create(idx * 2, idx * 2))).positions.forEach {
                ga.setBlockAt(
                    it.plus(roofOffset)
                        .withRelativeX(idx)
                        .withRelativeY(idx)
                        .toPosition3D(houseHeight + idx), roof()
                )
            }
        }
    }

    private fun addGrass(ga: GameArea<Tile, Block<Tile>>) {
        ga.actualSize.to2DSize().fetchPositions().forEach { pos ->
            ga.setBlockAt(pos.toPosition3D(0), grass())
        }
    }

    private val WORLD_SIZE = Size3D.create(100, 100, 100)
    private const val VISIBLE_Z_LEVELS = 5
    private val random = Random(5643218)

    private val BLACK = TileColor.fromString("#140c1c")
    private val DARK_BROWN = TileColor.fromString("#442434")
    private val PURPLE = TileColor.fromString("#30346d")
    private val DARK_GREY = TileColor.fromString("#4e4a4e")
    private val BROWN = TileColor.fromString("#854c30")
    private val DARK_GREEN = TileColor.fromString("#346524")
    private val RED = TileColor.fromString("#d04648")
    private val GREY = TileColor.fromString("#757161")
    private val BLUE = TileColor.fromString("#597dce")
    private val ORANGE = TileColor.fromString("#d27d2c")
    private val LIGHT_GREY = TileColor.fromString("#8595a1")
    private val LIGHT_GREEN = TileColor.fromString("#6daa2c")
    private val CREAM = TileColor.fromString("#d2aa99")
    private val TEAL = TileColor.fromString("#6dc2ca")
    private val YELLOW = TileColor.fromString("#dad45e")
    private val BRIGHT_GREEN = TileColor.fromString("#deeed6")
    private val TRANSPARENT = TileColor.transparent()


    private val EMPTY = Tile.empty()
    private val FLOOR = Tile.defaultTile()
        .withCharacter(Symbols.BLOCK_SPARSE)
        .withBackgroundColor(BROWN)
        .withForegroundColor(CREAM)

    private val WALL_TOP = Tile.defaultTile()
        .withCharacter(Symbols.BLOCK_SOLID)
        .withForegroundColor(DARK_GREY)

    private val GLASS = Tile.defaultTile()
        .withCharacter(' ')
        .withBackgroundColor(
            TileColor.create(
                red = BLUE.red,
                green = BLUE.green,
                blue = BLUE.blue,
                alpha = 125
            )
        )

    private val ROOF_TOP = Tile.defaultTile()
        .withCharacter(Symbols.DOUBLE_LINE_HORIZONTAL)
        .withModifiers(Border.newBuilder().withBorderColor(BROWN).build())
        .withBackgroundColor(BROWN)
        .withForegroundColor(RED)

    private val ROOF_FRONT = Tile.defaultTile()
        .withCharacter(Symbols.DOUBLE_LINE_HORIZONTAL)
        .withBackgroundColor(DARK_BROWN)
        .withForegroundColor(BROWN)

    private val BLOCK_BASE = Block.newBuilder<Tile>()
        .withContent(EMPTY)
        .withEmptyTile(EMPTY)
        .build()

    private fun roof() = Block.newBuilder<Tile>()
        .withContent(EMPTY)
        .withTop(ROOF_TOP)
        .withFront(ROOF_FRONT)
        .withEmptyTile(EMPTY)
        .build()

    private val GRASS_TILES = listOf(
        Tile.defaultTile()
            .withCharacter(',')
            .withBackgroundColor(DARK_GREEN)
            .withForegroundColor(LIGHT_GREEN),
        Tile.defaultTile()
            .withCharacter('.')
            .withBackgroundColor(DARK_GREEN)
            .withForegroundColor(BRIGHT_GREEN),
        Tile.defaultTile()
            .withCharacter('"')
            .withBackgroundColor(DARK_GREEN)
            .withForegroundColor(LIGHT_GREEN)
    )

    private fun grass() = Block.newBuilder<Tile>()
        .withEmptyTile(Tile.empty())
        .withContent(Tile.empty())
        .withBottom(GRASS_TILES[random.nextInt(GRASS_TILES.size)].let {
            it.withForegroundColor(it.foregroundColor.darkenByPercent(random.nextDouble(.1)))
                .withBackgroundColor(it.backgroundColor.lightenByPercent(random.nextDouble(.1)))
        })
        .build()

    private val EMPTY_BLOCK = Block.newBuilder<Tile>()
        .withEmptyTile(EMPTY)
        .withContent(EMPTY)
        .build()

    private val FLOOR_BLOCK = Block.newBuilder<Tile>()
        .withEmptyTile(Tile.empty())
        .withContent(EMPTY)
        .withBottom(FLOOR)
        .build()

    private val GLASS_BLOCK_FRONT = Block.newBuilder<Tile>()
        .withContent(EMPTY)
        .withFront(GLASS)
        .withEmptyTile(EMPTY)
        .build()

    private val GLASS_BLOCK_BACK = Block.newBuilder<Tile>()
        .withContent(EMPTY)
        .withBack(GLASS)
        .withEmptyTile(EMPTY)
        .build()

    private fun wallOutside() = Tile.defaultTile()
        .withCharacter(Symbols.DOUBLE_LINE_HORIZONTAL_SINGLE_LINE_CROSS)
        .withBackgroundColor(BROWN.darkenByPercent(random.nextDouble(.15)))
        .withForegroundColor(CREAM.lightenByPercent(random.nextDouble(.15)))

    private fun wallInside() = Tile.defaultTile()
        .withCharacter(Symbols.DOUBLE_LINE_VERTICAL_SINGLE_LINE_CROSS)
        .withBackgroundColor(DARK_GREY.darkenByPercent(random.nextDouble(.25)))
        .withForegroundColor(GREY.lightenByPercent(random.nextDouble(.25)))

    private fun wallFront() = Block.newBuilder<Tile>()
        .withContent(EMPTY)
        .withTop(WALL_TOP)
        .withFront(wallOutside())
        .withBack(wallInside())
        .withEmptyTile(EMPTY)
        .build()

    private fun wallBack() = wallFront().withFlippedAroundY()
}
