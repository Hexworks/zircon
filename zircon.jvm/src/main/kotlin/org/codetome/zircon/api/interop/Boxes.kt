package org.codetome.zircon.api.interop

import org.codetome.zircon.api.builder.graphics.BoxBuilder

object Boxes {

    /**
     * Creates a new [BoxBuilder] to build [org.codetome.zircon.api.graphics.Box]es.
     */
    @JvmStatic
    fun newBuilder() = BoxBuilder.newBuilder()
}
