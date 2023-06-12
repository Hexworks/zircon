package org.hexworks.zircon.api.builder.graphics

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.builder.data.CharacterTileBuilder
import org.hexworks.zircon.api.builder.data.SizeBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.graphics.FastTileGraphics

/**
 * Creates [org.hexworks.zircon.api.graphics.TileGraphics]s.
 * Defaults:
 * - Default [Size] is `ONE` (1x1).
 * - Default `filler` is an `EMPTY` character
 */
@ZirconDsl
class TileGraphicsBuilder : Builder<TileGraphics> {

    var tileset: TilesetResource = RuntimeConfig.config.defaultTileset
    var size: Size = Size.one()
    var tiles: Map<Position, Tile> = mapOf()
        set(value) {
            tiles.forEach { (pos, _) ->
                require(size.containsPosition(pos)) {
                    "Position ($pos) is outside of the bounds of this tile graphics ($size)"
                }
            }
            field = value
        }
    var filler: Tile = Tile.empty()

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

    private fun hasToFill() = filler != Tile.empty()
}

fun tileGraphics(init: TileGraphicsBuilder.() -> Unit) =
    TileGraphicsBuilder().apply(init).build()

fun TileGraphicsBuilder.withSize(init: SizeBuilder.() -> Unit) = apply {
    size = SizeBuilder().apply(init).build()
}

fun TileGraphicsBuilder.withFiller(init: CharacterTileBuilder.() -> Unit) = apply {
    filler = CharacterTileBuilder().apply(init).build()
}