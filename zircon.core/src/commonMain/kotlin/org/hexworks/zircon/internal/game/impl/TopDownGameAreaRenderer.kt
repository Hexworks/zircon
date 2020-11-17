package org.hexworks.zircon.internal.game.impl

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.BlockTileType.*
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.data.FastStackedTile
import org.hexworks.zircon.internal.game.GameAreaRenderer
import org.hexworks.zircon.internal.game.InternalGameArea

// TODO: test this
class TopDownGameAreaRenderer : GameAreaRenderer {

    private val blockOrder = listOf(TOP, CONTENT, BOTTOM)

    override fun render(
            gameArea: InternalGameArea<out Tile, out Block<out Tile>>,
            graphics: TileGraphics,
            fillerTile: Tile
    ) {
        val (blocks, _, visibleSize, visibleOffset, filter) = gameArea.state

        for (x in 0 until visibleSize.xLength) {
            for (y in 0 until visibleSize.yLength) {
                val stack = FastStackedTile(initialCapacity = visibleSize.zLength * 3)
                stacking@ for (z in (visibleSize.zLength - 1) downTo 0) {
                    val pos = Position3D.create(
                            x = x + visibleOffset.x,
                            y = y + visibleOffset.y,
                            z = z + visibleOffset.z // TODO: test all offsets!
                    )
                    val block = blocks[pos]
                    if (block != null) {
                        for (order in blockOrder) {
                            val tile = block.tiles[order]
                            if (tile != null) {
                                stack.addFirst(filter.transform(
                                        visibleSize = visibleSize,
                                        offsetPosition = pos - visibleOffset,
                                        blockTileType = order,
                                        tile.toBuilder()
                                ).build())
                                if (tile.isOpaque) {
                                    break@stacking
                                }
                            }
                        }
                    }
                }
                stack.addFirst(fillerTile)
                graphics.draw(stack, Position.create(x, y))
            }
        }
    }

}
