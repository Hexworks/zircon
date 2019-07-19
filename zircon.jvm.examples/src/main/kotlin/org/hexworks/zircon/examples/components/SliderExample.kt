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

        val slider = Components.slider()
                .withRange(100)
                .withNumberOfSteps(10)
                .withAlignment(positionalAlignment(0, 5))
                .withDecorations(box())
                .build()

        panel.addComponent(slider)

        val label = Components.label()
                .withSize(5,1)
                .withText("30")
                .withAlignmentAround(slider, ComponentAlignment.BOTTOM_CENTER)
                .build()

        panel.addComponent(label)

        slider.currentValue = 30

        slider.currentValueProperty.onChange {
            label.text = "${it.newValue}"
        }

        screen.display()
        screen.applyColorTheme(theme)
    }

}
