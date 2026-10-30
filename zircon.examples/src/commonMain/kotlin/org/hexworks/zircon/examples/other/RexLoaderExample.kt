package org.hexworks.zircon.examples.other

import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.extensions.draw
import org.hexworks.zircon.examples.EXAMPLE_TAB_WIDTH
import org.hexworks.zircon.examples.EXAMPLE_TOP
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/**
 * REXPaint files as layers: the CP437 table and the buildings sheet, each file's layers
 * drawn into one layer of the content area.
 */
public val rexLoaderExample: Example = Example("REX loader") { context ->
    val tileset = context.screen.tileset
    val files = listOf("cp437_table.xp" to Position(2, 1), "Buildings.xp" to Position(22, 1))
    val layers = files.map { (name, local) ->
        val rex = context.assets.rexFiles.getValue(name)
        val rexLayers = rex.toLayerList(tileset)
        val canvas = layer {
            size = rexLayers.first().size
            offset = Position(EXAMPLE_TAB_WIDTH, EXAMPLE_TOP) + local
            this.tileset = tileset
        }
        rexLayers.forEach { canvas.draw(it) }
        canvas
    }
    ExampleView(context.emptyContent(), layers = layers)
}
