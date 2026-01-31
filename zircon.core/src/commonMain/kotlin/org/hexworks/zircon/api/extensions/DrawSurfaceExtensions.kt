package org.hexworks.zircon.api.extensions

import org.hexworks.zircon.api.builder.graphics.tileComposite
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.builder.graphics.tileImage
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileComposite
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.TileImage
import org.hexworks.zircon.api.resource.TilesetResource

/**
 * Transforms an [Iterable] of [Position] to [Tile] [Pair]s to a [TileImage] with
 * the given [size] and [tileset].
 */
fun Iterable<Pair<Position, Tile>>.toTileImage(size: Size, tileset: TilesetResource): TileImage {
    return toMap().toTileImage(size, tileset)
}

/**
 * Transforms the given [Map] to a [TileImage] with the given [size] and [tileset].
 * The [Map] will be used to back the [TileImage].
 */
fun Map<Position, Tile>.toTileImage(size: Size, tileset: TilesetResource): TileImage {
    require(keys.none { it.hasNegativeComponent }) {
        "Can't create a TileImage with positions which have a negative component (x or y)."
    }
    return tileImage {
        tiles = this@toTileImage
        this.size = size
        this.tileset = tileset
    }
}

/**
 * Transforms an [Iterable] of [Position] to [Tile] [Pair]s to a [TileImage] with
 * the given [size].
 */
fun Iterable<Pair<Position, Tile>>.toTileComposite(size: Size): TileComposite {
    return toMap().toTileComposite(size)
}

/**
 * Transforms the given [Map] to a [TileComposite] with the given [size].
 * The [Map] will be used to back the [TileComposite].
 */
fun Map<Position, Tile>.toTileComposite(size: Size): TileComposite {
    require(keys.none { it.hasNegativeComponent }) {
        "Can't create a TileImage with positions which have a negative component (x or y)."
    }
    return tileComposite {
        this.size = size
        tiles = this@toTileComposite
    }
}

/**
 * Transforms an [Iterable] of [Position] to [Tile] [Pair]s to a [TileGraphics] with
 * the given [size] and [tileset].
 */
fun Iterable<Pair<Position, Tile>>.toTileGraphics(size: Size, tileset: TilesetResource): TileGraphics {
    return toMap().toTileGraphics(size, tileset)
}

/**
 * Transforms the given [Map] to a [TileGraphics] with the given [size] and [tileset].
 * The [Map] will be used to back the [TileGraphics].
 */
fun Map<Position, Tile>.toTileGraphics(size: Size, tileset: TilesetResource): TileGraphics {
    require(keys.none { it.hasNegativeComponent }) {
        "Can't create a TileImage with positions which have a negative component (x or y)."
    }
    return tileGraphics {
        tiles = this@toTileGraphics
        this.size = size
        this.tileset = tileset
    }
}
