package org.hexworks.zircon.api.builder.graphics

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.graphics.FastTileGraphics
import kotlin.jvm.JvmStatic

/**
 * Creates [org.hexworks.zircon.api.graphics.TileGraphics]s.
 * Defaults:
 * - Default [Size] is `ONE` (1x1).
 * - Default `filler` is an `EMPTY` character
 */
@Suppress("ArrayInDataClass")
class TileGraphicsBuilder private constructor(
    var tileset: TilesetResource = RuntimeConfig.config.defaultTileset,
    var size: Size = Size.one(),
    var style: StyleSet = StyleSet.defaultStyle(),
    var tiles: Map<Position, Tile> = mapOf(),
    var filler: Tile = Tile.empty()
) : Builder<TileGraphics> {

    fun withTileset(tileset: TilesetResource) = also {
        this.tileset = tileset
    }

    /**
     * Sets the size for the new [TileGraphics].
     * Default is 1x1.
     */
    fun withSize(width: Int, height: Int) = withSize(Size.create(width, height))

    /**
     * Sets the size for the new [TileGraphics].
     * Default is 1x1.
     */
    fun withSize(size: Size) = also {
        this.size = size
    }

    /**
     * Sets the filler for the new [TileGraphics] which
     * will be used to fill the empty spaces. Default is
     * [Tile.empty] which means no filling
     */
    fun withFiller(filler: Tile) = also {
        this.filler = filler
    }

    /**
     * Adds a [Tile] at the given [Position].
     */
    fun withTile(position: Position, tile: Tile) = also {
        if (size.containsPosition(position)) {
            tiles = tiles + (position to tile)
        }
    }

    /**
     * Sets the given [tiles] to be used for the new [TileGraphics].
     */
    fun withTiles(tiles: Map<Position, Tile>) = also {
        this.tiles = this.tiles + tiles
    }

    /**
     * Builds a [FastTileGraphics] implementation.
     */
    override fun build(): TileGraphics = FastTileGraphics(
        initialSize = size,
        initialTileset = tileset,
        initialTiles = tiles
    ).apply {
        if (hasToFill()) fill(filler)
    }

    override fun createCopy() = TileGraphicsBuilder(
        tileset = tileset,
        size = size,
        style = style,
        tiles = tiles.toMutableMap(),
        filler = filler
    )

    private fun hasToFill() = filler != Tile.empty()

    companion object {

        /**
         * Creates a new [TileGraphicsBuilder] to build [TileGraphics] objects.
         */
        @JvmStatic
        fun newBuilder() = TileGraphicsBuilder()
    }
}
