package org.hexworks.zircon.api.builder.graphics

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.builder.data.SizeBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileComposite
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.graphics.DefaultTileComposite

/**
 * Creates [TileComposite]s.
 * Defaults:
 * - Default [Size] is `ONE` (1x1).
 */
@ZirconDsl
class TileCompositeBuilder : Builder<TileComposite> {

    var size: Size = Size.one()
        set(value) {
            if (this.size.width > size.width || this.size.height > size.height) {
                removeOutOfBoundsTiles(size)
            }
            field = value
        }

    var tiles: Map<Position, Tile> = mapOf()
        set(value) {
            field = value
            removeOutOfBoundsTiles()
        }

    private fun removeOutOfBoundsTiles(size: Size = this.size) {
        this.tiles = tiles
            .filterKeys { size.containsPosition(it) }
            .toMap()
    }

    override fun build(): TileComposite {
        return DefaultTileComposite(tiles, size)
    }
}

/**
 * Creates a new [TileCompositeBuilder] using the builder DSL and returns it.
 */
fun tileComposite(init: TileCompositeBuilder.() -> Unit): TileComposite =
    TileCompositeBuilder().apply(init).build()

fun TileCompositeBuilder.withSize(init: SizeBuilder.() -> Unit) = apply {
    size = SizeBuilder().apply(init).build()
}
