package org.hexworks.zircon.internal.game

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.GameAreaTileFilter

public interface InternalGameArea<T : Tile, B : Block<T>> : GameArea<T, B> {

    public val state: GameAreaState<T, B>

    public val filter: GameAreaTileFilter<T>
}
