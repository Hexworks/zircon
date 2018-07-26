package org.codetome.zircon.api.builder.graphics

import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.internal.graphics.InMemoryTextImage

/**
 * Creates [org.codetome.zircon.api.graphics.TextImage]s.
 * Defaults:
 * - Default [Size] is `ONE` (1x1).
 * - Default `filler` is an `EMPTY` character
 */
@Suppress("ArrayInDataClass")
data class TextImageBuilder(
        private var size: Size = Size.one(),
        private var filler: Tile = Tile.empty(),
        private val chars: MutableMap<Position, Tile> = mutableMapOf()) : Builder<TextImage> {

    /**
     * Sets the size for the new [TextImage].
     * Default is 1x1.
     */
    fun size(size: Size) = also {
        this.size = size
    }

    /**
     * The new [TextImage] will be filled by this [Tile].
     * Defaults to `EMPTY`.
     */
    fun filler(filler: Tile) = also {
        this.filler = filler
    }

    /**
     * Adds a [Tile] at the given [Position].
     */
    fun character(position: Position, tile: Tile) = also {
        require(size.containsPosition(position)) {
            "The given character's position ($position) is out create bounds for text image size: $size."
        }
        chars[position] = tile
    }

    override fun build(): TextImage = InMemoryTextImage(
            size = size,
            filler = filler,
            chars = chars)

    override fun createCopy() = copy()

    companion object {
        /**
         * Creates a new [TextImageBuilder] to build [org.codetome.zircon.api.graphics.TextImage]s.
         */
        fun newBuilder() = TextImageBuilder()
    }
}
