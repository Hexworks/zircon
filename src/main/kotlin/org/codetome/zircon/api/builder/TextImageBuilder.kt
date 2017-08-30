package org.codetome.zircon.api.builder

import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.internal.graphics.DefaultTextImage

/**
 * Creates [org.codetome.zircon.api.graphics.TextImage]s.
 * Defaults:
 * - Default [Size] is `ONE` (1x1).
 * - Default `toCopy` is an empty array
 * - Default `filler` is an `EMPTY` character
 */
class TextImageBuilder {

    private var size: Size = Size.ONE
    private var toCopy: Array<Array<TextCharacter>> = arrayOf()
    private var filler: TextCharacter = TextCharacterBuilder.EMPTY

    /**
     * Sets the size for the new [org.codetome.zircon.api.graphics.TextImage].
     * Default is 1x1.
     */
    fun size(size: Size) = also {
        this.size = size
    }

    /**
     * 2d array of [TextCharacter]s to copy into the new [org.codetome.zircon.api.graphics.TextImage].
     * If <code>toCopy</code> is bigger than the new title image only the relevant parts will
     * be copied. If it is smaller the remaining cells will be filled by the <code>filler</code> char.
     * Default is an empty array.
     */
    fun toCopy(toCopy: Array<Array<TextCharacter>>) = also {
        this.toCopy = toCopy
    }

    /**
     * The new [org.codetome.zircon.api.graphics.TextImage] will be filled by this [TextCharacter].
     * Defaults to `EMPTY`.
     */
    fun filler(filler: TextCharacter) = also {
        this.filler = filler
    }

    fun build(): TextImage = DefaultTextImage(
            size = size,
            toCopy = toCopy,
            filler = filler)

    companion object {

        /**
         * Creates a new [TextImageBuilder] to build [org.codetome.zircon.api.graphics.TextImage]s.
         */
        @JvmStatic
        fun newBuilder() = TextImageBuilder()

    }
}