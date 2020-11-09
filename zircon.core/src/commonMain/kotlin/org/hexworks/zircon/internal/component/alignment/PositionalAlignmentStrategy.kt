package org.hexworks.zircon.internal.component.alignment

import org.hexworks.zircon.api.component.AlignmentStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

/**
 * This alignment strategy will return the given [position] regardless
 * of the size passed to [calculatePosition].
 */
class PositionalAlignmentStrategy(private val position: Position) : AlignmentStrategy {

    override fun calculatePosition(size: Size): Position {
        return position
    }
}
