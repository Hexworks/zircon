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

    override fun containsKey(key: Position) = dimensions.containsPosition(key)

    override fun containsValue(value: Tile) = arr.any { it?.value == value }

    override fun get(key: Position) = if (dimensions.containsPosition(key)) arr[key.index]?.value else null

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

    private val Position.index: Int
        get() = dimensions.width * y + x

    class Entry(
        override val key: Position,
        override val value: Tile
    ) : Map.Entry<Position, Tile>

}
