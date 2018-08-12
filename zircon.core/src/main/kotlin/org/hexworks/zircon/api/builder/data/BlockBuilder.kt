package org.hexworks.zircon.api.builder.data

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position3D
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
@Suppress("UNCHECKED_CAST")
data class BlockBuilder(
        private var position: Position3D = Position3D.unknown(),
        private var top: Tile = Tile.empty(),
        private var back: Tile = Tile.empty(),
        private var front: Tile = Tile.empty(),
        private var bottom: Tile = Tile.empty(),
        private var layers: MutableList<Tile> = mutableListOf()) : Builder<Block> {

    fun position(position: Position) = also {
        this.position = Position3D.from2DPosition(position)
    }

    fun position(position: Position3D) = also {
        this.position = position
    }

    fun top(top: Tile) = also {
        this.top = top
    }

    fun back(back: Tile) = also {
        this.back = back
    }

    fun front(front: Tile) = also {
        this.front = front
    }

    fun bottom(bottom: Tile) = also {
        this.bottom = bottom
    }

    fun layer(layer: Tile) = also {
        this.layers.add(layer)
    }

    fun layers(layers: List<Tile>) = also {
        this.layers.addAll(layers)
    }


    override fun build(): Block {
        require(position !== Position3D.unknown()) {
            "Can't build a Block with a missing Position3D."
        }
        return DefaultBlock(
                position = position,
                top = top,
                back = back,
                front = front,
                bottom = bottom,
                layers = layers.toMutableList())
    }

    override fun createCopy() = copy()

    companion object {

        /**
         * Creates a new [BlockBuilder] for creating [Tile]s.
         */
        fun create() = BlockBuilder()

    }
}
