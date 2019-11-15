package org.hexworks.zircon.internal.game.impl

import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.BlockTileType.BACK
import org.hexworks.zircon.api.data.BlockTileType.BOTTOM
import org.hexworks.zircon.api.data.BlockTileType.CONTENT
import org.hexworks.zircon.api.data.BlockTileType.FRONT
import org.hexworks.zircon.api.data.BlockTileType.TOP
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.graphics.TileComposite
import org.hexworks.zircon.internal.game.ProjectionStrategy
import org.hexworks.zircon.internal.util.AnyGameAreaState
import org.hexworks.zircon.internal.util.isPositionVisible

class TopDownObliqueProjectionStrategy : ProjectionStrategy {

    override fun renderSequence(position: Position3D): Sequence<Pair<Position3D, BlockTileType>> {
        return sequence {

        }
    }

    override fun projectGameArea(gameAreaState: AnyGameAreaState): Sequence<TileComposite> {
        val (width, depth, height) = gameAreaState.visibleSize
        val (offsetX, offsetY, offsetZ) = gameAreaState.visibleOffset
        val blocks = gameAreaState.blocks
        TODO()
    }

    companion object {

        private val SIDES_LOOKUP = mapOf(
                0 to listOf(FRONT, CONTENT, BOTTOM),
                1 to listOf(TOP, BACK))

        private fun projectionVector(position: Position3D): Sequence<Pair<Position3D, BlockTileType>> {
            return sequence {
                var currPos = position

            }
        }
    }
}