package org.codetome.zircon.api

import org.codetome.zircon.TextCharacter
import org.codetome.zircon.graphics.DefaultTextImage
import org.codetome.zircon.terminal.Size

class TextImageBuilder {

    private var size: Size = Size.ONE
    private var toCopy: Array<Array<TextCharacter>> = arrayOf()
    private var filler: TextCharacter = TextCharacter.DEFAULT_CHARACTER

    /**
     * Sets the size for the new [org.codetome.zircon.graphics.TextImage].
     * Default is 1x1.
     */
    fun size(size: Size) = also {
        this.size = size
    }

    /**
     * 2d array of [TextCharacter]s to copy into the new [org.codetome.zircon.graphics.TextImage].
     * If <code>toCopy</code> is bigger than the new text image only the relevant parts will
     * be copied. If it is smaller the remaining cells will be filled by the <code>filler</code> char.
     * Default is an empty array.
     */
    fun toCopy(toCopy: Array<Array<TextCharacter>>) = also {
        this.toCopy = toCopy
    }

    /**
     * The new [org.codetome.zircon.graphics.TextImage] will be filled by this [TextCharacter].
     * Defaults to ' ' (space).
     */
    fun filler(filler: TextCharacter) = also {
        this.filler = filler
    }

    fun build() = DefaultTextImage(
            size = size,
            toCopy = toCopy,
            filler = filler)

    companion object {

        @JvmStatic
        fun newBuilder() = TextImageBuilder()
    }
}