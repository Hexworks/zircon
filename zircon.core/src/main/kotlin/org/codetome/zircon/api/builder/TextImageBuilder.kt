package org.codetome.zircon.api.builder

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.internal.graphics.MapLikeTextImage

/**
 * Creates [org.codetome.zircon.api.graphics.TextImage]s.
 * Defaults:
 * - Default [Size] is `ONE` (1x1).
 * - Default `filler` is an `EMPTY` character
 */
@Suppress("ArrayInDataClass")
data class TextImageBuilder(
        private var size: Size = Size.ONE,
        private var filler: TextCharacter = TextCharacterBuilder.EMPTY,
        private val chars: MutableMap<Position, TextCharacter> = mutableMapOf()) : Builder<TextImage> {

    /**
     * Sets the size for the new [TextImage].
     * Default is 1x1.
     */
    fun size(size: Size) = also {
        this.size = size
    }

    /**
     * The new [TextImage] will be filled by this [TextCharacter].
     * Defaults to `EMPTY`.
     */
    fun filler(filler: TextCharacter) = also {
        this.filler = filler
    }

    /**
     * Adds a [TextCharacter] at the given [Position].
     */
    fun character(position: Position, textCharacter: TextCharacter) = also {
        require(size.containsPosition(position))
        chars[position] = textCharacter
    }

    override fun build(): TextImage = MapLikeTextImage(
            size = size,
            filler = filler,
            chars = chars)

    override fun createCopy() = copy()

    companion object {

        /**
         * Creates a new [TextImageBuilder] to build [org.codetome.zircon.api.graphics.TextImage]s.
         */
        @JvmStatic
        fun newBuilder() = TextImageBuilder()

    }
}
