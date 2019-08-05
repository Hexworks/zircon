package org.hexworks.zircon.internal.graphics

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.behavior.Clearable
import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.data.LayerSnapshot
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.behavior.impl.DefaultMovable

class DefaultLayer(position: Position,
                   val backend: TileGraphics,
                   private val movable: DefaultMovable = DefaultMovable(
                           position = position,
                           size = backend.size))
    : Layer,
        DrawSurface by backend,
        Drawable by backend,
        Movable by movable,
        Clearable by backend {

    override val size: Size
        get() = movable.size

    override val width: Int
        get() = size.width

    override val height: Int
        get() = size.height

    override val hiddenProperty = createPropertyFrom(false)

    override var isHidden: Boolean by hiddenProperty.asDelegate()

    override fun createSnapshot(): LayerSnapshot {
        val (tiles, tileset, size) = backend.createSnapshot()
        return if (isHidden) {
            LayerSnapshot.create(mapOf(), tileset, size, position)
        } else {
            LayerSnapshot.create(
                    tiles = tiles.mapKeys { it.key.withRelative(position) },
                    tileset = tileset,
                    size = size,
                    position = position)
        }
    }

    override fun getAbsoluteTileAt(position: Position): Maybe<Tile> {
        return backend.getTileAt(position - this.position)
    }

    override fun setAbsoluteTileAt(position: Position, tile: Tile) {
        backend.setTileAt(position - this.position, tile)
    }

    override fun createCopy() = DefaultLayer(
            position = position,
            backend = backend.createCopy())

}
