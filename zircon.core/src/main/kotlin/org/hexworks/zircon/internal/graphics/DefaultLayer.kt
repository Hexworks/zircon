package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.DrawSurface
import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.data.Bounds
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.TileGraphic

/**
 * this is a basic building block which can be re-used by complex image
 * classes like layers, boxes, components, and more
 * all classes which are implementing the DrawSurface or the Drawable operations can
 * use this class as a base class just like how the TileGrid uses it
 */

data class DefaultLayer(private var position: Position,
                        val backend: TileGraphic)
    : Layer, TilesetOverride by backend {

    private var bounds: Bounds = refreshBounds()

    override fun snapshot(): Map<Position, Tile> {
        return backend.snapshot().mapKeys { it.key + position }
    }

    override fun draw(drawable: Drawable, position: Position) {
        backend.draw(drawable, position)
    }

    override fun drawOnto(surface: DrawSurface, position: Position) {
        backend.drawOnto(surface, position)
    }

    override fun getRelativeTileAt(position: Position) = backend.getTileAt(position)

    override fun setRelativeTileAt(position: Position, tile: Tile) {
        backend.setTileAt(position, tile)
    }

    override fun bounds() = bounds

    override fun position() = position

    override fun size(): Size {
        return backend.size()
    }

    override fun moveTo(position: Position) =
            if (this.position == position) {
                false
            } else {
                this.position = position
                this.bounds = refreshBounds()
                true
            }

    override fun intersects(boundable: Boundable): Boolean {
        return bounds.intersects(boundable.bounds())
    }

    override fun containsPosition(position: Position): Boolean {
        return bounds.containsPosition(position)
    }

    override fun containsBoundable(boundable: Boundable): Boolean {
        return bounds.containsBounds(boundable.bounds())
    }

    override fun getTileAt(position: Position) = backend.getTileAt(position - this.position)

    override fun setTileAt(position: Position, tile: Tile) {
        backend.setTileAt(position - this.position, tile)
    }

    private fun refreshBounds(): Bounds {
        return Bounds.create(position, size())
    }

    override fun createCopy() = DefaultLayer(
            position = position,
            backend = backend)

    override fun fill(filler: Tile): Layer {
        backend.fill(filler)
        return this
    }

}
