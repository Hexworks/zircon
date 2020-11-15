package org.hexworks.zircon.api.game

import org.hexworks.zircon.api.Beta
import org.hexworks.zircon.internal.game.GameAreaRenderer
import org.hexworks.zircon.internal.game.impl.TopDownFrontGameAreaRenderer
import org.hexworks.zircon.internal.game.impl.TopDownGameAreaRenderer

/**
 * Represents modes for projecting the Blocks in a [GameArea].
 * See: [here](https://docs.google.com/spreadsheets/d/1mvFKwToAHLErmBCZkD0Pylwiptle_rAij-B0DzoCOhI/edit?usp=sharing)
 * for more info.
 */
@Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")
@Beta
enum class ProjectionMode(
        val gameAreaRenderer: GameAreaRenderer
) {

    /**
     * Tiles are rendered from top to bottom.
     */
    TOP_DOWN(TopDownGameAreaRenderer()),

    /**
     * Top-down oblique rendering mode with a viewpoint from the front.
     */
    TOP_DOWN_OBLIQUE_FRONT(TopDownFrontGameAreaRenderer())
}
