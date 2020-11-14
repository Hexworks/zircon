package org.hexworks.zircon.internal.game.impl

import kotlinx.collections.immutable.persistentListOf
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.data.BlockTileType.*
import org.hexworks.zircon.api.extensions.toTileImage
import org.hexworks.zircon.api.graphics.TileImage
import org.hexworks.zircon.internal.game.ProjectionStrategy
import org.hexworks.zircon.internal.util.AnyGameAreaState
import org.hexworks.zircon.internal.util.RenderSequence
import kotlin.math.max

class TopDownObliqueFrontProjectionStrategy : ProjectionStrategy {

    private fun frontRenderingSequence(size: Size3D, position: Position): RenderSequence {
        return sequence {
            var currPos = Position3D.create(x = position.x,
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

    private fun topRenderingSequence(size: Size3D, position: Position): RenderSequence {
        return sequence {
            var currPos = Position3D.create(x = position.x,
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


    override fun projectGameArea(gameAreaState: AnyGameAreaState): Sequence<TileImage> {
        val (blocks, _, visibleSize, visibleOffset, tileset) = gameAreaState
        val sectionSize = Size.create(visibleSize.xLength, max(visibleSize.zLength, visibleSize.yLength))
        val layersMap = linkedMapOf<Int, MutableMap<Position, Tile>>()

        sectionSize.fetchPositions().forEach sectionLoop@{ pos ->
            var idx = 0
            val seq = if (pos.y >= visibleSize.yLength) {
                frontRenderingSequence(visibleSize, pos)
            } else {
                topRenderingSequence(visibleSize, pos)
            }
            seq.forEach sequenceLoop@{ (blockPos, blockSide) ->
                val tile = blocks[blockPos + visibleOffset]?.getTileByType(blockSide) ?: Tile.empty()
                val layerPos = Position.create(pos.x, pos.y)
                if (tile.isNotEmpty) {
                    layersMap.getOrPut(idx) { mutableMapOf() }[layerPos] = tile
                    idx++
                }
                if (tile.isOpaque) {
                    return@sectionLoop
                }
            }
        }
        var result = persistentListOf<TileImage>()
        layersMap.forEach { (_, layer) ->
            if (layer.isNotEmpty()) {
                result = result.add(0, layer.toTileImage(sectionSize, tileset))
            }
        }
        return result.asSequence()
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
