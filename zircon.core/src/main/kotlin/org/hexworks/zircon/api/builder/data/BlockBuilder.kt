package org.hexworks.zircon.api.builder.data

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.data.BlockSide.*
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
@Suppress("UNCHECKED_CAST")
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

    fun position(position: Position) = also {
        this.position = Position3D.from2DPosition(position)
    }

    fun position(position: Position3D) = also {
        this.position = position
    }

    fun side(blockSide: BlockSide, tile: Tile) = also {
        when (blockSide) {
            TOP -> top(tile)
            BOTTOM -> bottom(tile)
            LEFT -> left(tile)
            RIGHT -> right(tile)
            FRONT -> front(tile)
            BACK -> back(tile)
        }
    }

    fun top(top: Tile) = also {
        this.top = Maybe.of(top)
    }

    fun bottom(bottom: Tile) = also {
        this.bottom = Maybe.of(bottom)
    }

    fun front(front: Tile) = also {
        this.front = Maybe.of(front)
    }

    fun back(back: Tile) = also {
        this.back = Maybe.of(back)
    }

    fun left(left: Tile) = also {
        this.left = Maybe.of(left)
    }

    fun right(right: Tile) = also {
        this.right = Maybe.of(right)
    }

    fun layer(layer: Tile) = also {
        require(currLayerIdx < layerCount) {
            "Can't add more layers to this Block, it has already reached maximum."
        }
        this.layers.add(layer)
        currLayerIdx++
    }

    fun layers(layers: List<Tile>) = also {
        layerCount = layers.size
        currLayerIdx = layerCount - 1
        this.layers.clear()
        this.layers.addAll(layers)
    }

    fun layers(vararg layers: Tile) = also {
        layers(layers.toList())
    }

    fun layerCount(layerCount: Int) = also {
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
