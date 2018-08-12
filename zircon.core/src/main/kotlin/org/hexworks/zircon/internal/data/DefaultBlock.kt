package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Tile

data class DefaultBlock(
        override val position: Position3D,
        override val top: Tile,
        override val back: Tile,
        override val front: Tile,
        override val bottom: Tile,
        override val layers: MutableList<Tile>) : Block
