package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.resource.TilesetResource

data class PixelPosition(override val x: Int,
                         override val y: Int) : Position {

    override fun toPixelPosition(tileset: TilesetResource) = this

    companion object {

        fun create(x: Int, y: Int): Position = PixelPosition(x, y)
    }
}
