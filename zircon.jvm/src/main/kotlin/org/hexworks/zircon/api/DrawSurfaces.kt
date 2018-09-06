package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.graphics.TileGraphicBuilder
import org.hexworks.zircon.api.builder.graphics.TileImageBuilder

object DrawSurfaces {

    /**
     * Creates a new [TileImageBuilder] to build [org.hexworks.zircon.api.graphics.TileImage]s.
     */
    @JvmStatic
    fun tileImageBuilder() = TileImageBuilder.newBuilder()

    /**
     * Creates a new [TileGraphicBuilder] to build [org.hexworks.zircon.api.graphics.TileGraphics]s.
     */
    @JvmStatic
    fun tileGraphicsBuilder() = TileGraphicBuilder.newBuilder()
}
