@file:JvmName("DrawSurfaceUtils")
package org.hexworks.zircon.api.extensions

import org.hexworks.zircon.api.DrawSurfaces
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileComposite
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.TileImage
import org.hexworks.zircon.api.resource.TilesetResource
import kotlin.jvm.JvmName

/**
 * Transforms an [Iterable] of [Position] to [Tile] [Pair]s to a [TileImage] with
 * the given [size] and [tileset].
 */
fun Iterable<Pair<Position, Tile>>.toTileImage(size: Size, tileset: TilesetResource): TileImage {
    return toMap().toTileImage(size, tileset)
}

/**
 * Transforms the given [Map] to a [TileImage] with the given [size] and [tileset].
 */
fun Map<Position, Tile>.toTileImage(size: Size, tileset: TilesetResource): TileImage {
    require(keys.none { it.hasNegativeComponent }) {
        "Can't create a TileImage with positions which have a negative component (x or y)."
    }
    return DrawSurfaces.tileImageBuilder()
            .withTiles(this)
            .withSize(size)
            .withTileset(tileset)
            .build()
}

/**
 * Transforms an [Iterable] of [Position] to [Tile] [Pair]s to a [TileImage] with
 * the given [size] and [tileset].
 */
fun Iterable<Pair<Position, Tile>>.toTileComposite(size: Size): TileComposite {
    return toMap().toTileComposite(size)
}

/**
 * Transforms the given [Map] to a [TileComposite] with the given [size].
 */
fun Map<Position, Tile>.toTileComposite(size: Size): TileComposite {
    require(keys.none { it.hasNegativeComponent }) {
        "Can't create a TileImage with positions which have a negative component (x or y)."
    }
    return DrawSurfaces.tileCompositeBuilder()
            .withSize(size)
            .withTiles(this)
            .build()
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
 */
fun Map<Position, Tile>.toTileGraphics(size: Size, tileset: TilesetResource): TileGraphics {
    require(keys.none { it.hasNegativeComponent }) {
        "Can't create a TileImage with positions which have a negative component (x or y)."
    }
    return DrawSurfaces.tileGraphicsBuilder()
            .withTiles(this)
            .withSize(size)
            .withTileset(tileset)
            .build()
}
