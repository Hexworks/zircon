package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.builder.graphics.BoxBuilder

interface Box : TileGraphics {

    val boxType: BoxType

    companion object {

        fun newBuilder() = BoxBuilder.newBuilder()
    }
}
