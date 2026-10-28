package org.hexworks.zircon.api.game

import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.DrawWindow

public interface GameAreaRenderer<T : Tile, B : Block<T>> {

    public fun render(
        gameArea: GameArea<T, B>,
        graphics: DrawWindow,
        fillerTile: T
    )
}
