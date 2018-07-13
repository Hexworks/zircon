package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.behavior.Movable

/**
 * A [DefaultBoundable] has no offset.
 */
class DefaultBoundable(private val size: Size,
                       private var position: Position = Position.defaultPosition())
    : Boundable, Movable {

    private var rect = refreshRect()

    override fun getPosition() = position

    override fun getBoundableSize() = size

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
                    boundable.getPosition().x,
                    boundable.getPosition().y,
                    boundable.getBoundableSize().xLength,
                    boundable.getBoundableSize().yLength))

    private fun refreshRect() =
            Rectangle(position.x, position.y, getBoundableSize().xLength, getBoundableSize().yLength)
}
