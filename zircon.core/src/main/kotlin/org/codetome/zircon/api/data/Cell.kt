package org.codetome.zircon.api.data

/**
 * Represents a [Tile] which is at a given [Position].
 */
data class Cell(val position: Position,
                val tile: Tile)
