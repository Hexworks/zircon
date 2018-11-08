package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Snapshot
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.behavior.impl.DefaultMovable

class DefaultLayer(position: Position,
                   val backend: TileGraphics,
                   private val movable: DefaultMovable = DefaultMovable(
                           position = position,
                           size = backend.size))
    : Layer,
        DrawSurface by backend,
        Drawable by backend,
        Movable by movable {

    override val size: Size
        get() = movable.size

    override val width: Int
        get() = size.width

    override val height: Int
        get() = size.height

    override fun createSnapshot(): Snapshot {
        return backend.createSnapshot().let { snapshot ->
            Snapshot.create(
                    cells = snapshot.cells.map { it.withPosition(it.position + position) },
                    tileset = snapshot.tileset)
        }
    }

    override fun getAbsoluteTileAt(position: Position): Maybe<Tile> {
        return backend.getTileAt(position - this.position)
    }

    override fun setAbsoluteTileAt(position: Position, tile: Tile) {
        backend.setTileAt(position - this.position, tile)
    }

    // TODO: make deep copy!
    override fun createCopy() = DefaultLayer(
            position = position,
            backend = backend)

    override fun fill(filler: Tile): Layer {
        backend.fill(filler)
        return this
    }

}
