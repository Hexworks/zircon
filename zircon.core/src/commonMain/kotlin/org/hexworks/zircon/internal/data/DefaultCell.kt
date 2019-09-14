package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.base.BaseCell

data class DefaultCell(override val position: Position,
                       override val tile: Tile) : BaseCell()
