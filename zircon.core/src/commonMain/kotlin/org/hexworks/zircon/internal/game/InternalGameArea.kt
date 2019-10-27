package org.hexworks.zircon.internal.game

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.LayerState
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea

interface InternalGameArea<T : Tile, B : Block<T>> : GameArea<T, B> {

    /**
     * The [LayerState] (s) representing the contents of this
     * [InternalGameArea].
     */
    val layerStates: Sequence<LayerState>
}