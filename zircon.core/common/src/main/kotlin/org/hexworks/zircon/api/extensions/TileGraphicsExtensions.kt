package org.hexworks.zircon.api.extensions

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.util.TileTransformer

/**
 * Extension function which adapts [TileGraphics.transform] to
 * Kotlin idioms (eg: lambdas).
 */
inline fun TileGraphics.transform(crossinline fn: (Tile) -> Tile) {
    transform(object : TileTransformer {
        override fun invoke(tile: Tile): Tile {
            return fn(tile)
        }
    })
}

/**
 * Extension function which adapts [TileGraphics.transformTileAt] to
 * Kotlin idioms (eg: lambdas).
 */
inline fun TileGraphics.transformTileAt(position: Position, crossinline fn: (Tile) -> Tile) {
    transformTileAt(position, object : TileTransformer {
        override fun invoke(tile: Tile): Tile {
            return fn(tile)
        }
    })
}
