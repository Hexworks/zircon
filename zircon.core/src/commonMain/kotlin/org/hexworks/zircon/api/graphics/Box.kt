package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.builder.graphics.BoxBuilder
import kotlin.jvm.JvmStatic

interface Box : TileGraphics {

    val boxType: BoxType

    companion object {

        @JvmStatic
        fun newBuilder() = BoxBuilder()
    }
}
