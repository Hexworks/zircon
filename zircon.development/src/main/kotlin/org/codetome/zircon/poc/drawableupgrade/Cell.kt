package org.codetome.zircon.poc.drawableupgrade

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.poc.drawableupgrade.tile.Tile

data class Cell<T : Any>(val position: Position,
                         val tile: Tile<T>)
