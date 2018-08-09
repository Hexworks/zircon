package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.tileset.Tileset

data class AbsolutePosition(override val x: Int,
                            override val y: Int) : Position {

    override fun toAbsolutePosition(tileset: Tileset<out Any>) = this

    companion object {

        fun create(x: Int, y: Int): Position = AbsolutePosition(x, y)
    }
}
