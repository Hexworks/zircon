package org.hexworks.zircon.api.extensions

import org.hexworks.zircon.api.data.Tile

fun List<Tile>.transform(tileTransformer: (Tile) -> Tile): List<Tile> {
    return this.map(tileTransformer::invoke)
}

fun List<Tile>.transformIndexed(transformer: (Int, Tile) -> Tile): List<Tile> {
    return this.mapIndexed { index, tile -> transformer.invoke(index, tile) }
}
