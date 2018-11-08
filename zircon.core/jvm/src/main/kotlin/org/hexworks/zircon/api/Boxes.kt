package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.graphics.BoxBuilder

object Boxes {

    /**
     * Creates a new [BoxBuilder] to build [org.hexworks.zircon.api.graphics.Box]es.
     */
    @JvmStatic
    fun newBuilder() = BoxBuilder.newBuilder()
}
