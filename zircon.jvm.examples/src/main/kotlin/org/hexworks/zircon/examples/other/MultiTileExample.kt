@file:Suppress("UNCHECKED_CAST")

package org.hexworks.zircon.examples.other


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.screen.Screen

object MultiTileExample {

    private val theme = ColorThemes.arc()
    private val tileset = CP437TilesetResources.rexPaint20x20()


    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(60, 30))
                .build())

        val screen = Screen.create(tileGrid)
        screen.display()
        screen.theme = theme

        val label = Components.label()
                .withText("Rexpaint 20x20")
                .withPosition(1, 1)
                .build()
        screen.addComponent(label)

        label.tileset = CP437TilesetResources.rexPaint20x20()

        val label2 = Components.label()
                .withText("Yobbo 20x20")
                .withPosition(1, 3)
                .build()
        screen.addComponent(label2)
        label2.tileset = CP437TilesetResources.yobbo20x20()

        val panel = Components.panel()
                .withDecorations(box(title = "Anikki 20x20"))
                .withPosition(1, 5)
                .withSize(17, 2)
                .build()
        screen.addComponent(panel)
        panel.tileset = CP437TilesetResources.anikki20x20()

        val container = Components.panel()
                .withPosition(1, 8)
                .withSize(17, 3)
                .withDecorations(box(title = "Bisasam 20x20"))
                .build()
        val label3 = Components.label()
                .withText("Oreslam 20x20")
                .build()
        container.addComponent(label3)

        screen.addComponent(container)
        container.tileset = CP437TilesetResources.bisasam20x20()
        label3.tileset = CP437TilesetResources.oreslam20x20()


    }

}
