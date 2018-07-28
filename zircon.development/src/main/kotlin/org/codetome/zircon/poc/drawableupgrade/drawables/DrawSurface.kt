package org.codetome.zircon.poc.drawableupgrade.drawables

import org.codetome.zircon.poc.drawableupgrade.position.GridPosition
import org.codetome.zircon.poc.drawableupgrade.tile.Tile
import java.util.*

interface DrawSurface<T : Any> {

    fun getTileAt(position: GridPosition): Optional<Tile<T>>

    fun setTileAt(position: GridPosition, tile: Tile<T>)

    fun createSnapshot(): Map<GridPosition, Tile<T>>

    fun draw(drawable: Drawable<T>,
             offset: GridPosition)
}
