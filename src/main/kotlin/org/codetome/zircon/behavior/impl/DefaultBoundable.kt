package org.codetome.zircon.behavior.impl

import org.codetome.zircon.Position
import org.codetome.zircon.behavior.Boundable
import org.codetome.zircon.terminal.Size
import java.awt.Point
import java.awt.Rectangle
import java.util.*

class DefaultBoundable(private val position: Position,
                       private val size: Size) : Boundable {

    private val rect = Rectangle(position.column, position.row, size.columns, size.rows)

    override fun getPosition() = position

    override fun getSize() = size

    override fun intersects(boundable: Boundable) = rect.intersects(
            Rectangle(
                    boundable.getPosition().column,
                    boundable.getPosition().row,
                    boundable.getSize().columns,
                    boundable.getSize().rows))

    override fun calculateIntersectionForBoundable(boundable: Boundable): Optional<Boundable> {
        rect.intersection(Rectangle(
                boundable.getPosition().column,
                boundable.getPosition().row,
                boundable.getSize().columns,
                boundable.getSize().rows)).let { intersection ->
            return if (intersection.isEmpty) {
                Optional.empty()
            } else {
                Optional.of(DefaultBoundable(
                        position = Position(
                                column = intersection.x,
                                row = intersection.y),
                        size = Size(
                                columns = intersection.width,
                                rows = intersection.height)))
            }
        }
    }

    override fun containsPosition(position: Position) = rect.contains(
            Point(position.column, position.row))

    override fun containsBoundable(boundable: Boundable) = rect.contains(
            Rectangle(
                    boundable.getPosition().column,
                    boundable.getPosition().row,
                    boundable.getSize().columns,
                    boundable.getSize().rows))
}