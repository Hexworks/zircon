package org.codetome.zircon.behavior.impl

import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.codetome.zircon.behavior.Boundable
import java.awt.Point
import java.awt.Rectangle

/**
 * A [DefaultBoundable] has no offset.
 */
class DefaultBoundable(private val size: Size,
                       private val position: Position = Position.DEFAULT_POSITION)
    : Boundable {

    private val rect = Rectangle(position.column, position.row, getBoundableSize().columns, getBoundableSize().rows)

    override fun getPosition() = position

    override fun getBoundableSize() = size

    override fun intersects(boundable: Boundable) = rect.intersects(
            Rectangle(
                    boundable.getPosition().column,
                    boundable.getPosition().row,
                    boundable.getBoundableSize().columns,
                    boundable.getBoundableSize().rows))

    override fun containsPosition(position: Position): Boolean {
        return rect.contains(Point(position.column, position.row))
    }

    override fun containsBoundable(boundable: Boundable) = rect.contains(
            Rectangle(
                    boundable.getPosition().column,
                    boundable.getPosition().row,
                    boundable.getBoundableSize().columns,
                    boundable.getBoundableSize().rows))
}