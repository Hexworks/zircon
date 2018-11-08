package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.data.BlockBuilder
import org.hexworks.zircon.api.data.Tile

object Blocks {

    /**
     * Creates a new [BlockBuilder] to build [org.hexworks.zircon.api.data.Block]s.
     */
    @JvmStatic
    fun <T : Tile> newBuilder() = BlockBuilder.newBuilder<T>()
}
