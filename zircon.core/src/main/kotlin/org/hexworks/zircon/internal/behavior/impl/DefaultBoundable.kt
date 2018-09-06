package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.data.Bounds
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

class DefaultBoundable(size: Size,
                       position: Position = Position.defaultPosition())
    : Boundable, Movable {

    private var bounds = Bounds.create(position, size)

    override fun position() = bounds.position()

    override fun size() = bounds.size()

    override fun bounds() = bounds

    override fun moveTo(position: Position): Boolean {
        return if (position() == position) {
            false
        } else {
            this.bounds = bounds.withPosition(position)
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

}
