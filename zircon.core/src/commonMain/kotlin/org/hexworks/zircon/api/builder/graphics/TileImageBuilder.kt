package org.hexworks.zircon.api.builder.graphics

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.builder.data.CharacterTileBuilder
import org.hexworks.zircon.api.builder.data.SizeBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileImage
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.graphics.DefaultTileImage

/**
 * Creates [org.hexworks.zircon.api.graphics.TileGraphics]s.
 * Defaults:
 * - Default [Size] is `ONE` (1x1).
 * - Default `filler` is an `EMPTY` character
 */
@ZirconDsl
class TileImageBuilder : Builder<TileImage> {

    var tileset: TilesetResource = RuntimeConfig.config.defaultTileset
    var filler: Tile = Tile.empty()

    var size: Size = Size.one()

    var tiles: Map<Position, Tile> = mapOf()

    override fun build(): TileImage {
        return DefaultTileImage(
            size = size,
            tileset = tileset,
            initialTiles = tiles.filter { size.containsPosition(it.key) }
        ).withFiller(filler)
    }
}

/**
 * Creates a new [TileImageBuilder] using the builder DSL and returns it.
 */
fun tileImage(init: TileImageBuilder.() -> Unit): TileImage =
    TileImageBuilder().apply(init).build()

fun TileImageBuilder.withSize(init: SizeBuilder.() -> Unit) = apply {
    size = SizeBuilder().apply(init).build()
}

fun TileImageBuilder.withFiller(init: CharacterTileBuilder.() -> Unit) = apply {
    filler = CharacterTileBuilder().apply(init).build()
}
