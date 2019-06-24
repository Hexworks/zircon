package org.hexworks.zircon.internal.component.alignment

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.component.AlignmentStrategy
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

data class AroundAlignmentStrategy(
        private val other: Boundable,
        private val alignmentType: ComponentAlignment) : AlignmentStrategy {

    override fun calculateAlignment(size: Size): Position {
        return alignmentType.alignAround(other.rect, size)
    }
}
