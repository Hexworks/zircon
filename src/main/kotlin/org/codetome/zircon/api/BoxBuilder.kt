package org.codetome.zircon.api

import org.codetome.zircon.Size
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.graphics.box.BoxType
import org.codetome.zircon.graphics.box.DefaultBox
import org.codetome.zircon.graphics.style.StyleSet

class BoxBuilder {

    private var size: Size = Size(3, 3)
    private var style: StyleSet = StyleSetBuilder.DEFAULT_STYLE
    private var boxType: BoxType = BoxType.BASIC
    private var filler: Char = TextCharacterBuilder.EMPTY.getCharacter()

    /**
     * Sets the size for the new [org.codetome.zircon.graphics.box.Box].
     * Default is 3x3.
     */
    fun size(size: Size) = also {
        this.size = size
    }

    /**
     * Sets the style for the resulting [org.codetome.zircon.graphics.box.Box].
     */
    fun style(style: StyleSet) = also {
        this.style = style
    }

    /**
     * The new [org.codetome.zircon.graphics.box.Box] will be filled by this [Char].
     * Defaults to `EMPTY` character.
     */
    fun filler(filler: Char) = also {
        this.filler = filler
    }

    /**
     * Sets the [BoxType] for the resulting [org.codetome.zircon.graphics.box.Box].
     */
    fun boxType(boxType: BoxType) = also {
        this.boxType = boxType
    }

    fun build() = DefaultBox(
            size = size,
            filler = TextCharacterBuilder.newBuilder()
                    .styleSet(style)
                    .character(filler)
                    .build(),
            styleSet = style,
            boxType = boxType)

    companion object {

        /**
         * Creates a new [BoxBuilder] to build [org.codetome.zircon.graphics.box.Box]es.
         */
        @JvmStatic
        fun newBuilder() = BoxBuilder()

    }
}