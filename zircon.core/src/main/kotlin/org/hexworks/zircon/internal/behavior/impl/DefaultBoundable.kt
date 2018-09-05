package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.data.Bounds
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

class DefaultBoundable(private val size: Size,
                       private var position: Position = Position.defaultPosition())
    : Boundable, Movable {

    private var bounds = refreshBounds()

    override fun position() = position

    override fun size() = size

    override fun bounds() = bounds

    override fun moveTo(position: Position): Boolean {
        return if (this.position == position) {
            false
        } else {
            this.position = position
            this.bounds = refreshBounds()
            true
        }
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

    private fun refreshBounds() = Bounds.create(position, size())
}
