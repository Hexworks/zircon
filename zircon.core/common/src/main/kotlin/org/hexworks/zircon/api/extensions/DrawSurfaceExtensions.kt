package org.hexworks.zircon.api.extensions

import org.hexworks.zircon.api.DrawSurfaces
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.TileImage
import org.hexworks.zircon.api.resource.TilesetResource

fun Iterable<Pair<Position, Tile>>.toTileImage(size: Size, tileset: TilesetResource): TileImage {
    return toMap().toTileImage(size, tileset)
}

fun Map<Position, Tile>.toTileImage(size: Size, tileset: TilesetResource): TileImage {
    require(keys.none { it.hasNegativeComponent() }) {
        "Can't create a TileImage with positions which have a negative component (x or y)."
    }
    return DrawSurfaces.tileImageBuilder()
            .withTiles(this)
            .withSize(size)
            .withTileset(tileset)
            .build()
}

fun Iterable<Pair<Position, Tile>>.toTileGraphics(size: Size, tileset: TilesetResource): TileGraphics {
    return toMap().toTileGraphics(size, tileset)
}

fun Map<Position, Tile>.toTileGraphics(size: Size, tileset: TilesetResource): TileGraphics {
    require(keys.none { it.hasNegativeComponent() }) {
        "Can't create a TileImage with positions which have a negative component (x or y)."
    }
    return DrawSurfaces.tileGraphicsBuilder()
            .withTiles(this)
            .withSize(size)
            .withTileset(tileset)
            .build()
}
