package org.hexworks.zircon.api.game

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.game.GameAreaRenderer
import org.hexworks.zircon.internal.game.impl.TopDownGameAreaRenderer

/**
 * Represents modes for projecting the Blocks in a [GameArea].
 * See: [here](https://docs.google.com/spreadsheets/d/1mvFKwToAHLErmBCZkD0Pylwiptle_rAij-B0DzoCOhI/edit?usp=sharing)
 * for more info.
 */

enum class ProjectionMode : RendererFactory {

    /**
     * Tiles are rendered from top to bottom.
     */
    TOP_DOWN {
        override fun <T : Tile, B : Block<T>> createRenderer() = TopDownGameAreaRenderer<T, B>()
    },

    /**
     * Top-down oblique rendering mode with a viewpoint from the front.
     */
    TOP_DOWN_OBLIQUE_FRONT {
        override fun <T : Tile, B : Block<T>> createRenderer() = TopDownGameAreaRenderer<T, B>()
    };
}

interface RendererFactory {
    fun <T : Tile, B : Block<T>> createRenderer(): GameAreaRenderer<T, B>
}
