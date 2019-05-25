package org.hexworks.zircon.api

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.component.AlignmentStrategy
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.component.alignment.AroundAlignmentStrategy
import org.hexworks.zircon.internal.component.alignment.PositionalAlignmentStrategy
import org.hexworks.zircon.internal.component.alignment.WithinAlignmentStrategy

object ComponentAlignments {

    /**
     * Calculates the [Position] of the resulting [Component] within
     * the given [tileGrid] using the given [ComponentAlignment].
     *
     * E.g. `TOP_LEFT` will align this [Component] to the top left
     * corner of the [tileGrid].
     */
    fun alignmentWithin(tileGrid: TileGrid,
                        alignmentType: ComponentAlignment): AlignmentStrategy {
        return WithinAlignmentStrategy(
                other = Boundable.create(size = tileGrid.size),
                alignmentType = alignmentType)
    }

    /**
     * Calculates the [Position] of the resulting [Component] within
     * the given [container] using the given [ComponentAlignment].
     *
     * E.g. `TOP_LEFT` will align this [Component] to the top left
     * corner of the [container].
     */
    fun alignmentWithin(container: Container,
                        alignmentType: ComponentAlignment): AlignmentStrategy {
        return WithinAlignmentStrategy(
                other = container.rect,
                alignmentType = alignmentType)
    }

    /**
     * Calculates the [Position] of the resulting [Component] relative to
     * the given [component] using the given [ComponentAlignment].
     *
     * E.g. `TOP_LEFT` will align this [Component] to the top left
     * corner of [component].
     */
    fun alignmentAround(component: Component,
                        alignmentType: ComponentAlignment): AlignmentStrategy {
        return AroundAlignmentStrategy(
                other = component,
                alignmentType = alignmentType)
    }

    /**
     * Creates a [AlignmentStrategy] which will align a [Component]
     * within another one relative to its top left corner
     * using the given [x],[y] coordinates.
     */
    fun positionalAlignment(x: Int, y: Int): AlignmentStrategy {
        return positionalAlignment(Positions.create(x, y))
    }

    /**
     * Creates a [AlignmentStrategy] which will align a [Component]
     * within another one relative to its top left corner
     * using the given [Position].
     */
    fun positionalAlignment(position: Position): AlignmentStrategy {
        return PositionalAlignmentStrategy(position)
    }

}
