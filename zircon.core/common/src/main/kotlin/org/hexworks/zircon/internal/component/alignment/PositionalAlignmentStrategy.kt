package org.hexworks.zircon.internal.component.alignment

import org.hexworks.zircon.api.component.alignment.AlignmentStrategy
import org.hexworks.zircon.api.data.Position

class PositionalAlignmentStrategy(private val position: Position) : AlignmentStrategy {

    override fun calculateAlignment(): Position {
        return position
    }
}
