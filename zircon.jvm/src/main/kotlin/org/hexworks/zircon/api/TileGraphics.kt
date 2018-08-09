package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.graphics.TileGraphicBuilder

object TileGraphics {

    /**
     * Creates a new [TileGraphicBuilder] to build [org.hexworks.zircon.api.graphics.TileGraphic]s.
     */
    @JvmStatic
    fun newBuilder() = TileGraphicBuilder.newBuilder()
}
