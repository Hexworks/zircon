package org.hexworks.zircon.api.extensions

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
