package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.*

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
                .wrapWithBox(true)
                .wrapWithShadow(true)
                .withSize(Sizes.create(30, 28))
                .withPosition(Positions.create(29, 1))
                .withTitle("Progress Bars on panel")
                .build()
        screen.addComponent(panel)

        val progressBar = Components.progressBar()
                .withNumberOfSteps(20)
                .withRange(100)
                .withNumberOfSteps(10)
                .withPosition(Positions.create(0, 5))
                .wrapWithBox(true)
                .build()

        val progressBarWithPercentValue = Components.progressBar()
                .withNumberOfSteps(20)
                .withPosition(Positions.create(0, 10))
                .withRange(100)
                .withNumberOfSteps(10)
                .wrapWithBox(true)
                .withDisplayPercentValueOfProgress(true)
                .build()


        panel.addComponent(progressBar)
        panel.addComponent(progressBarWithPercentValue)

        progressBar.progress = 0.0
        progressBarWithPercentValue.progress = 90.0

        screen.display()
        screen.applyColorTheme(theme)
    }

}
