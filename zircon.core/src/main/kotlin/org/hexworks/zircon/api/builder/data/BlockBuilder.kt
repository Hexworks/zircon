package org.hexworks.zircon.api.builder.data

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.data.BlockSide.*
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.kotlin.map
import org.hexworks.zircon.api.util.Maybe
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
data class BlockBuilder(
        private var position: Position3D = Position3D.unknown(),
        private var top: Maybe<Tile> = Maybe.empty(),
        private var bottom: Maybe<Tile> = Maybe.empty(),
        private var front: Maybe<Tile> = Maybe.empty(),
        private var back: Maybe<Tile> = Maybe.empty(),
        private var left: Maybe<Tile> = Maybe.empty(),
        private var right: Maybe<Tile> = Maybe.empty(),
        private var layers: MutableList<Tile> = mutableListOf(),
        private var layerCount: Int = 1) : Builder<Block> {

    private var currLayerIdx = 0

    fun withPosition(position: Position) = also {
        this.position = Position3D.from2DPosition(position)
    }

    fun withPosition(position: Position3D) = also {
        this.position = position
    }

    fun withSide(blockSide: BlockSide, tile: Tile) = also {
        when (blockSide) {
            TOP -> withTop(tile)
            BOTTOM -> withBottom(tile)
            LEFT -> withLeft(tile)
            RIGHT -> withRight(tile)
            FRONT -> withFront(tile)
            BACK -> withBack(tile)
        }
    }

    fun withTop(top: Tile) = also {
        this.top = Maybe.of(top)
    }

    fun withBottom(bottom: Tile) = also {
        this.bottom = Maybe.of(bottom)
    }

    fun withFront(front: Tile) = also {
        this.front = Maybe.of(front)
    }

    fun withBack(back: Tile) = also {
        this.back = Maybe.of(back)
    }

    fun withLeft(left: Tile) = also {
        this.left = Maybe.of(left)
    }

    fun withRight(right: Tile) = also {
        this.right = Maybe.of(right)
    }

    fun addLayer(layer: Tile) = also {
        require(currLayerIdx < layerCount) {
            "Can't add more layers to this Block, it has already reached maximum."
        }
        this.layers.add(layer)
        currLayerIdx++
    }

    fun withLayers(layers: List<Tile>) = also {
        layerCount = layers.size
        currLayerIdx = layerCount - 1
        this.layers.clear()
        this.layers.addAll(layers)
    }

    fun withLayers(vararg layers: Tile) = also {
        withLayers(layers.toList())
    }

    fun withLayerCount(layerCount: Int) = also {
        this.layerCount = layerCount
    }

    override fun build(): Block {
        require(position !== Position3D.unknown()) {
            "Can't build a Block with a missing Position3D."
        }
        require(layers.size <= layerCount) {
            "Can't have more layers in a Block than the expected layer count."
        }
        val sides = mutableMapOf<BlockSide, Tile>()
        listOf(TOP to top, BOTTOM to bottom, LEFT to left, RIGHT to right, FRONT to front, BACK to back).forEach { pair ->
            pair.second.map {
                sides[pair.first] = it
            }
        }
        return DefaultBlock(
                position = position,
                layers = layers.toMutableList(),
                sides = sides.toMap())
    }

    override fun createCopy() = copy()

    companion object {

        /**
         * Creates a new [BlockBuilder] for creating [Tile]s.
         */
        fun create() = BlockBuilder()

    }
}
