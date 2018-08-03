package org.codetome.zircon.api.builder.graphics

import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.graphics.TileImage
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.util.Maybe
import org.codetome.zircon.internal.graphics.DefaultLayer

/**
 * Use this to build [Layer]s. Defaults are:
 * - size: [Size.one()]
 * - filler: [Tile.empty()]
 * - offset: [Position.defaultPosition()]
 * - has no text image by default
 */
data class LayerBuilder(
        private var tileset: Maybe<TilesetResource<out Tile>> = Maybe.empty(),
        private var size: Size = Size.defaultTerminalSize(),
        private var offset: Position = Position.defaultPosition(),
        private var tileImage: Maybe<TileImage> = Maybe.empty()) : Builder<Layer> {

    /**
     * Sets the [Tileset] to use with the resulting [Layer].
     */
    fun font(tileset: TilesetResource<out Tile>) = also {
        this.tileset = Maybe.of(tileset)
    }

    /**
     * Sets the size for the new [org.codetome.zircon.api.graphics.Layer].
     * Default is 1x1.
     */
    fun size(size: Size) = also {
        this.size = size
    }

    /**
     * Sets the `offset` for the new [org.codetome.zircon.api.graphics.Layer].
     * Default is 0x0.
     */
    fun offset(offset: Position) = also {
        this.offset = offset
    }

    /**
     * Uses the given [TileImage] and converts it to a [Layer].
     */
    fun textImage(tileImage: TileImage) = also {
        this.tileImage = Maybe.of(tileImage)
    }

    override fun build(): Layer = if (tileImage.isPresent) {
        DefaultLayer(
                position = offset,
                backend = tileImage.get())
    } else {
        DefaultLayer(
                position = offset,
                backend = TileImageBuilder(
                        tileset = tileset.get(),
                        size = size).build())
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = LayerBuilder()
    }
}
