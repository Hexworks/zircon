package org.hexworks.zircon.internal.game.impl

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.game.base.BaseGameArea
import org.hexworks.zircon.internal.game.ProjectionStrategy

class DefaultGameArea<T : Tile, B : Block<T>>(
        initialVisibleSize: Size3D,
        initialActualSize: Size3D,
        initialContents: Map<Position3D, B> = mapOf(),
        projectionStrategy: ProjectionStrategy = ProjectionMode.TOP_DOWN.projectionStrategy) : BaseGameArea<T, B>(
        initialVisibleSize = initialVisibleSize,
        initialActualSize = initialActualSize,
        initialContents = initialContents,
        projectionStrategy = projectionStrategy)
