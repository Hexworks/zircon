package org.hexworks.zircon.api.builder.data

import kotlinx.collections.immutable.toPersistentMap
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.BlockTileType.*
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.data.DefaultBlock
import org.hexworks.zircon.internal.dsl.ZirconDsl

/**
 * Builds [Block]s.
 * Has no [Tile]s for either sides by default.
 * Setting an [emptyTile] is **mandatory** and has no default.
 */
@ZirconDsl
class BlockBuilder<T : Tile> : Builder<Block<T>> {

    private var tilesMap: MutableMap<BlockTileType, T> = mutableMapOf()

    var emptyTile: T? = null

    var top: T
        get() = tilesMap[TOP] ?: error("No top tile present")
        set(value) {
            tilesMap[TOP] = value
        }

    var bottom: T
        get() = tilesMap[BOTTOM] ?: error("No bottom tile present")
        set(value) {
            tilesMap[BOTTOM] = value
        }

    var front: T
        get() = tilesMap[FRONT] ?: error("No front tile present")
        set(value) {
            tilesMap[FRONT] = value
        }

    var back: T
        get() = tilesMap[BACK] ?: error("No back tile present")
        set(value) {
            tilesMap[BACK] = value
        }

    var left: T
        get() = tilesMap[LEFT] ?: error("No left tile present")
        set(value) {
            tilesMap[LEFT] = value
        }

    var right: T
        get() = tilesMap[RIGHT] ?: error("No right tile present")
        set(value) {
            tilesMap[RIGHT] = value
        }

    var content: T
        get() = tilesMap[CONTENT] ?: error("No content tile present")
        set(value) {
            tilesMap[CONTENT] = value
        }
    
    var tiles: Map<BlockTileType, T>
        get() = tilesMap.toMap()
        set(value) {
            tilesMap = value.toMutableMap()
        }


    override fun build(): Block<T> {
        requireNotNull(emptyTile) {
            "Can't build block: no empty tile supplied."
        }
        return DefaultBlock(
            emptyTile = emptyTile!!,
            initialTiles = tilesMap.toPersistentMap()
        )
    }
}

/**
 * Creates a new [Block] using the builder DSL and returns it.
 */
fun <T : Tile> block(init: BlockBuilder<T>.() -> Unit): Block<T> {
    return BlockBuilder<T>().apply(init).build()
}
