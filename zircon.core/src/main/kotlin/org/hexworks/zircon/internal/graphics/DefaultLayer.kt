package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.DrawSurface
import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.TileGraphic
import org.hexworks.zircon.internal.behavior.impl.Rectangle

/**
 * this is a basic building block which can be re-used by complex image
 * classes like layers, boxes, components, and more
 * all classes which are implementing the DrawSurface or the Drawable operations can
 * use this class as a base class just like how the TileGrid uses it
 */

data class DefaultLayer(private var position: Position,
                        val backend: TileGraphic)
    : Layer, TilesetOverride by backend {

    private var rect: Rectangle = refreshRect()

    override fun snapshot(): Map<Position, Tile> {
        return backend.snapshot().mapKeys { it.key + position }
    }

    override fun draw(drawable: Drawable, position: Position) {
        backend.draw(drawable, position)
    }

    override fun drawOnto(surface: DrawSurface, position: Position) {
        backend.drawOnto(surface, position)
    }

    override fun size(): Size {
        return backend.size()
    }

    override fun getRelativeTileAt(position: Position) = backend.getTileAt(position)

    override fun setRelativeTileAt(position: Position, character: Tile) {
        backend.setTileAt(position, character)
    }

    override fun position() = position

    override fun moveTo(position: Position) =
            if (this.position == position) {
                false
            } else {
                this.position = position
                this.rect = refreshRect()
                true
            }

    override fun intersects(boundable: Boundable) = rect.intersects(
            Rectangle(
                    boundable.position().x,
                    boundable.position().y,
                    boundable.size().xLength,
                    boundable.size().yLength))

    override fun containsPosition(position: Position): Boolean {
        return rect.contains(position)
    }

    override fun containsBoundable(boundable: Boundable) = rect.contains(
            Rectangle(
                    position.x,
                    position.y,
                    boundable.size().xLength,
                    boundable.size().yLength))

    override fun getTileAt(position: Position) = backend.getTileAt(position - this.position)

    override fun setTileAt(position: Position, tile: Tile) {
        backend.setTileAt(position - this.position, tile)
    }

    private fun refreshRect(): Rectangle {
        return Rectangle(position.x, position.y, size().xLength, size().yLength)
    }

    override fun createCopy() = DefaultLayer(
            position = position,
            backend = backend)

    override fun fill(filler: Tile): Layer {
        backend.fill(filler)
        return this
    }

}
