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

    override fun get(key: Position): Tile? = key.index?.let { arr[it]?.value }

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

    private val Position.index: Int?
        get() = if (x in 0 until dimensions.width && y in 0 until dimensions.height) {
            dimensions.width * y + x
        } else null

    public class Entry(
        override val key: Position,
        override val value: Tile
    ) : Map.Entry<Position, Tile>

}
