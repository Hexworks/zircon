package org.hexworks.zircon.internal.component.alignment

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.alignment.AlignmentStrategy
import org.hexworks.zircon.api.data.Position

data class AroundAlignmentStrategy(
        private val targetSupplier: () -> Boundable,
        private val subjectSupplier: () -> Boundable = { throw NoSuchElementException("No subject is set") },
        private val alignmentType: ComponentAlignment) : AlignmentStrategy {

    override fun calculateAlignment(): Position {
        return alignmentType.alignAround(targetSupplier().rect, subjectSupplier().rect)
    }
}
