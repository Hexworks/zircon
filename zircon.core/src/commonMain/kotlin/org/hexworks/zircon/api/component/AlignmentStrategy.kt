package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.alignment.AroundAlignmentStrategy
import org.hexworks.zircon.internal.component.alignment.PositionalAlignmentStrategy
import org.hexworks.zircon.internal.component.alignment.WithinAlignmentStrategy

/**
 * An [AlignmentStrategy] can be used to calculate the [Position]
 * of a [Boundable] (such as [Component]s) relative to another one.
 * @see [AroundAlignmentStrategy]
 * @see [PositionalAlignmentStrategy]
 * @see [WithinAlignmentStrategy]
 */
interface AlignmentStrategy {

    /**
     * Calculates the [Position] for an object having
     * the given [size] relative to a [Boundable].
     */
    fun calculatePosition(size: Size): Position
}
