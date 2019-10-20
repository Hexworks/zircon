package org.hexworks.zircon.api.builder.data

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.BlockTileType.*
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.data.DefaultBlock

/**
 * Builds [Tile]s.
 * Defaults:
 * - Default character is a space
 * - Default modifiers is an empty set
 * also
 * @see [org.hexworks.zircon.api.color.TileColor] to check default colors.
 */
@Suppress("UNCHECKED_CAST", "MemberVisibilityCanBePrivate")
data class BlockBuilder<T : Tile>(
        private var emptyTile: Maybe<T> = Maybe.empty(),
        private var top: Maybe<T> = Maybe.empty(),
        private var bottom: Maybe<T> = Maybe.empty(),
        private var front: Maybe<T> = Maybe.empty(),
        private var back: Maybe<T> = Maybe.empty(),
        private var left: Maybe<T> = Maybe.empty(),
        private var right: Maybe<T> = Maybe.empty(),
        private var content: Maybe<T> = Maybe.empty()) : Builder<Block<T>> {

    private var currLayerIdx = 0

    fun withEmptyTile(tile: T) = also {
        this.emptyTile = Maybe.of(tile)
    }

    fun withTop(top: T) = also {
        this.top = Maybe.of(top)
    }

    fun withBottom(bottom: T) = also {
        this.bottom = Maybe.of(bottom)
    }

    fun withFront(front: T) = also {
        this.front = Maybe.of(front)
    }

    fun withBack(back: T) = also {
        this.back = Maybe.of(back)
    }

    fun withLeft(left: T) = also {
        this.left = Maybe.of(left)
    }

    fun withRight(right: T) = also {
        this.right = Maybe.of(right)
    }

    fun withContent(content: T) = also {
        this.content = Maybe.of(content)
    }

    override fun build(): Block<T> {
        require(emptyTile.isPresent) {
            "No empty tile supplied."
        }
        val initialTiles = mutableMapOf<BlockTileType, T>()
        listOf(TOP to top, BOTTOM to bottom, LEFT to left, RIGHT to right, FRONT to front, BACK to back)
                .map { (type, maybeTile) ->
                    maybeTile.map {
                        initialTiles[type] = it
                    }
                }
        return DefaultBlock(
                emptyTile = emptyTile.get(),
                initialTiles = initialTiles)
    }

    override fun createCopy() = copy()

    companion object {

        /**
         * Creates a new [BlockBuilder] for creating [Tile]s.
         */
        fun <T : Tile> newBuilder() = BlockBuilder<T>()

    }
}
