package org.codetome.zircon.api.interop

import org.codetome.zircon.api.builder.TextImageBuilder

object TextImages {

    /**
     * Creates a new [TextImageBuilder] to build [org.codetome.zircon.api.graphics.TextImage]s.
     */
    @JvmStatic
    fun newBuilder() = TextImageBuilder()
}
