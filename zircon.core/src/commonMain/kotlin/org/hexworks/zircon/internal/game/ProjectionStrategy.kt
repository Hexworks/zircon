package org.hexworks.zircon.internal.game

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.LayerState
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea

/**
 * Can be used to create projections of a [GameArea] which can be used
 * to render the [GameArea] on the screen.
 */
interface ProjectionStrategy {

    /**
     * Creates a projection of the supplied [gameArea] as a [Sequence] of
     * [LayerState]s.
     */
    fun project(gameArea: GameArea<out Tile, out Block<out Tile>>): Sequence<LayerState>
}