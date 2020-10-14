package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile

class ArrayBackedTileMap(
        private val dimensions: Size,
        private val arr: Array<Tile?>
) : Map<Position, Tile> {

    override val entries: Set<Map.Entry<Position, Tile>>
        get() = arr.mapIndexed { index, _ ->
            arr[index]?.let { Entry(index.pos, it) }
        }.filterNotNull().toSet()

    override val keys: Set<Position>
        get() = arr.mapIndexed { index, _ ->
            arr[index]?.let { index.pos }
        }.filterNotNull().toSet()
    override val size: Int
        get() = dimensions.width * dimensions.height
    override val values: Collection<Tile>
        get() = arr.filterNotNull()

    override fun containsKey(key: Position) = arr[key.index] != null

    override fun containsValue(value: Tile) = arr.contains(value)

    override fun get(key: Position) = arr[key.index]

    override fun isEmpty() = arr.isEmpty()

    fun createCopy() = ArrayBackedTileMap(
            dimensions = dimensions,
            arr = arr.copyOf()
    )

    private val Position.index: Int
        get() = dimensions.width * y + x

    private val Int.pos: Position
        get() {
            val y = this / dimensions.height
            return Position.create(this - y, y)
        }

    class Entry(
            override val key: Position,
            override val value: Tile
    ) : Map.Entry<Position, Tile>

}