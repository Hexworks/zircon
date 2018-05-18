package org.codetome.zircon.api.graphics

import org.codetome.zircon.api.builder.TextImageBuilder

interface TextImageCompanion {

    /**
     * Creates a new [TextImageBuilder] to build [org.codetome.zircon.api.graphics.TextImage]s.
     */
    fun newBuilder() = TextImageBuilder()
}
