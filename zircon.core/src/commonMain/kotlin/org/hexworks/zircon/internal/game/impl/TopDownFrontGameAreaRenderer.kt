package org.hexworks.zircon.internal.game.impl

import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.data.FastStackedTile
import org.hexworks.zircon.internal.game.GameAreaRenderer
import org.hexworks.zircon.internal.game.InternalGameArea
import kotlin.math.max

// TODO: test this
class TopDownFrontGameAreaRenderer : GameAreaRenderer {

    override fun render(
        gameArea: InternalGameArea<out Tile, out Block<out Tile>>,
        graphics: TileGraphics,
        fillerTile: Tile
    ) {
        val (blocks, _, visibleSize, visibleOffset, filter) = gameArea.state

        for (x in 0 until graphics.width) {
            val projectionSequence = generateProjectionSequence(
                visibleSize = visibleSize,
                visibleOffset = visibleOffset,
                x = x + visibleOffset.x,
                height = graphics.height
            )
            // TODO: test regression with y: was not translated to graphics position
            var y = 0
            for (vector in projectionSequence) {
                val stack = FastStackedTile()
                stacking@ for ((pos, side) in vector) {
                    val tile = blocks[pos]?.getTileByType(side)
                    if (tile != null) {
                        stack.addFirst(
                            filter.transform(
                                visibleSize = visibleSize,
                                offsetPosition = pos - visibleOffset,
                                blockTileType = side,
                                tile.toBuilder()
                            ).build()
                        )
                        if (tile.isOpaque) {
                            break@stacking
                        }
                    }
                }
                stack.addFirst(fillerTile)
                graphics.draw(stack, Position.create(x, y))
                y++
            }
        }

    }

    fun generateProjectionSequence(
        visibleSize: Size3D,
        visibleOffset: Position3D,
        x: Int,
        height: Int
    ): Sequence<Sequence<Pair<Position3D, BlockTileType>>> = sequence {
        val maxY = visibleOffset.y + visibleSize.yLength - 1
        val minZ = visibleOffset.z
        var currPos = Position3D.create(
            x = x, // TODO: test regression:  `+ visibleOffset.x`
            y = 0 + visibleOffset.y,
            z = visibleSize.zLength - 1 + visibleOffset.z
        )
        var counter = max(height, visibleSize.yLength + visibleSize.zLength)
        do {
            yield(generateTopSequence(visibleOffset, currPos))
            currPos = currPos.withRelativeY(1)
            counter--
        } while (counter > 0 && currPos.y <= maxY)
        currPos = currPos.withRelativeY(-1)
        do {
            yield(generateFrontSequence(visibleOffset, currPos))
            currPos = currPos.withRelativeZ(-1)
            counter--
        } while (counter > 0 && currPos.z >= minZ)
    }

    fun generateFrontSequence(
        visibleOffset: Position3D,
        startPos: Position3D
    ): Sequence<Pair<Position3D, BlockTileType>> = sequence {
        var y = startPos.y
        var z = startPos.z
        val minY = visibleOffset.y
        val minZ = visibleOffset.z
        // TODO: test these bounds
        while (y >= minY && z >= minZ) {
            for (value in FrontTraversal.values()) {
                val pos = Position3D.create(
                    x = startPos.x,
                    y = value.yTransformer(y),
                    z = value.zTransformer(z)
                )
                if (pos.hasNegativeComponent.not()) {
                    yield(pos to value.facing)
                }
            }
            y--
            z--
        }
    }

    fun generateTopSequence(
        visibleOffset: Position3D,
        startPos: Position3D
    ): Sequence<Pair<Position3D, BlockTileType>> = sequence {
        var y = startPos.y
        var z = startPos.z
        val minY = visibleOffset.y
        val minZ = visibleOffset.z
        // TODO: test these bounds
        while (y >= minY && z >= minZ) {
            for (value in TopTraversal.values()) {
                val pos = Position3D.create(
                    x = startPos.x,
                    y = value.yTransformer(y),
                    z = value.zTransformer(z)
                )
                if (pos.hasNegativeComponent.not()) {
                    yield(pos to value.facing)
                }
            }
            y--
            z--
        }
    }

    private enum class FrontTraversal(
        val yTransformer: (x: Int) -> Int,
        val zTransformer: (z: Int) -> Int,
        val facing: BlockTileType
    ) {
        FRONT(
            yTransformer = { y -> y },
            zTransformer = { z -> z },
            facing = BlockTileType.FRONT
        ),
        BOTTOM(
            yTransformer = { y -> y },
            zTransformer = { z -> z },
            facing = BlockTileType.BOTTOM
        ),
        TOP(
            yTransformer = { y -> y },
            zTransformer = { z -> z - 1 },
            facing = BlockTileType.TOP
        ),
        BACK(
            yTransformer = { y -> y },
            zTransformer = { z -> z - 1 },
            facing = BlockTileType.BACK
        )
    }

    private enum class TopTraversal(
        val yTransformer: (x: Int) -> Int,
        val zTransformer: (z: Int) -> Int,
        val facing: BlockTileType
    ) {
        TOP(
            yTransformer = { y -> y },
            zTransformer = { z -> z },
            facing = BlockTileType.TOP
        ),
        BACK(
            yTransformer = { y -> y },
            zTransformer = { z -> z },
            facing = BlockTileType.BACK
        ),
        FRONT(
            yTransformer = { y -> y - 1 },
            zTransformer = { z -> z },
            facing = BlockTileType.FRONT
        ),
        BOTTOM(
            yTransformer = { y -> y - 1 },
            zTransformer = { z -> z },
            facing = BlockTileType.BOTTOM
        )
    }
}
