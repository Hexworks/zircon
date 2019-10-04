package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

/**
 * An [AlignmentStrategy] can be used to calculate the alignment
 * of a [Boundable] relative to another one.
 */
interface AlignmentStrategy {

    /**
     * Calculates the [Position] for an object having
     * the given [size] relative to a [Boundable].
     */
    fun calculatePosition(size: Size): Position
}
