package org.hexworks.zircon.examples.game

import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.behavior.extensions.scrollOneBackward
import org.hexworks.zircon.api.behavior.extensions.scrollOneDown
import org.hexworks.zircon.api.behavior.extensions.scrollOneForward
import org.hexworks.zircon.api.behavior.extensions.scrollOneLeft
import org.hexworks.zircon.api.behavior.extensions.scrollOneRight
import org.hexworks.zircon.api.behavior.extensions.scrollOneUp
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.data.block
import org.hexworks.zircon.api.builder.game.gameArea
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.color.extensions.darkenByPercent
import org.hexworks.zircon.api.color.extensions.lightenByPercent
import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.extensions.fetchPositions
import org.hexworks.zircon.api.data.extensions.front
import org.hexworks.zircon.api.data.extensions.to2DSize
import org.hexworks.zircon.api.data.extensions.toPosition3D
import org.hexworks.zircon.api.data.extensions.toSize3D
import org.hexworks.zircon.api.data.extensions.top
import org.hexworks.zircon.api.data.extensions.withFlippedAroundY
import org.hexworks.zircon.api.data.extensions.withRelativeHeight
import org.hexworks.zircon.api.data.extensions.withRelativeWidth
import org.hexworks.zircon.api.data.extensions.withRelativeX
import org.hexworks.zircon.api.data.extensions.withRelativeY
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.game.gameAreaComponentRenderer
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.shape.filledRectangle
import org.hexworks.zircon.api.shape.line
import org.hexworks.zircon.api.shape.rectangle
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import kotlin.random.Random

/**
 * A small world (grass, houses with roofs and windows, a goblin and a troll) in a game
 * area with the top-down oblique projection. Arrows scroll, `U`/`D` change the level.
 */
public val obliqueWorldExample: Example = Example("Oblique world") { context ->
    val panel = panel {
        preferredSize = context.contentSize
        decorations { +box(boxType = BoxType.DOUBLE, title = "World: arrows, U, D") }
    }
    val area = gameArea<Tile, Block<Tile>> {
        actualSize = WORLD_SIZE
        visibleSize = panel.contentSize.toSize3D(VISIBLE_Z_LEVELS)
    }
    val view: Label = label {
        preferredSize = panel.contentSize
        componentRenderer = gameAreaComponentRenderer(area, ProjectionMode.TOP_DOWN_OBLIQUE_FRONT, Tile.empty())
    }
    panel.addComponent(view)

    val world = World(Random(5643218))
    world.addGrass(area)
    area.setBlockAt(Position3D(30, 10, 0), world.goblin())
    area.setBlockAt(Position3D(32, 10, 0), world.trollLegs())
    area.setBlockAt(Position3D(32, 10, 1), world.trollTorso())
    world.addHouse(area, Position(5, 5), Size(12, 8))
    world.addHouse(area, Position(40, 0), Size(9, 8))
    world.addHouse(area, Position(25, 20), Size(14, 6))

    var keys: Subscription? = null
    ExampleView(
        content = panel,
        onShown = {
            keys = context.screen.handleKeyboardEvents(KeyboardEventType.KEY_PRESSED) { event, _ ->
                when (event.code) {
                    KeyCode.LEFT -> area.scrollOneLeft()
                    KeyCode.RIGHT -> area.scrollOneRight()
                    KeyCode.UP -> area.scrollOneBackward()
                    KeyCode.DOWN -> area.scrollOneForward()
                    KeyCode.KEY_U -> area.scrollOneUp()
                    KeyCode.KEY_D -> area.scrollOneDown()
                    else -> Unit
                }
                Processed
            }
        },
        onHidden = { keys?.dispose() },
    )
}

private class World(private val random: Random) {

    fun addGrass(area: GameArea<Tile, Block<Tile>>) {
        area.actualSize.to2DSize().fetchPositions().forEach { pos ->
            area.setBlockAt(pos.toPosition3D(0), grass())
        }
    }

    fun addHouse(area: GameArea<Tile, Block<Tile>>, topLeft: Position, size: Size) {
        require(size.width > 2)
        require(size.height > 2)
        val bottomLeft = topLeft.withRelativeY(size.height - 1)
        val bottomRight = bottomLeft.withRelativeX(size.width - 1)
        val topRight = topLeft.withRelativeX(size.width - 1)
        val houseHeight = 4
        filledRectangle(topLeft, size).positions.forEach {
            area.setBlockAt(it.toPosition3D(0), floorBlock())
        }
        repeat(houseHeight) { lvl ->
            line(bottomLeft, bottomRight).positions.forEach { pos ->
                area.setBlockAt(pos.toPosition3D(lvl), wallFront())
            }
            line(topLeft, topRight).positions.forEach { pos ->
                area.setBlockAt(pos.toPosition3D(lvl), wallBack())
            }
            line(topLeft.withRelativeY(1), bottomLeft.withRelativeY(-1)).positions.forEach { pos ->
                area.setBlockAt(pos.toPosition3D(lvl), wallBack())
            }
            line(topRight.withRelativeY(1), bottomRight.withRelativeY(-1)).positions.forEach { pos ->
                area.setBlockAt(pos.toPosition3D(lvl), wallBack())
            }
        }
        val doorPos = topLeft.withRelativeY(size.height - 1).withRelativeX((size.width - 1) / 2)
        area.setBlockAt(doorPos.toPosition3D(0), floorBlock())
        area.setBlockAt(doorPos.toPosition3D(1), emptyBlock())
        if (size.width > 8 && houseHeight > 3) {
            val frontWindowPositions = listOf(
                doorPos.withRelativeX(-2).toPosition3D(1),
                doorPos.withRelativeX(-2).toPosition3D(2),
                doorPos.withRelativeX(-3).toPosition3D(1),
                doorPos.withRelativeX(-3).toPosition3D(2),
                doorPos.withRelativeX(2).toPosition3D(1),
                doorPos.withRelativeX(2).toPosition3D(2),
                doorPos.withRelativeX(3).toPosition3D(1),
                doorPos.withRelativeX(3).toPosition3D(2),
            )
            val backWindowPositions = frontWindowPositions.map { Position3D(it.x, it.y - size.height + 1, it.z) }
            frontWindowPositions.forEach { area.setBlockAt(it, glassBlockFront()) }
            backWindowPositions.forEach { area.setBlockAt(it, glassBlockBack()) }
        }
        val roofOffset = topLeft.withRelativeX(-1).withRelativeY(-1)
        val roofSize = size.withRelativeHeight(2).withRelativeWidth(2)
        repeat(size.width.coerceAtMost(size.height) / 2 + 1) { idx ->
            rectangle(Position.ZERO, Size(roofSize.width - idx * 2, roofSize.height - idx * 2)).positions.forEach {
                area.setBlockAt(
                    (it + roofOffset).withRelativeX(idx).withRelativeY(idx).toPosition3D(houseHeight + idx),
                    roof()
                )
            }
        }
    }

    fun goblin(): Block<Tile> = block {
        emptyTile = EMPTY
        content = EMPTY
        front = Tile.empty().withCharacter('g').withBackgroundColor(GREY).withForegroundColor(BLACK)
        top = Tile.empty().withCharacter(Symbols.ARROW_DOWN).withBackgroundColor(LIGHT_GREY).withForegroundColor(CREAM)
    }

    fun trollLegs(): Block<Tile> = block {
        emptyTile = EMPTY
        content = EMPTY
        front = Tile.empty().withCharacter(Symbols.DOUBLE_LINE_VERTICAL).withForegroundColor(ORANGE)
    }

    fun trollTorso(): Block<Tile> = block {
        emptyTile = EMPTY
        content = EMPTY
        front = Tile.empty().withCharacter('t').withBackgroundColor(GREY).withForegroundColor(BLACK)
        top = Tile.empty().withCharacter(Symbols.ARROW_DOWN).withBackgroundColor(LIGHT_GREY).withForegroundColor(CREAM)
    }

    private fun roof(): Block<Tile> = block {
        emptyTile = EMPTY
        content = EMPTY
        top = ROOF_TOP
        front = ROOF_FRONT
    }

    private fun grass(): Block<Tile> = block {
        emptyTile = EMPTY
        content = EMPTY
        bottom = GRASS_TILES[random.nextInt(GRASS_TILES.size)].let {
            it.withForegroundColor(it.foregroundColor.darkenByPercent(random.nextDouble(.1)))
                .withBackgroundColor(it.backgroundColor.lightenByPercent(random.nextDouble(.1)))
        }
    }

    private fun emptyBlock(): Block<Tile> = block {
        emptyTile = EMPTY
        content = EMPTY
    }

    private fun floorBlock(): Block<Tile> = block {
        emptyTile = EMPTY
        content = EMPTY
        bottom = FLOOR
    }

    private fun glassBlockFront(): Block<Tile> = block {
        emptyTile = EMPTY
        content = EMPTY
        front = GLASS
    }

    private fun glassBlockBack(): Block<Tile> = block {
        emptyTile = EMPTY
        content = EMPTY
        back = GLASS
    }

    private fun wallOutside(): Tile = Tile.defaultTile()
        .withCharacter(Symbols.DOUBLE_LINE_HORIZONTAL_SINGLE_LINE_CROSS)
        .withBackgroundColor(BROWN.darkenByPercent(random.nextDouble(.15)))
        .withForegroundColor(CREAM.lightenByPercent(random.nextDouble(.15)))

    private fun wallInside(): Tile = Tile.defaultTile()
        .withCharacter(Symbols.DOUBLE_LINE_VERTICAL_SINGLE_LINE_CROSS)
        .withBackgroundColor(DARK_GREY.darkenByPercent(random.nextDouble(.25)))
        .withForegroundColor(GREY.lightenByPercent(random.nextDouble(.25)))

    private fun wallFront(): Block<Tile> = block {
        emptyTile = EMPTY
        content = EMPTY
        top = WALL_TOP
        front = wallOutside()
        back = wallInside()
    }

    private fun wallBack(): Block<Tile> = wallFront().withFlippedAroundY()
}

// Bigger than any screen the app runs on: the visible size may not exceed it.
private val WORLD_SIZE = Size3D(300, 200, 100)
private const val VISIBLE_Z_LEVELS = 5

private val BLACK = Color.fromString("#140c1c")
private val DARK_BROWN = Color.fromString("#442434")
private val DARK_GREY = Color.fromString("#4e4a4e")
private val BROWN = Color.fromString("#854c30")
private val DARK_GREEN = Color.fromString("#346524")
private val RED = Color.fromString("#d04648")
private val GREY = Color.fromString("#757161")
private val BLUE = Color.fromString("#597dce")
private val ORANGE = Color.fromString("#d27d2c")
private val LIGHT_GREY = Color.fromString("#8595a1")
private val LIGHT_GREEN = Color.fromString("#6daa2c")
private val CREAM = Color.fromString("#d2aa99")
private val BRIGHT_GREEN = Color.fromString("#deeed6")

private val EMPTY: Tile = Tile.empty()
private val FLOOR: Tile = Tile.defaultTile()
    .withCharacter(Symbols.BLOCK_SPARSE)
    .withBackgroundColor(BROWN)
    .withForegroundColor(CREAM)
private val WALL_TOP: Tile = Tile.defaultTile()
    .withCharacter(Symbols.BLOCK_SOLID)
    .withForegroundColor(DARK_GREY)
// No alpha in 2.0: the glass is a lighter solid blue instead of a translucent one.
private val GLASS: Tile = Tile.defaultTile()
    .withCharacter(' ')
    .withBackgroundColor(BLUE.lightenByPercent(.3))
// The old roof top carried a Border modifier, which 2.0 dropped.
private val ROOF_TOP: Tile = Tile.defaultTile()
    .withCharacter(Symbols.DOUBLE_LINE_HORIZONTAL)
    .withBackgroundColor(BROWN)
    .withForegroundColor(RED)
private val ROOF_FRONT: Tile = Tile.defaultTile()
    .withCharacter(Symbols.DOUBLE_LINE_HORIZONTAL)
    .withBackgroundColor(DARK_BROWN)
    .withForegroundColor(BROWN)
private val GRASS_TILES: List<CharacterTile> = listOf(
    Tile.defaultTile().withCharacter(',').withBackgroundColor(DARK_GREEN).withForegroundColor(LIGHT_GREEN),
    Tile.defaultTile().withCharacter('.').withBackgroundColor(DARK_GREEN).withForegroundColor(BRIGHT_GREEN),
    Tile.defaultTile().withCharacter('"').withBackgroundColor(DARK_GREEN).withForegroundColor(LIGHT_GREEN),
)
