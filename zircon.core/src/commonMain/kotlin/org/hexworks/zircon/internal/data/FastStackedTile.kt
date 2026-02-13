package org.hexworks.zircon.internal.data


import org.hexworks.zircon.api.data.StackedTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.TileType
import org.hexworks.zircon.api.modifier.Modifier

/**
 * This **fast** [StackedTile] implementation foregoes validation and it allows mutation of its state.
 * Only use it if you need the speed and you can be really cautious.
 */
//! TODO: check implementation
class FastStackedTile(
    initialTiles: List<Tile> = listOf(),
    initialCapacity: Int = initialTiles.size,

) : StackedTile {

    override val modifiers: Set<Modifier>
        get() = top.modifiers

    override fun withModifiers(modifiers: Set<Modifier>): StackedTile = also {
        setLast(top.withModifiers(modifiers))
    }

    private val actualTiles = ArrayDeque<Tile>(initialCapacity).apply {
        addAll(initialTiles)
    }

    override val rest: List<Tile> = actualTiles.drop(1)

    override val tiles: List<Tile> = actualTiles

    override val baseTile: Tile
        get() = actualTiles.first()

    override val top: Tile
        get() = tiles.last()

    fun addFirst(tile: Tile) {
        actualTiles.addFirst(tile)
    }

    fun addLast(tile: Tile) {
        actualTiles.addLast(tile)
    }

    fun setLast(tile: Tile) {
        actualTiles.removeLast()
        actualTiles.addLast(tile)
    }

    // delegation
    override val tileType: TileType
        get() = baseTile.tileType

    override val cacheKey: String
        get() = baseTile.cacheKey


}
