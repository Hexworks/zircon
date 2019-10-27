package org.hexworks.zircon.internal.game.impl

import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.LayerState
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.internal.game.ProjectionStrategy
import kotlin.math.min

class TopDownProjectionStrategy : ProjectionStrategy {

    override fun project(gameArea: GameArea<out Tile, out Block<out Tile>>): Sequence<LayerState> {

        val visibleLevelCount = gameArea.visibleSize.zLength
        val actualSize = gameArea.actualSize
        val visibleSize = gameArea.visibleSize
        val blocks = gameArea.blocks
        val height = gameArea.actualSize.zLength
        val fromZ = gameArea.visibleOffset.z
        val screenSize = gameArea.visibleSize.to2DSize()

//        (fromZ until min(fromZ + visibleLevelCount, height)).forEach { levelIdx ->
//            val segment = gameArea.fetchLayersAt(
//                    offset = Position3D.from2DPosition(gameArea.visibleOffset.to2DPosition(), levelIdx),
//                    size = Size3D.from2DSize(size, 1))
//            segment.forEach {
//                result.add(LayerBuilder.newBuilder()
//                        .withTileGraphics(it)
//                        // TODO: regression test this: position vs absolutePosition
//                        .withOffset(relativePosition)
//                        .build())
//            }
//        }
        TODO()
    }
}