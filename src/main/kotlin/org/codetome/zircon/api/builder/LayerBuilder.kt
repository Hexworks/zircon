package org.codetome.zircon.api.builder

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.internal.font.impl.FontSettings
import org.codetome.zircon.internal.graphics.DefaultLayer
import java.awt.image.BufferedImage
import java.util.*

/**
 * Use this to build [Layer]s. Defaults are:
 * - size: [Size.ONE]
 * - filler: [TextCharacterBuilder.EMPTY]
 * - offset: [Position.DEFAULT_POSITION]
 * - has no text image by default
 */
data class LayerBuilder(private var font: Font<BufferedImage> = FontSettings.NO_FONT,
                        private var size: Size = Size.ONE,
                        private var filler: TextCharacter = TextCharacterBuilder.EMPTY,
                        private var offset: Position = Position.DEFAULT_POSITION,
                        private var textImage: Optional<TextImage> = Optional.empty()) : Builder<Layer> {


    /**
     * Sets the [Font] to use with the resulting [Layer].
     */
    fun font(font: Font<BufferedImage>) = also {
        this.font = font
    }

    /**
     * Sets the size for the new [org.codetome.zircon.api.graphics.Layer].
     * Default is 1x1.
     */
    fun size(size: Size) = also {
        this.size = size
    }

    /**
     * The new [org.codetome.zircon.api.graphics.Layer] will be filled by this [TextCharacter].
     * Defaults to `EMPTY`.
     */
    fun filler(filler: TextCharacter) = also {
        this.filler = filler
    }

    /**
     * Sets the `offset` for the new [org.codetome.zircon.api.graphics.Layer].
     * Default is 0x0.
     */
    fun offset(offset: Position) = also {
        this.offset = offset
    }

    /**
     * Uses the given [TextImage] and converts it to a [Layer].
     */
    fun textImage(textImage: TextImage) = also {
        this.textImage = Optional.of(textImage)
    }

    override fun build(): Layer = if (textImage.isPresent) {
        DefaultLayer(size = size,
                filler = filler,
                offset = offset,
                textImage = textImage.get(),
                initialFont = font)
    } else {
        DefaultLayer(
                size = size,
                filler = filler,
                offset = offset,
                initialFont = font)
    }

    override fun createCopy() = copy()

    companion object {

        @JvmStatic
        fun newBuilder() = LayerBuilder()
    }
}