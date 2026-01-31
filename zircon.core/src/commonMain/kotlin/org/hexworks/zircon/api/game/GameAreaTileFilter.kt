package org.hexworks.zircon.api.game

import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile


/**
 * A [GameAreaTileFilter] can be used to transform the visible tiles within a [GameArea]
 * before rendering. This can be used for example to apply a depth effect or some other
 * visual effect (such as fog) that doesn't have game logic in it and only adds visuals.
 */
interface GameAreaTileFilter<T : Tile> {

    fun transform(
        visibleSize: Size3D,
        offsetPosition: Position3D,
        blockTileType: BlockTileType,
        tile: T,
    ): T

    /**
     * Allows the chaining of [GameAreaTileFilter]s eg: `filter0 + filter1`
     * will apply `filter0` and then `filter1`.
     */
    operator fun plus(other: GameAreaTileFilter<T>): GameAreaTileFilter<T> {
        return CompositeGameAreaTileFilter(this, other)
    }

    private class CompositeGameAreaTileFilter<T : Tile>(
        private val first: GameAreaTileFilter<T>,
        private val next: GameAreaTileFilter<T>
    ) : GameAreaTileFilter<T> {
        override fun transform(
            visibleSize: Size3D,
            offsetPosition: Position3D,
            blockTileType: BlockTileType,
            tile: T
        ): T {
            val prev = first.transform(
                visibleSize = visibleSize,
                offsetPosition = offsetPosition,
                blockTileType = blockTileType,
                tile = tile
            )
            return next.transform(
                visibleSize = visibleSize,
                offsetPosition = offsetPosition,
                blockTileType = blockTileType,
                tile = prev
            )
        }
    }

    companion object {
        fun <T : Tile> identity(): GameAreaTileFilter<T> = object : GameAreaTileFilter<T> {
            override fun transform(
                visibleSize: Size3D,
                offsetPosition: Position3D,
                blockTileType: BlockTileType,
                tile: T
            ): T {
                return tile
            }

        }
    }
}
