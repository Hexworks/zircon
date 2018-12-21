@file:Suppress("UNCHECKED_CAST")

package org.hexworks.zircon.internal.integration

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.kotlin.onMouseReleased

object DataBindingTest {

    private val theme = ColorThemes.arc()
    private val tileset = CP437TilesetResources.rexPaint20x20()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel()
                .withTitle("Panel title")
                .withSize(40, 20)
                .wrapWithBox()
                .build()

        val label = Components.label()
                .withText("Label")
                .withPosition(1, 1)
                .withSize(20, 1)
                .build()

        val header = Components.header()
                .withText("Header")
                .withPosition(1, 2)
                .withSize(20, 1)
                .build()

        val btn = Components.button()
                .withText("Button")
                .withPosition(1, 3)
                .withSize(20, 1)
                .wrapSides(false)
                .build()

        val checkBox = Components.checkBox()
                .withText("Check box")
                .withPosition(1, 4)
                .withSize(20, 1)
                .build()

        val rbg = Components.radioButtonGroup()
                .withPosition(1, 5)
                .withSize(20, 1)
                .build()
        val option = rbg.addOption("btn0", "Radio button")

        screen.addComponent(panel)

        val master = Components.label()
                .withText("foo")
                .withPosition(25, 1)
                .withSize(10, 1)
                .build()

        val tamper = Components.button()
                .withText("Tamper")
                .withPosition(25, 2)
                .build().apply {
                    onMouseReleased {
                        master.textProperty.value = master.text + "x"
                    }
                }

        val bind = Components.button()
                .withText("Bind")
                .withPosition(25, 3)
                .build().apply {
                    onMouseReleased {
                        panel.titleProperty.bind(master.textProperty)
                        label.textProperty.bind(master.textProperty)
                        header.textProperty.bind(master.textProperty)
                        btn.textProperty.bind(master.textProperty)
                        checkBox.textProperty.bind(master.textProperty)
                        option.textProperty.bind(master.textProperty)
                    }
                }

        val unbind = Components.button()
                .withText("Unbind")
                .withPosition(25, 4)
                .build().apply {
                    master.textProperty.unbind()
                }

        panel.addComponent(label)
        panel.addComponent(header)
        panel.addComponent(btn)
        panel.addComponent(checkBox)
        panel.addComponent(rbg)

        panel.addComponent(master)
        panel.addComponent(tamper)
        panel.addComponent(bind)
        panel.addComponent(unbind)

        screen.display()
        screen.applyColorTheme(theme)
    }

}
