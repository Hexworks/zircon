package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.DrawSurface
import org.hexworks.zircon.api.behavior.Styleable
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.behavior.impl.DefaultBoundable
import org.hexworks.zircon.internal.behavior.impl.DefaultStyleable
import org.hexworks.zircon.internal.behavior.impl.DefaultTilesetOverride

/**
 * this is a basic building block which can be re-used by complex image
 * classes like layers, boxes, components, and more
 * all classes which are implementing the DrawSurface or the Drawable operations can
 * use this class as a base class just like how the TileGrid uses it
 */

abstract class BaseTileGraphics(
        styleSet: StyleSet,
        tileset: TilesetResource,
        size: Size,
        private val tilesetOverride: TilesetOverride = DefaultTilesetOverride(
                tileset = tileset),
        private val contents: MutableMap<Position, Tile>,
        styleable: Styleable = DefaultStyleable(styleSet),
        boundable: Boundable = DefaultBoundable(size = size))
    : TileGraphics,
        Styleable by styleable,
        Boundable by boundable,
        TilesetOverride by tilesetOverride {

    override fun toString(): String {
        return (0 until height).joinToString("") { y ->
            (0 until width).joinToString("") { x ->
                getTileAt(Position.create(x, y))
                        .get()
                        .asCharacterTile()
                        .orElse(Tile.defaultTile())
                        .character.toString()
            }.plus("\n")
        }.trim()
    }

    override fun clear() {
        contents.clear()
    }

    override fun getTileAt(position: Position): Maybe<Tile> {
        return if (containsPosition(position)) {
            Maybe.of(contents[position] ?: Tile.empty())
        } else {
            Maybe.empty()
        }
    }

    override fun setTileAt(position: Position, tile: Tile) {
        if (position.x < width && position.y < height) {
            contents[position] = tile
        }
    }

    override fun createSnapshot(): Snapshot {
        return Snapshot.create(
                cells = contents.entries.map { (pos, tile) ->
                    Cell.create(pos, tile)
                },
                tileset = currentTileset())
    }

    override fun drawOnto(surface: DrawSurface, position: Position) {
        contents.entries.forEach { (pos, tile) ->
            surface.setTileAt(pos + position, tile)
        }
    }
}
