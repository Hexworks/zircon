package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.graphics.TileCompositeBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.builder.graphics.TileImageBuilder
import org.hexworks.zircon.api.graphics.DrawSurface
import kotlin.jvm.JvmStatic

/**
 * This *facade* object can be used to create builders for [DrawSurface] implementations.
 */
object DrawSurfaces {

    /**
     * Creates a new [TileImageBuilder] to build [org.hexworks.zircon.api.graphics.TileImage]s.
     */
    @JvmStatic
    fun tileImageBuilder() = TileImageBuilder.newBuilder()

    /**
     * Creates a new [TileGraphicsBuilder] to build [org.hexworks.zircon.api.graphics.TileGraphics]s.
     */
    @JvmStatic
    fun tileGraphicsBuilder() = TileGraphicsBuilder.newBuilder()

    /**
     * Creates a new [TileCompositeBuilder] to build [org.hexworks.zircon.api.graphics.TileComposite]s.
     */
    @JvmStatic
    fun tileCompositeBuilder() = TileCompositeBuilder.newBuilder()
}
