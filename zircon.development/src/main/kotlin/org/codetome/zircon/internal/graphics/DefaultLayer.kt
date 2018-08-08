package org.codetome.zircon.internal.graphics

import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.behavior.Drawable
import org.codetome.zircon.api.behavior.TilesetOverride
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.graphics.TileImage
import org.codetome.zircon.internal.behavior.impl.Rectangle

/**
 * this is a basic building block which can be re-used by complex image
 * classes like layers, boxes, components, and more
 * all classes which are implementing the DrawSurface or the Drawable operations can
 * use this class as a base class just like how the TileGrid uses it
 */

data class DefaultLayer(private var position: Position,
                        val backend: TileImage)
    : Layer, TilesetOverride by backend {

    private var rect: Rectangle = refreshRect()

    override fun createSnapshot(): Map<Position, Tile> {
        return backend.createSnapshot().mapKeys { it.key + position }
    }

    override fun draw(drawable: Drawable, offset: Position) {
        backend.draw(drawable, offset)
    }

    override fun getBoundableSize(): Size {
        return backend.getBoundableSize()
    }

    override fun getRelativeTileAt(position: Position) = backend.getTileAt(position)

    override fun setRelativeTileAt(position: Position, character: Tile) {
        backend.setTileAt(position, character)
    }

    override fun getPosition() = position

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
                    boundable.getPosition().x,
                    boundable.getPosition().y,
                    boundable.getBoundableSize().xLength,
                    boundable.getBoundableSize().yLength))

    override fun containsPosition(position: Position): Boolean {
        return rect.contains(position)
    }

    override fun containsBoundable(boundable: Boundable) = rect.contains(
            Rectangle(
                    position.x,
                    position.y,
                    boundable.getBoundableSize().xLength,
                    boundable.getBoundableSize().yLength))

    override fun getTileAt(position: Position) = backend.getTileAt(position - this.position)

    override fun setTileAt(position: Position, tile: Tile) {
        backend.setTileAt(position - this.position, tile)
    }

    private fun refreshRect(): Rectangle {
        return Rectangle(position.x, position.y, getBoundableSize().xLength, getBoundableSize().yLength)
    }

    override fun createCopy() = DefaultLayer(
            position = position,
            backend = backend)

}
