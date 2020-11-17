package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

object KotlinPlayground {

    @JvmStatic
    fun main(args: Array<String>) {

    }

    interface GameAreaTileFilter {

        fun transform(
                visibleSize: Size3D,
                blockPosition: Position3D,
                blockTileType: BlockTileType,
                tile: TileBuilder,
        ): TileBuilder
    }
}

