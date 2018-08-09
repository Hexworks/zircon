package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

class DefaultBoundable(private val size: Size,
                       private var position: Position = Position.defaultPosition())
    : Boundable, Movable {

    private var rect = refreshRect()

    override fun position() = position

    override fun size() = size

    override fun moveTo(position: Position): Boolean {
        return if (this.position == position) {
            false
        } else {
            this.position = position
            this.rect = refreshRect()
            true
        }
    }


    override fun intersects(boundable: Boundable): Boolean {
        return rect.intersects(Rectangle(
                boundable.position().x,
                boundable.position().y,
                boundable.size().xLength,
                boundable.size().yLength))
    }

    override fun containsPosition(position: Position): Boolean {
        return rect.contains(position)
    }

    override fun containsBoundable(boundable: Boundable): Boolean {
        return rect.contains(Rectangle(
                boundable.position().x,
                boundable.position().y,
                boundable.size().xLength,
                boundable.size().yLength))
    }

    private fun refreshRect() = Rectangle(position.x, position.y, size().xLength, size().yLength)
}
