package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.resource.TilesetResource

data class GridPosition(override val x: Int,
                        override val y: Int) : Position {

    override fun toPixelPosition(tileset: TilesetResource): PixelPosition {
        return PixelPosition(x * tileset.width, y * tileset.height)
    }

    companion object {

        fun create(x: Int, y: Int): Position = GridPosition(x, y)
    }
}
