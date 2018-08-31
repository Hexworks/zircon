package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.graphics.TileImageBuilder

object TileImages {

    /**
     * Creates a new [TileImageBuilder] to build [org.hexworks.zircon.api.graphics.TileImage]s.
     */
    @JvmStatic
    fun newBuilder() = TileImageBuilder.newBuilder()
}
