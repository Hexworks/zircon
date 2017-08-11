package org.codetome.zircon.behavior.impl

import org.codetome.zircon.Position
import org.codetome.zircon.behavior.Boundable
import org.codetome.zircon.terminal.Size
import java.awt.Point
import java.awt.Rectangle

/**
 * A [DefaultBoundable] has no offset.
 */
class DefaultBoundable(private val size: Size) : Boundable {

    private val rect = Rectangle(0, 0, size.columns, size.rows)

    override fun getBoundableSize() = size

    override fun intersects(boundable: Boundable) = rect.intersects(
            Rectangle(
                    0,
                    0,
                    boundable.getBoundableSize().columns,
                    boundable.getBoundableSize().rows))

    override fun containsPosition(position: Position) = rect.contains(Point(position.column, position.row))

    override fun containsBoundable(boundable: Boundable) = rect.contains(
            Rectangle(
                    0,
                    0,
                    boundable.getBoundableSize().columns,
                    boundable.getBoundableSize().rows))
}