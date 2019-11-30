package org.hexworks.zircon.api.builder.graphics

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.graphics.ThreadSafeLayer

/**
 * Use this to build [Layer]s. Defaults are:
 * - size: [Size.one()]
 * - filler: [Tile.empty()]
 * - offset: [Position.defaultPosition()]
 * - has no text image by default
 */
data class LayerBuilder(
        private var tileset: TilesetResource = RuntimeConfig.config.defaultTileset,
        private var size: Size = Size.defaultGridSize(),
        private var offset: Position = Position.defaultPosition(),
        private var tileGraphics: Maybe<TileGraphics> = Maybe.empty(),
        private var filler: Tile = Tile.empty()) : Builder<Layer> {

    /**
     * Sets the [Tileset] to use with the resulting [Layer].
     */
    fun withTileset(tileset: TilesetResource) = also {
        this.tileset = tileset
    }

    /**
     * Sets the size for the new [org.hexworks.zircon.api.graphics.Layer].
     * Default is 1x1.
     */
    fun withSize(size: Size) = also {
        this.size = size
    }

    /**
     * Sets the size for the new [org.hexworks.zircon.api.graphics.Layer].
     * Default is 1x1.
     */
    fun withSize(width: Int, height: Int) = withSize(Size.create(width, height))

    /**
     * Sets the `offset` for the new [org.hexworks.zircon.api.graphics.Layer].
     * Default is 0x0.
     */
    fun withOffset(offset: Position) = also {
        this.offset = offset
    }

    /**
     * Sets the `offset` for the new [org.hexworks.zircon.api.graphics.Layer].
     * Default is 0x0.
     */
    fun withOffset(x: Int, y: Int) = also {
        this.offset = Position.create(x, y)
    }

    /**
     * Uses the given [TileGraphics] and converts it to a [Layer].
     */
    fun withTileGraphics(tileGraphics: TileGraphics) = also {
        this.tileGraphics = Maybe.of(tileGraphics)
    }

    /**
     * Sets the filler for the new [TileGraphics] which
     * will be used to fill the empty spaces. Default is
     * [Tile.empty] which means no filling
     */
    fun withFiller(filler: Tile) = also {
        this.filler = filler
    }

    override fun build(): Layer = if (tileGraphics.isPresent) {
        ThreadSafeLayer(
                initialPosition = offset,
                initialContents = tileGraphics.get())
    } else {
        ThreadSafeLayer(
                initialPosition = offset,
                initialContents = TileGraphicsBuilder.newBuilder()
                        .withSize(size)
                        .withTileset(tileset)
                        .buildThreadSafeTileGraphics())
    }.apply {
        if (filler != Tile.empty()) fill(filler)
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = LayerBuilder()
    }
}
