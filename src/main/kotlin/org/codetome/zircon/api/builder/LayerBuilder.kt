package org.codetome.zircon.api.builder

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.internal.graphics.DefaultLayer
import org.codetome.zircon.api.graphics.Layer
import java.util.*

class LayerBuilder : Builder<Layer> {

    private var size: Size = Size.ONE
    private var filler: TextCharacter = TextCharacterBuilder.EMPTY
    private var offset: Position = Position.DEFAULT_POSITION
    private var textImage: Optional<TextImage> = Optional.empty()

    /**
     * Sets the size for the new [org.codetome.zircon.graphics.layer.Layer].
     * Default is 1x1.
     */
    fun size(size: Size) = also {
        this.size = size
    }

    /**
     * The new [org.codetome.zircon.graphics.layer.Layer] will be filled by this [TextCharacter].
     * Defaults to `EMPTY`.
     */
    fun filler(filler: TextCharacter) = also {
        this.filler = filler
    }

    /**
     * Sets the `offset` for the new [org.codetome.zircon.graphics.layer.Layer].
     * Default is 0x0.
     */
    fun offset(offset: Position) = also {
        this.offset = offset
    }

    fun textImage(textImage: TextImage) = also {
        this.textImage = Optional.of(textImage)
    }

    override fun build(): Layer = if (textImage.isPresent) {
        DefaultLayer(size = size,
                filler = filler,
                offset = offset,
                textImage = textImage.get())
    } else {
        DefaultLayer(
                size = size,
                filler = filler,
                offset = offset)
    }

    companion object {

        fun newBuilder() = LayerBuilder()
    }
}