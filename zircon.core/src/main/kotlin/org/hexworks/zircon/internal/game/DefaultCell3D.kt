package org.hexworks.zircon.internal.game

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.game.base.BaseCell3D

data class DefaultCell3D<T : Tile, B : Block<T>>(override val position: Position3D,
                                                 override val block: B) : BaseCell3D<T, B>()
