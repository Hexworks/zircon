package org.hexworks.zircon.api.kotlin

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.util.TileTransformer

fun List<Tile>.transform(tileTransformer: TileTransformer): List<Tile> {
    return this.map(tileTransformer::transform)
}

fun List<Tile>.transformIndexed(transformer: (Int, Tile) -> Tile): List<Tile> {
    return this.mapIndexed { index, tile -> transformer.invoke(index, tile) }
}
