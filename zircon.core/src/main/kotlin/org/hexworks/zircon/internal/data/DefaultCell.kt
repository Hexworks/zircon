package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.data.Cell
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile

data class DefaultCell(override val position: Position,
                       override val tile: Tile) : Cell
