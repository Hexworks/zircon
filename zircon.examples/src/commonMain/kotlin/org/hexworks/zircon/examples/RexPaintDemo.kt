package org.hexworks.zircon.examples

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.loadREXFile
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.graphics.extensions.draw
import org.hexworks.zircon.api.resource.Resource
import org.hexworks.zircon.api.resource.ResourceType

/**
 * The Zircon logo from a REXPaint file, its layers drawn over the content area.
 */
public val rexPaintDemo: Demo = Demo("REXPaint", CP437TilesetResources.rexPaint16x16()) { context ->
    val tileset = context.screen.tileset
    val logo = loadREXFile(Resource.create("/rex_files/zircon_logo.xp", ResourceType.PROJECT))
    val canvas = layer {
        size = context.contentSize
        offset = context.contentPosition
        this.tileset = tileset
    }
    logo.toLayerList(tileset).forEach { canvas.draw(it) }
    context.screen.addLayer(canvas)
    DemoView(context.emptyContent())
}
