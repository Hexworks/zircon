package org.hexworks.zircon.internal.component.alignment

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.component.AlignmentStrategy
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

/**
 * Can be used to align objects **around** an [other] [Boundable]
 * object. This means that [calculatePosition] will return a [Position]
 * for which [Boundable.containsPosition] will return `false` when
 * called on [other].
 */
data class AroundAlignmentStrategy(
    private val other: Boundable,
    private val alignmentType: ComponentAlignment
) : AlignmentStrategy {

    override fun calculatePosition(size: Size): Position {
        return alignmentType.alignAround(other.rect, size)
    }
}
