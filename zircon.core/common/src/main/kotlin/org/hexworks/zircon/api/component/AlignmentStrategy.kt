package org.hexworks.zircon.api.component.alignment

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.data.Position

/**
 * An [AlignmentStrategy] can be used to calculate the alignment
 * of a [Boundable] relative to another one.
 */
interface AlignmentStrategy {

    /**
     * Calculates the current [Position] of a [Boundable]
     * relative to another one.
     */
    fun calculateAlignment(): Position
}
