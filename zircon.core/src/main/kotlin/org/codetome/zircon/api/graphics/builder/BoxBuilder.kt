package org.codetome.zircon.api.graphics.builder

import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.graphics.Box
import org.codetome.zircon.api.graphics.BoxType
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.internal.graphics.DefaultBox

data class BoxBuilder(private var size: Size = Size.create(3, 3),
                      private var style: StyleSet = StyleSetBuilder.defaultStyle(),
                      private var boxType: BoxType = BoxType.BASIC,
                      private var filler: Char = TextCharacter.empty().getCharacter()) : Builder<Box> {

    /**
     * Sets the size for the new [org.codetome.zircon.api.graphics.Box].
     * Default is 3x3.
     */
    fun size(size: Size) = also {
        this.size = size
    }

    /**
     * Sets the style for the resulting [org.codetome.zircon.api.graphics.Box].
     */
    fun style(style: StyleSet) = also {
        this.style = style
    }

    /**
     * The new [org.codetome.zircon.api.graphics.Box] will be filled by this [Char].
     * Defaults to `EMPTY` character.
     */
    fun filler(filler: Char) = also {
        this.filler = filler
    }

    /**
     * Sets the [BoxType] for the resulting [org.codetome.zircon.api.graphics.Box].
     */
    fun boxType(boxType: BoxType) = also {
        this.boxType = boxType
    }

    override fun build(): Box = DefaultBox(
            size = size,
            filler = TextCharacterBuilder.newBuilder()
                    .styleSet(style)
                    .character(filler)
                    .build(),
            styleSet = style,
            boxType = boxType)

    override fun createCopy() = copy()

    companion object {

        /**
         * Creates a new [BoxBuilder] to build [org.codetome.zircon.api.graphics.Box]es.
         */
        fun newBuilder() = BoxBuilder()

    }
}
