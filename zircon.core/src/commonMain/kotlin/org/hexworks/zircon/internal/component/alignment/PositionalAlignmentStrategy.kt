package org.hexworks.zircon.internal.component.alignment

import org.hexworks.zircon.api.component.AlignmentStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

class PositionalAlignmentStrategy(private val position: Position) : AlignmentStrategy {

    override fun calculatePosition(size: Size): Position {
        return position
    }
}
