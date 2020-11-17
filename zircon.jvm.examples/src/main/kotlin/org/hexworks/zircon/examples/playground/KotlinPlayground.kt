package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.builder.data.BlockBuilder
import org.hexworks.zircon.api.data.*

object KotlinPlayground {

    @JvmStatic
    fun main(args: Array<String>) {

    }

    interface BlockFilter<T: Tile> {

        fun transform(
                visibleSize: Size3D,
                blockPosition: Position3D,
                block: BlockBuilder<T>,
        ): BlockBuilder<T>
    }
}

