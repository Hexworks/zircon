package org.codetome.zircon

import org.codetome.zircon.api.builder.component.ButtonBuilder
import org.codetome.zircon.api.builder.component.ComponentStyleSetBuilder
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.gui.swing.impl.BufferedImageCP437Tileset
import org.codetome.zircon.gui.swing.impl.SwingFrame
import org.codetome.zircon.internal.component.impl.DefaultButton
import org.codetome.zircon.internal.grid.RectangleTileGrid
import org.codetome.zircon.internal.screen.TileGridScreen
import org.codetome.zircon.internal.util.DefaultThreadSafeQueue
import java.awt.image.BufferedImage

fun main(args: Array<String>) {


    val size = Size.create(70, 40)
    val tileset = CP437TilesetResource.WANDERLUST_16X16
    val tileGrid: TileGrid = RectangleTileGrid(tileset, size)
    val frame = SwingFrame(tileGrid)
    val screen = TileGridScreen(tileGrid)

    frame.isVisible = true

    frame.renderer.create()

    screen.addComponent(ButtonBuilder.newBuilder()
            .tileset(tileset)
            .text("Foo")
            .build())

    screen.display()
    frame.renderer.render()

}

