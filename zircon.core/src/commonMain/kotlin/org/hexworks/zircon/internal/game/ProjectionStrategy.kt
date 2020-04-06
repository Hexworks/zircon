package org.hexworks.zircon.internal.game

import kotlinx.collections.immutable.PersistentList
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.graphics.TileComposite
import org.hexworks.zircon.api.graphics.TileImage
import org.hexworks.zircon.internal.util.AnyGameAreaState

/**
 * Can be used to create projections of a [GameArea] which can be used
 * to render the [GameArea] on the screen. A projection always happens
 * from a viewpoint which depends on the implementation of the
 * [ProjectionStrategy]. For example a top down projection is executed
 * in a top -> bottom direction.
 */
interface ProjectionStrategy {

    /**
     * Creates a projection of the supplied [gameAreaState] as a [Sequence] of
     * [TileComposite]s. The [TileComposite]s are ordered from the viewpoint
     * from near to far. For example with a top down [ProjectionStrategy]
     * they are ordered from top to bottom.
     * **Note that** only the visible parts of the [GameArea] will be projected.
     */
    fun projectGameArea(gameAreaState: AnyGameAreaState): PersistentList<TileImage>
}