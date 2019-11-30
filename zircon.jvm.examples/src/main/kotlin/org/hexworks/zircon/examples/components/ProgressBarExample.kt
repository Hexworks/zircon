package org.hexworks.zircon.examples.components


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.positionalAlignment
import org.hexworks.zircon.api.extensions.shadow
import org.hexworks.zircon.api.screen.Screen

object ProgressBarExample {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.wanderlust16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(60, 30))
                .build())

        val screen = Screen.create(tileGrid)

        val panel = Components.panel()
                .withDecorations(box(title = "Progress Bars on panel"), shadow())
                .withSize(Size.create(30, 28))
                .withAlignment(positionalAlignment(29, 1))
                .build()
        screen.addComponent(panel)

        val progressBar = Components.progressBar()
                .withRange(100)
                .withNumberOfSteps(10)
                .withAlignment(positionalAlignment(0, 5))
                .withDecorations(box())
                .withSize(Size.create(25, 3))
                .build()

        val progressBarWithPercentValue = Components.progressBar()
                .withNumberOfSteps(20)
                .withAlignment(positionalAlignment(0, 10))
                .withRange(100)
                .withDecorations(box())
                .build()


        panel.addComponent(progressBar)
        panel.addComponent(progressBarWithPercentValue)

        progressBar.progress = 60.0
        progressBarWithPercentValue.progress = 90.0

        screen.display()
        screen.theme = theme
    }

}
