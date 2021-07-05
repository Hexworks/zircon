package org.hexworks.zircon.examples.components


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.CursorStyle
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentAlignments.positionalAlignment
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.uievent.MouseEventType

object SliderExample {

    private val theme = ColorThemes.amigaOs()
    private val tileset = CP437TilesetResources.wanderlust16x16()

    @JvmStatic
    fun main(args: Array<String>) {
        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withCursorBlinking(true)
                .withCursorStyle(CursorStyle.UNDER_BAR)
                .withCursorColor(theme.accentColor)
                .withSize(Size.create(60, 30))
                .build())

        val screen = Screen.create(tileGrid)
        val panel = Components.panel()
                .withDecorations(box(title = "Slider on panel"), shadow())
                .withSize(Size.create(30, 28))
                .withAlignment(positionalAlignment(29, 1))
                .build()
        screen.addComponent(panel)


        val slider1 = Components.horizontalSlider()
                .withMinValue(7)
                .withMaxValue(100)
                .withNumberOfSteps(10)
                .withDecorations(box())
                .withAlignment(positionalAlignment(0, 5))
                .build()
        val label = Components.label()
                .withPreferredSize(5, 1)
                .withText("30")
                .withAlignmentAround(slider1, ComponentAlignment.RIGHT_CENTER)
                .build()
        panel.addComponent(slider1)
        panel.addComponent(label)
        label.textProperty.updateFrom(slider1.currentValueProperty) {
            it.toString()
        }
        slider1.currentValue = 35


        val slider2 = Components.horizontalSlider()
                .withMaxValue(3)
                .withNumberOfSteps(3)
                .withAlignment(positionalAlignment(0, 10))
                .withDecorations(box(BoxType.TOP_BOTTOM_DOUBLE))
                .build()
        slider2.currentValue = 2
        panel.addComponent(slider2)


        val compositeSliderPanel1 = Components.hbox()
                .withPreferredSize(17, 1)
                .withSpacing(0)
                .withAlignment(positionalAlignment(0, 15))
                .build()
        val slider3 = Components.horizontalSlider()
                .withMaxValue(255)
                .withDecorations()
                .withNumberOfSteps(10)
                .build()
        val decrementButton = Components.button()
                .withText("${Symbols.TRIANGLE_LEFT_POINTING_BLACK}")
                .withPreferredSize(1, 1)
                .withDecorations()
                .build().apply {
                    processMouseEvents(MouseEventType.MOUSE_PRESSED) { _, _ ->
                        slider3.decrementCurrentValue()
                    }
                    processComponentEvents(ComponentEventType.ACTIVATED) {
                        slider3.decrementCurrentValue()
                    }
                }
        val incrementButton = Components.button()
                .withText("${Symbols.TRIANGLE_RIGHT_POINTING_BLACK}")
                .withPreferredSize(1, 1)
                .withDecorations()
                .build().apply {
                    processComponentEvents(ComponentEventType.ACTIVATED) {
                        slider3.incrementCurrentValue()
                    }
                }
        val numberInput = Components.horizontalNumberInput(3)
                .withInitialValue(0)
                .withMaxValue(255)
                .withDecorations()
                .build()
        slider3.currentValueProperty.bind(numberInput.currentValueProperty)
        compositeSliderPanel1.addComponent(decrementButton)
        compositeSliderPanel1.addComponent(slider3)
        compositeSliderPanel1.addComponent(incrementButton)
        compositeSliderPanel1.addComponent(numberInput)
        panel.addComponent(compositeSliderPanel1)
        slider3.currentValue = 127


        val slider4 = Components.horizontalSlider()
                .withMaxValue(100)
                .withNumberOfSteps(10)
                .withAlignment(positionalAlignment(0, 20))
                .build()
        slider4.currentValue = 20
        panel.addComponent(slider4)


        val vertical = Components.verticalSlider()
                .withMaxValue(100)
                .withNumberOfSteps(10)
                .withAlignment(positionalAlignment(25, 3))
                .build()
        vertical.currentValue = 10
        panel.addComponent(vertical)


        screen.display()
        screen.theme = theme
    }

}
