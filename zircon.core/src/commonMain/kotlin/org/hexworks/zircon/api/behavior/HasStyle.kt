package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.StyleSet

interface HasStyle<T : Tile> {

    val styleSet: StyleSet

    fun withStyle(style: StyleSet): T

}
