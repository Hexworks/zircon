package org.hexworks.zircon.api.builder.graphics

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.builder.data.CharacterTileBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.extensions.containsPosition
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.extensions.fill
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.config.ZirconDefaults
import org.hexworks.zircon.internal.graphics.FastTileGraphics

/**
 * Creates [org.hexworks.zircon.api.graphics.TileGraphics]s.
 * Defaults:
 * - Default [Size] is `ONE` (1x1).
 * - Default `filler` is an `EMPTY` character
 */
@ZirconDsl
public class TileGraphicsBuilder : Builder<TileGraphics> {

    public var tileset: TilesetResource = ZirconDefaults.current.tileset
    public var size: Size = Size.ONE
    public var tiles: Map<Position, Tile> = mapOf()
    public var filler: Tile = Tile.empty()

    /**
     * Builds a [FastTileGraphics] implementation.
     */
    override fun build(): TileGraphics {
        // Checked here, against the final size, whatever order size and tiles were set in.
        tiles.keys.forEach { pos ->
            require(size.containsPosition(pos)) {
                "Position ($pos) is outside of the bounds of this tile graphics ($size)"
            }
        }
        return FastTileGraphics(
            initialSize = size,
            initialTileset = tileset,
            initialTiles = tiles
        ).apply {
            if (hasToFill()) fill(filler)
        }
    }

    private fun hasToFill() = filler != Tile.empty()
}

public fun tileGraphics(init: TileGraphicsBuilder.() -> Unit): TileGraphics =
    TileGraphicsBuilder().apply(init).build()


public fun TileGraphicsBuilder.withFiller(init: CharacterTileBuilder.() -> Unit): TileGraphicsBuilder = apply {
    filler = CharacterTileBuilder().apply(init).build()
}