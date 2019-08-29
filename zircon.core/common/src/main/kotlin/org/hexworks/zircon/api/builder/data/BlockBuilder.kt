package org.hexworks.zircon.api.builder.data

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.BlockSide
import org.hexworks.zircon.api.data.BlockSide.BACK
import org.hexworks.zircon.api.data.BlockSide.BOTTOM
import org.hexworks.zircon.api.data.BlockSide.FRONT
import org.hexworks.zircon.api.data.BlockSide.LEFT
import org.hexworks.zircon.api.data.BlockSide.RIGHT
import org.hexworks.zircon.api.data.BlockSide.TOP
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
        private var layers: MutableList<T> = mutableListOf(),
        private var layerCount: Int = 1) : Builder<Block<T>> {

    private var currLayerIdx = 0

    fun withEmptyTile(tile: T) = also {
        this.emptyTile = Maybe.of(tile)
    }

    fun withSide(blockSide: BlockSide, tile: T) = also {
        when (blockSide) {
            TOP -> withTop(tile)
            BOTTOM -> withBottom(tile)
            LEFT -> withLeft(tile)
            RIGHT -> withRight(tile)
            FRONT -> withFront(tile)
            BACK -> withBack(tile)
        }
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

    fun addLayer(layer: T) = also {
        require(currLayerIdx < layerCount) {
            "Can't add more layers to this Block, it has already reached maximum."
        }
        this.layers.add(layer)
        currLayerIdx++
    }

    fun withLayers(layers: List<T>) = also {
        layerCount = layers.size
        currLayerIdx = layerCount - 1
        this.layers.clear()
        this.layers.addAll(layers)
    }

    fun withLayers(vararg layers: T) = also {
        withLayers(layers.toList())
    }

    fun withLayerCount(layerCount: Int) = also {
        this.layerCount = layerCount
    }

    override fun build(): Block<T> {
        require(layers.size <= layerCount) {
            "Can't have more layers in a Block than the expected layer count."
        }
        require(emptyTile.isPresent) {
            "No empty tile supplied"
        }
        val sides = mutableMapOf<BlockSide, T>()
        listOf(TOP to top, BOTTOM to bottom, LEFT to left, RIGHT to right, FRONT to front, BACK to back).forEach { pair ->
            pair.second.map {
                sides[pair.first] = it
            }
        }
        return DefaultBlock(
                layers = layers.toMutableList(),
                sides = sides.toMutableMap(),
                emptyTile = emptyTile.get())
    }

    override fun createCopy() = copy()

    companion object {

        /**
         * Creates a new [BlockBuilder] for creating [Tile]s.
         */
        fun <T : Tile> newBuilder() = BlockBuilder<T>()

    }
}
