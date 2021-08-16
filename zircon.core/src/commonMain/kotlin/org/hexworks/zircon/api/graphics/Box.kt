package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.builder.graphics.BoxBuilder
import kotlin.jvm.JvmStatic

@Suppress("JVM_STATIC_IN_INTERFACE_1_6")
interface Box : TileGraphics {

    val boxType: BoxType

    companion object {

        @JvmStatic
        fun newBuilder() = BoxBuilder.newBuilder()
    }
}
