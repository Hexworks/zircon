package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.data.BlockBuilder

object Blocks {

    /**
     * Creates a new [BlockBuilder] to build [org.hexworks.zircon.api.data.Block]s.
     */
    @JvmStatic
    fun newBuilder() = BlockBuilder.create()
}
