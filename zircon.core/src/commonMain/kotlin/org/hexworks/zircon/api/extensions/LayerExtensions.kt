package org.hexworks.zircon.api.extensions

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.util.TileTransformer

/**
 * Extension function which adapts [Layer.transform] to
 * Kotlin idioms (eg: lambdas).
 */
inline fun Layer.transform(crossinline fn: (Tile) -> Tile) {
    transform(object : TileTransformer {
        override fun invoke(tile: Tile): Tile {
            return fn(tile)
        }
    })
}
