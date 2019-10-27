package org.hexworks.zircon.internal.game.impl

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.LayerState
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.internal.game.ProjectionStrategy

class TopDownObliqueProjectionStrategy : ProjectionStrategy {

    override fun project(gameArea: GameArea<out Tile, out Block<out Tile>>): Sequence<LayerState> {
//        val visibleLevelCount = gameArea.visibleSize.zLength
//        val actualSize = gameArea.actualSize
//        val visibleSize = gameArea.visibleSize
//        val blocks = gameArea.blocks
//        val height = gameArea.actualSize.zLength
//        val fromZ = gameArea.visibleOffset.z
//        val screenSize = gameArea.visibleSize.to2DSize()
//        val fixedLayerCount = 4
//        val customLayersPerBlock = gameArea.layersPerBlock()
//        val totalLayerCount = fixedLayerCount + customLayersPerBlock
//        val builders = (0 until totalLayerCount * height).map {
//            TileGraphicsBuilder.newBuilder().withSize(screenSize)
//        }
//        val (fromX, fromY) = gameArea.visibleOffset.to2DPosition()
//        val toX = fromX + size.width
//        val toY = fromY + size.height
//        (fromZ until min(fromZ + visibleLevelCount, height)).forEach { z ->
//            (fromY until toY).forEach { screenY ->
//                (fromX until toX).forEach { x ->
//                    val y = screenY + z // we need to add `z` to `y` because of isometric
//                    val maybeBlock: Maybe<out Block<T>> = gameArea.fetchBlockAt(Position3D.create(x, y, z))
//                    val maybeNext = gameArea.fetchBlockAt(Position3D.create(x, y + 1, z))
//                    val screenPos = Position.create(x, screenY)
//                    val bottomIdx = z * totalLayerCount
//                    val frondIdx = bottomIdx + customLayersPerBlock + 1
//                    val backIdx = frondIdx + 1
//                    val topIdx = backIdx + 1
//                    maybeBlock.ifPresent { block ->
//                        val bot = block.bottom
//                        val layers = block.layers
//                        val front = block.front
//
//                        builders[bottomIdx].withTile(screenPos, bot)
//                        layers.forEachIndexed { idx, layer ->
//                            builders[bottomIdx + idx + 1].withTile(screenPos, layer)
//                        }
//                        builders[frondIdx].withTile(screenPos, front)
//                    }
//                    maybeNext.ifPresent { block ->
//                        val back = block.back
//                        val top = block.top
//                        builders[backIdx].withTile(screenPos, back)
//                        builders[topIdx].withTile(screenPos, top)
//                    }
//                }
//            }
//        }
//        builders.forEach {
//            result.add(LayerBuilder.newBuilder()
//                    .withTileGraphics(it.build())
//                    .withOffset(relativePosition)
//                    .build())
//        }
        TODO()
    }
}