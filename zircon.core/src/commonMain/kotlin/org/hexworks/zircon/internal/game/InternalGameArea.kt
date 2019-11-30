package org.hexworks.zircon.internal.game

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.graphics.TileImage
import org.hexworks.zircon.api.resource.TilesetResource

interface InternalGameArea<T : Tile, B : Block<T>> : GameArea<T, B> {

    val state: GameAreaState<T, B>
    
    /**
     * The [TileImage] (s) representing the contents of this
     * [InternalGameArea].
     */
    fun fetchImageLayers(tileset: TilesetResource): Sequence<TileImage>
}