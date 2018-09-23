package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.graphics.BoxType

object RadioButtonGroupsExample {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.rogueYun16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(tileset)
                .defaultSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel()
                .wrapWithBox(true)
                .size(Sizes.create(28, 28))
                .position(Positions.create(31, 1))
                .build()
        screen.addComponent(panel)

        val simpleRadioButtonGroup0 = Components.radioButtonGroup()
                .position(Positions.create(2, 2))
                .size(Sizes.create(14, 3))
                .build()
        val simpleRadioButtonGroup1 = Components.radioButtonGroup()
                .position(Positions.create(0, 0))
                .size(Sizes.create(14, 3))
                .build()

        listOf(simpleRadioButtonGroup0, simpleRadioButtonGroup1).forEach {
            it.addOption("key0", "First")
            it.addOption("key1", "Second")
            it.addOption("key2", "Third")
        }

        screen.addComponent(simpleRadioButtonGroup0)
        panel.addComponent(simpleRadioButtonGroup1)

        val decoratedRadioButtonGroup0 = Components.radioButtonGroup()
                .boxType(BoxType.DOUBLE)
                .wrapWithShadow(true)
                .wrapWithBox(true)
                .size(Sizes.create(14, 3))
                .position(Positions.create(2, 8))
                .build()
        val decoratedRadioButtonGroup1 = Components.radioButtonGroup()
                .boxType(BoxType.DOUBLE)
                .wrapWithShadow(true)
                .wrapWithBox(true)
                .size(Sizes.create(14, 3))
                .position(Positions.create(0, 6))
                .build()

        screen.addComponent(decoratedRadioButtonGroup0)
        panel.addComponent(decoratedRadioButtonGroup1)

        listOf(decoratedRadioButtonGroup0, decoratedRadioButtonGroup1).forEach {
            it.addOption("key0", "First")
            it.addOption("key1", "Second")
            it.addOption("key2", "Third")
        }


        screen.display()
        screen.applyColorTheme(theme)
    }

}
