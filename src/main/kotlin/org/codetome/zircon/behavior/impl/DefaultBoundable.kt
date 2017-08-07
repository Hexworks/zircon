package org.codetome.zircon.behavior.impl

import org.codetome.zircon.Position
import org.codetome.zircon.behavior.Boundable
import org.codetome.zircon.terminal.Size
import java.awt.Point
import java.awt.Rectangle
import java.util.*

class DefaultBoundable(private val offset: Position,
                       private val size: Size) : Boundable {

    private val rect = Rectangle(offset.column, offset.row, size.columns, size.rows)

    override fun getOffset() = offset

    override fun getSize() = size

    override fun intersects(boundable: Boundable) = rect.intersects(
            Rectangle(
                    boundable.getOffset().column,
                    boundable.getOffset().row,
                    boundable.getSize().columns,
                    boundable.getSize().rows))

    override fun calculateIntersectionForBoundable(boundable: Boundable): Optional<Boundable> {
        rect.intersection(Rectangle(
                boundable.getOffset().column,
                boundable.getOffset().row,
                boundable.getSize().columns,
                boundable.getSize().rows)).let { intersection ->
            return if (intersection.isEmpty) {
                Optional.empty()
            } else {
                Optional.of(DefaultBoundable(
                        offset = Position(
                                column = intersection.x,
                                row = intersection.y),
                        size = Size(
                                columns = intersection.width,
                                rows = intersection.height)))
            }
        }
    }

    override fun containsPosition(position: Position) = rect.contains(Point(position.column, position.row))

    override fun containsBoundable(boundable: Boundable) = rect.contains(
            Rectangle(
                    boundable.getOffset().column,
                    boundable.getOffset().row,
                    boundable.getSize().columns,
                    boundable.getSize().rows))
}