package org.hexworks.zircon.internal.graphics

import kotlinx.coroutines.channels.Channel
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileComposite

data class DefaultTileComposite(
        override val tiles: Map<Position, Tile>,
        override val size: Size
) : TileComposite {

    val foo: List<String> = mutableListOf()
}
