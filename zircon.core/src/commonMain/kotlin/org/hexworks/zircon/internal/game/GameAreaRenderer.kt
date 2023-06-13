package org.hexworks.zircon.internal.game

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.impl.DrawWindow

interface GameAreaRenderer {

    fun render(
        gameArea: InternalGameArea<out Tile, out Block<out Tile>>,
        graphics: DrawWindow,
        fillerTile: Tile
    )
}
