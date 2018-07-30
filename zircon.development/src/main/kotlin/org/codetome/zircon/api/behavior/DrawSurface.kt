package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Tile
import java.util.*

interface DrawSurface<T : Any> {

    fun getTileAt(position: Position): Optional<Tile<T>>

    fun setTileAt(position: Position, tile: Tile<T>)

    fun createSnapshot(): Map<Position, Tile<T>>

    fun draw(drawable: Drawable<T>,
             offset: Position)
}
