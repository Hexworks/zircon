package org.hexworks.zircon.internal.game.impl

import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.data.BlockTileType.BACK
import org.hexworks.zircon.api.data.BlockTileType.BOTTOM
import org.hexworks.zircon.api.data.BlockTileType.CONTENT
import org.hexworks.zircon.api.data.BlockTileType.FRONT
import org.hexworks.zircon.api.data.BlockTileType.TOP
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D
import org.hexworks.zircon.api.extensions.toTileComposite
import org.hexworks.zircon.api.graphics.TileComposite
import org.hexworks.zircon.internal.game.ProjectionStrategy
import org.hexworks.zircon.internal.util.AnyGameAreaState
import org.hexworks.zircon.internal.util.RenderSequence
import kotlin.math.max

class TopDownObliqueFrontProjectionStrategy : ProjectionStrategy {

    fun frontRenderingSequence(size: Size3D, position: Position): RenderSequence {
        return sequence {
            var currPos = Positions.create3DPosition(
                    x = position.x,
                    y = size.yLength - 1,
                    z = size.zLength - 1 - (size.yLength - position.y))
            var counter = 0
            while (currPos.z >= 0) {
                val idx = counter % 2
                SIDES_LOOKUP.getValue(idx).forEach { type ->
                    yield(currPos to type)
                }
                currPos = POS_TRANSFORM_LOOKUP.getValue(idx).invoke(currPos)
                counter++
            }
        }
    }

    fun topRenderingSequence(size: Size3D, position: Position): RenderSequence {
        return sequence {
            var currPos = Positions.create3DPosition(
                    x = position.x,
                    y = position.y,
                    z = size.zLength - 1)
            var counter = 1
            while (currPos.z >= 0) {
                val idx = counter % 2
                SIDES_LOOKUP.getValue(idx).forEach { type ->
                    yield(currPos to type)
                }
                currPos = POS_TRANSFORM_LOOKUP.getValue(idx).invoke(currPos)
                counter++
            }
        }
    }


    override fun projectGameArea(gameAreaState: AnyGameAreaState): Sequence<TileComposite> {
        val (blocks, _, visibleSize, visibleOffset) = gameAreaState
        val sectionSize = Sizes.create(visibleSize.xLength, max(visibleSize.zLength, visibleSize.yLength))
        val result = linkedMapOf<Int, MutableMap<Position, Tile>>()

        sectionSize.fetchPositions().forEach sectionLoop@{ pos ->
            var idx = 0
            val seq = if (pos.y >= visibleSize.yLength) {
                frontRenderingSequence(visibleSize, pos)
            } else {
                topRenderingSequence(visibleSize, pos)
            }
            seq.forEach sequenceLoop@{ (blockPos, blockSide) ->
                val tile = blocks[blockPos + visibleOffset]?.getTileByType(blockSide) ?: Tiles.empty()
                val layerPos = Positions.create(pos.x, pos.y)
                if (tile.isNotEmpty) {
                    result.getOrPut(idx) { mutableMapOf() }[layerPos] = tile
                    idx++
                }
                if (tile.isOpaque) {
                    return@sectionLoop
                }
            }
        }
        return sequence {
            result.forEach { (_, layer) ->
                if (layer.isNotEmpty()) {
                    yield(layer.toTileComposite(sectionSize))
                }
            }
        }
    }

    companion object {

        private val SIDES_LOOKUP = mapOf(
                0 to listOf(FRONT, CONTENT, BOTTOM),
                1 to listOf(TOP, BACK))

        private val POS_TRANSFORM_LOOKUP = mapOf(
                0 to { pos: Position3D -> pos.withRelativeZ(-1) },
                1 to { pos: Position3D -> pos.withRelativeY(-1) })
    }
}