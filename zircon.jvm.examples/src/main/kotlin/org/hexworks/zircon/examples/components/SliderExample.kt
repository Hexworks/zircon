package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.onValueChanged
import org.hexworks.zircon.api.extensions.positionalAlignment
import org.hexworks.zircon.api.extensions.shadow

object SliderExample {

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
                .withDecorations(box(title = "Slider on panel"), shadow())
                .withSize(Sizes.create(30, 28))
                .withAlignment(positionalAlignment(29, 1))
                .build()
        screen.addComponent(panel)

        val slider1 = Components.slider()
                .withRange(100)
                .withNumberOfSteps(10)
                .withAlignment(positionalAlignment(0, 5))
                .build()

        panel.addComponent(slider1)

        val label = Components.label()
                .withSize(5,1)
                .withText("30")
                .withAlignmentAround(slider1, ComponentAlignment.BOTTOM_CENTER)
                .build()

        panel.addComponent(label)

        slider1.currentValue = 30

        slider1.onValueChanged {
            label.text = "${it.newValue}"
        }

        val slider2 = Components.slider()
                .withRange(3)
                .withNumberOfSteps(3)
                .withAlignment(positionalAlignment(0, 10))
                .build()

        panel.addComponent(slider2)

        slider2.currentValue = 2

        screen.display()
        screen.applyColorTheme(theme)
    }

}
