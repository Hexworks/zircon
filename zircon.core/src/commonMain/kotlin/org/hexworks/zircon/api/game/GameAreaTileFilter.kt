@file:Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")

package org.hexworks.zircon.api.game

import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D


interface GameAreaTileFilter {

    fun transform(
        visibleSize: Size3D,
        offsetPosition: Position3D,
        blockTileType: BlockTileType,
        tileBuilder: TileBuilder,
    ): TileBuilder

    operator fun plus(other: GameAreaTileFilter): GameAreaTileFilter {
        return CompositeGameAreaTileFilter(this, other)
    }

    private class CompositeGameAreaTileFilter(
        private val first: GameAreaTileFilter,
        private val next: GameAreaTileFilter
    ) : GameAreaTileFilter {
        override fun transform(
            visibleSize: Size3D,
            offsetPosition: Position3D,
            blockTileType: BlockTileType,
            tileBuilder: TileBuilder
        ): TileBuilder {
            val prev = first.transform(
                visibleSize = visibleSize,
                offsetPosition = offsetPosition,
                blockTileType = blockTileType,
                tileBuilder = tileBuilder
            )
            return next.transform(
                visibleSize = visibleSize,
                offsetPosition = offsetPosition,
                blockTileType = blockTileType,
                tileBuilder = prev
            )
        }
    }

    companion object {
        val identity: GameAreaTileFilter = object : GameAreaTileFilter {
            override fun transform(
                visibleSize: Size3D,
                offsetPosition: Position3D,
                blockTileType: BlockTileType,
                tileBuilder: TileBuilder
            ): TileBuilder {
                return tileBuilder
            }

        }
    }
}
