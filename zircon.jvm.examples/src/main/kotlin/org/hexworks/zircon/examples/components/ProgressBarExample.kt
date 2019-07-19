package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.positionalAlignment
import org.hexworks.zircon.api.extensions.shadow

object ProgressBarExample {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.wanderlust16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel()
                .withDecorations(box(title = "Progress Bars on panel"), shadow())
                .withSize(Sizes.create(30, 28))
                .withAlignment(positionalAlignment(29, 1))
                .build()
        screen.addComponent(panel)

        val progressBar = Components.progressBar()
                .withRange(100)
                .withNumberOfSteps(10)
                .withAlignment(positionalAlignment(0, 5))
                .withDecorations(box())
                .withSize(Size.create(25,3))
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
        screen.applyColorTheme(theme)
    }

}
