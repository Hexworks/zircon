package org.codetome.zircon.jvm.api.interop

import org.codetome.zircon.api.builder.graphics.TileImageBuilder

object TextImages {

    /**
     * Creates a new [TileImageBuilder] to build [org.codetome.zircon.api.graphics.TileImage]s.
     */
    @JvmStatic
    fun newBuilder() = TileImageBuilder.newBuilder()
}
