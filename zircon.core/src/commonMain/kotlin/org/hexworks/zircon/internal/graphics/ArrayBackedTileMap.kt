package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile

public class ArrayBackedTileMap(
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

    override fun containsKey(key: Position): Boolean = get(key) != null

    override fun containsValue(value: Tile): Boolean = arr.any { it?.value == value }

    private val width = dimensions.width
    private val height = dimensions.height

    override fun get(key: Position): Tile? {
        val index = indexOf(key)
        return if (index < 0) null else arr[index]?.value
    }

    override fun isEmpty(): Boolean = arr.isEmpty()

    public fun contents(): Sequence<Map.Entry<Position, Tile>> = sequence {
        for (i in arr.indices) {
            arr[i]?.let { entry ->
                yield(entry)
            }
        }
    }

    public fun createCopy(): ArrayBackedTileMap = ArrayBackedTileMap(
        dimensions = dimensions,
        arr = arr.copyOf()
    )

    // Returns -1 when out of bounds. An `Int?` here would box on every lookup, and the
    // renderer does one lookup per cell per layer per frame.
    private fun indexOf(position: Position): Int {
        val x = position.x
        val y = position.y
        return if (x < 0 || y < 0 || x >= width || y >= height) -1 else width * y + x
    }

    public class Entry(
        override val key: Position,
        override val value: Tile
    ) : Map.Entry<Position, Tile>

}
