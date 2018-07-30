package org.codetome.zircon.api.data

data class Cell<T : Any>(val position: Position,
                         val tile: Tile<T>)
