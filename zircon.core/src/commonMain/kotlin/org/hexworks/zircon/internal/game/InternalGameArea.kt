package org.hexworks.zircon.internal.game

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.GameAreaTileFilter

interface InternalGameArea<T : Tile, B : Block<T>> : GameArea<T, B> {

    val state: GameAreaState<T, B>

    val filter: GameAreaTileFilter<T>
}
