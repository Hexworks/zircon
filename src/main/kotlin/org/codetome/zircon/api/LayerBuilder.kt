package org.codetome.zircon.api

import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.graphics.layer.DefaultLayer
import org.codetome.zircon.graphics.layer.Layer

class LayerBuilder {

    private var size: Size = Size.ONE
    private var filler: TextCharacter = TextCharacterBuilder.EMPTY
    private var offset: Position = Position.DEFAULT_POSITION

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

    fun build(): Layer = DefaultLayer(
            size = size,
            filler = filler,
            offset = offset)

    companion object {

        fun newBuilder() = LayerBuilder()
    }
}