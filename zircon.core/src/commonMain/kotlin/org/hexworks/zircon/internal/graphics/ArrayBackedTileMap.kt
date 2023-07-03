package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile

class ArrayBackedTileMap(
    private val dimensions: Size,
    private val arr: Array<Map.Entry<Position, Tile>?>
) : AbstractMap<Position, Tile>() {

    override val entries: Set<Map.Entry<Position, Tile>>
        get() = arr.asSequence().filterNotNull().toSet()

    override val size: Int
        get() = dimensions.width * dimensions.height

    override val keys: Set<Position>
        get() = arr.asSequence().filterNotNull().map { it.key }.toSet()

    override val values: Collection<Tile>
        get() = arr.asSequence().filterNotNull().map { it.value }.toList()

    override fun containsKey(key: Position) = get(key) != null

    override fun containsValue(value: Tile) = arr.any { it?.value == value }

    override fun get(key: Position) = key.index?.let { arr[it]?.value }

    override fun isEmpty() = arr.isEmpty()

    fun contents(): Sequence<Map.Entry<Position, Tile>> = sequence {
        for (i in arr.indices) {
            arr[i]?.let { entry ->
                yield(entry)
            }
        }
    }

    fun createCopy() = ArrayBackedTileMap(
        dimensions = dimensions,
        arr = arr.copyOf()
    )

    private val Position.index: Int?
        get() {
            return if (dimensions.containsPosition(this)) {
                val idx = dimensions.width * y + x;
                if (idx < arr.lastIndex || idx >= 0) idx else null
            } else null
        }

    class Entry(
        override val key: Position,
        override val value: Tile
    ) : Map.Entry<Position, Tile>

}
