package org.hexworks.zircon.api.extensions

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileImage
import org.hexworks.zircon.api.util.TileTransformer

/**
 * Extension function which adapts [TileImage.transform] to
 * Kotlin idioms (eg: lambdas).
 */
inline fun TileImage.transform(crossinline fn: (Tile) -> Tile): TileImage {
    return transform(object : TileTransformer {
        override fun invoke(tile: Tile): Tile {
            return fn(tile)
        }
    })
}
