package org.hexworks.zircon.internal.game.impl

import org.hexworks.zircon.internal.extensions.asInternal
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.BlockTileType.*
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.isOpaque
import org.hexworks.zircon.api.graphics.DrawWindow
import org.hexworks.zircon.internal.data.FastStackedTile
import org.hexworks.zircon.api.game.GameAreaRenderer

// TODO: test this
public class TopDownGameAreaRenderer<T : Tile, B : Block<T>> : GameAreaRenderer<T, B> {

    private val blockOrder = listOf(TOP, CONTENT, BOTTOM)

    override fun render(
        gameArea: GameArea<T, B>,
        graphics: DrawWindow,
        fillerTile: T
    ) {
        val (blocks, _, visibleSize, visibleOffset, filter) = gameArea.asInternal().state

        for (x in 0 until visibleSize.xLength) {
            for (y in 0 until visibleSize.yLength) {
                val stack = FastStackedTile(initialCapacity = visibleSize.zLength * 3)
                stacking@ for (z in (visibleSize.zLength - 1) downTo 0) {
                    val pos = Position3D(
                        x = x + visibleOffset.x,
                        y = y + visibleOffset.y,
                        z = z + visibleOffset.z // TODO: test all offsets!
                    )
                    val block = blocks[pos]
                    if (block != null) {
                        for (order in blockOrder) {
                            val tile = block.tiles[order]
                            if (tile != null) {
                                stack.addFirst(
                                    filter.transform(
                                        visibleSize = visibleSize,
                                        offsetPosition = pos - visibleOffset,
                                        blockTileType = order,
                                        tile = tile
                                    )
                                )
                                if (tile.isOpaque) {
                                    break@stacking
                                }
                            }
                        }
                    }
                }
                stack.addFirst(fillerTile)
                graphics.draw(stack, Position(x, y))
            }
        }
    }

}
