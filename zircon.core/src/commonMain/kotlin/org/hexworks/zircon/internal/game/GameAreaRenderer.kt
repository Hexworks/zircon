package org.hexworks.zircon.internal.game

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.impl.DrawWindow

interface GameAreaRenderer<T : Tile, B : Block<T>> {

    fun render(
        gameArea: InternalGameArea<T, B>,
        graphics: DrawWindow,
        fillerTile: T
    )
}
