package org.hexworks.zircon.examples.other

import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.data.block
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.builder.game.gameArea
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.game.gameAreaComponentRenderer
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import kotlin.random.Random

/**
 * A dungeon carved by binary space partitioning: the area is split in halves until the
 * pieces are small, a room is placed in every leaf, and each room is drawn with its own
 * character into a game area twice as wide as the view.
 */
public val bspExample: Example = Example("BSP") { context ->
    val visibleSize = Size3D(context.contentSize.width, context.contentSize.height, 1)
    val actualSize = Size3D(context.contentSize.width * 2, context.contentSize.height, 1)
    val area = gameArea<Tile, Block<Tile>> {
        this.visibleSize = visibleSize
        this.actualSize = actualSize
    }
    createMap(area, actualSize.xLength, actualSize.yLength)
    val view = label {
        preferredSize = context.contentSize
        componentRenderer = gameAreaComponentRenderer(area, ProjectionMode.TOP_DOWN, Tile.empty())
    }
    ExampleView(view)
}

private fun createMap(area: GameArea<Tile, Block<Tile>>, width: Int, height: Int) {
    val root = BspNode(Position.ZERO, Size(width, height))
    root.split(Random(42))
    val rooms = mutableListOf<BspNode>()
    root.collectRooms(rooms)
    var char = 48
    for (node in rooms) {
        val tile = characterTile {
            character = char.toChar()
            withStyleSet { foregroundColor = Color.fromString("#999999") }
        }
        val room = node.room ?: continue
        for (y in room.first.y until room.first.y + room.second.height) {
            for (x in room.first.x until room.first.x + room.second.width) {
                area.setBlockAt(Position3D(x, y, 0), block {
                    content = tile
                    emptyTile = Tile.empty()
                })
            }
        }
        char++
    }
}

/** One node of the partition: its bounds, its two halves, and in a leaf its room. */
private class BspNode(val position: Position, val size: Size) {
    var left: BspNode? = null
    var right: BspNode? = null
    var room: Pair<Position, Size>? = null

    fun split(random: Random) {
        if (size.width < MIN_LEAF * 2 && size.height < MIN_LEAF * 2) {
            placeRoom(random)
            return
        }
        val horizontal = when {
            size.width < MIN_LEAF * 2 -> true
            size.height < MIN_LEAF * 2 -> false
            else -> random.nextBoolean()
        }
        if (horizontal) {
            val at = random.nextInt(MIN_LEAF, size.height - MIN_LEAF + 1)
            left = BspNode(position, Size(size.width, at))
            right = BspNode(Position(position.x, position.y + at), Size(size.width, size.height - at))
        } else {
            val at = random.nextInt(MIN_LEAF, size.width - MIN_LEAF + 1)
            left = BspNode(position, Size(at, size.height))
            right = BspNode(Position(position.x + at, position.y), Size(size.width - at, size.height))
        }
        left?.split(random)
        right?.split(random)
    }

    private fun placeRoom(random: Random) {
        val roomWidth = random.nextInt(3, maxOf(4, size.width - 1))
        val roomHeight = random.nextInt(3, maxOf(4, size.height - 1))
        val x = position.x + random.nextInt(1, maxOf(2, size.width - roomWidth))
        val y = position.y + random.nextInt(1, maxOf(2, size.height - roomHeight))
        room = Position(x, y) to Size(roomWidth, roomHeight)
    }

    fun collectRooms(into: MutableList<BspNode>) {
        if (room != null) into += this
        left?.collectRooms(into)
        right?.collectRooms(into)
    }

    companion object {
        const val MIN_LEAF = 8
    }
}
