package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.CursorStyle
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.onValueChanged
import org.hexworks.zircon.api.extensions.positionalAlignment
import org.hexworks.zircon.api.extensions.shadow
import org.hexworks.zircon.api.graphics.BoxType

object SliderExample {

    private val theme = ColorThemes.amigaOs()
    private val tileset = CP437TilesetResources.wanderlust16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withCursorBlinking(true)
                .withCursorStyle(CursorStyle.UNDER_BAR)
                .withCursorColor(theme.accentColor)
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel()
                .withDecorations(box(title = "Slider on panel"), shadow())
                .withSize(Sizes.create(30, 28))
                .withAlignment(positionalAlignment(29, 1))
                .build()
        screen.addComponent(panel)

        val slider1 = Components.horizontalSlider()
                .withRange(100)
                .withNumberOfSteps(10)
                .withDecorations(box())
                .withAlignment(positionalAlignment(0, 5))
                .build()

        panel.addComponent(slider1)

        val label = Components.label()
                .withSize(5,1)
                .withText("30")
                .withAlignmentAround(slider1, ComponentAlignment.RIGHT_CENTER)
                .build()

        panel.addComponent(label)

        slider1.currentValue = 30

        slider1.onValueChanged {
            label.text = "${it.newValue}"
        }

        val slider2 = Components.horizontalSlider()
                .withRange(3)
                .withNumberOfSteps(3)
                .withAlignment(positionalAlignment(0, 10))
                .withDecorations(box(BoxType.TOP_BOTTOM_DOUBLE))
                .build()

        panel.addComponent(slider2)

        slider2.currentValue = 2

        val slider3 = Components.horizontalSlider()
                .withRange(255)
                .withNumberOfSteps(10)
                .withAlignment(positionalAlignment(0, 15))
                .build()

        panel.addComponent(slider3)

        slider3.currentValue = 127

        val slider4 = Components.horizontalSlider()
                .withRange(100)
                .withNumberOfSteps(10)
                .withAlignment(positionalAlignment(0, 20))
                .build()

        panel.addComponent(slider4)

        slider4.currentValue = 20

        val vertical = Components.verticalSlider()
                .withRange(100)
                .withNumberOfSteps(10)
                .withAlignment(positionalAlignment(25, 3))
                .build()

        panel.addComponent(vertical)

        vertical.currentValue = 10

        screen.display()
        screen.applyColorTheme(theme)
    }

}
