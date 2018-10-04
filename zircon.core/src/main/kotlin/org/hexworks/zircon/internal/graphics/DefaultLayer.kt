package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.DrawSurface
import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Snapshot
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.TileGraphics

/**
 * this is a basic building block which can be re-used by complex image
 * classes like layers, boxes, components, and more
 * all classes which are implementing the DrawSurface or the Drawable operations can
 * use this class as a base class just like how the TileGrid uses it
 */

data class DefaultLayer(private var currentPosition: Position,
                        val backend: TileGraphics)
    : Layer,
        DrawSurface by backend,
        Drawable by backend {

    // note that we could delegate to rect but delegation of
    // mutable vars is broken in Kotlin:
    // http://the-cogitator.com/2018/09/29/by-the-way-exploring-delegation-in-kotlin.html#the-pitfall-of-interface-delegation

    override val rect: Rect
        get() = currentRect

    override val position: Position
        get() = currentPosition

    private var currentRect: Rect = regenerateRect()

    override fun createSnapshot(): Snapshot {
        return backend.createSnapshot().let { snapshot ->
            Snapshot.create(
                    cells = snapshot.cells.map { it.withPosition(it.position + position) },
                    tileset = snapshot.tileset)
        }
    }

    override fun getAbsoluteTileAt(position: Position) = backend.getTileAt(position - this.position)

    override fun setAbsoluteTileAt(position: Position, tile: Tile) {
        backend.setTileAt(position - this.position, tile)
    }

    override fun moveTo(position: Position) =
            if (this.position == position) {
                false
            } else {
                currentPosition = position
                currentRect = regenerateRect()
                true
            }

    override fun intersects(boundable: Boundable): Boolean {
        return rect.intersects(boundable.rect)
    }

    override fun containsPosition(position: Position): Boolean {
        return rect.containsPosition(position)
    }

    override fun containsBoundable(boundable: Boundable): Boolean {
        return rect.containsBoundable(boundable.rect)
    }

    override fun createCopy() = DefaultLayer(
            currentPosition = position,
            backend = backend)

    override fun fill(filler: Tile): Layer {
        backend.fill(filler)
        return this
    }

    private fun regenerateRect(): Rect {
        return Rect.create(position, size)
    }

}
