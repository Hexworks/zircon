package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.TrueTypeFontResources
import org.hexworks.zircon.api.extensions.*
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.uievent.ComponentEventType

object TextAreasExample {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = TrueTypeFontResources.kaypro(16)

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel()
                .withDecorations(box())
                .withSize(28, 28)
                .withPosition(31, 1)
                .build()
        screen.addComponent(panel)

        screen.addComponent(Components.textArea()
                .withText("Some text")
                .withSize(13, 5)
                .withPosition(2, 2))
        panel.addComponent(Components.textArea()
                .withText("Some text")
                .withSize(13, 5)
                .withPosition(2, 2))

        screen.addComponent(Components.textArea()
                .withText("Some other text")
                .withDecorations(box(boxType = BoxType.DOUBLE), shadow())
                .withSize(13, 7)
                .withPosition(2, 8))
        panel.addComponent(Components.textArea()
                .withText("Some other text")
                .withDecorations(box(boxType = BoxType.DOUBLE), shadow())
                .withSize(13, 7)
                .withPosition(2, 8))

        screen.addComponent(Components.label()
                .withText("Numbers only!")
                .withDecorations(box())
                .withAlignment(positionalAlignment(Positions.create(2, 17))))

        val boundLabel = Components.label()
                .withText("")
                .withSize(Sizes.create(13,3))
                .withDecorations(box())
                .withPosition(2, 27)
                .build()

        val numLabel = Components.label()
                .withText("Also 256 Max!")
                .withDecorations()
                .withPosition(2, 25)
                .build()

        val hbox = Components.hbox()
                .withSize(18, 4)
                .withSpacing(0)
                .withPosition(2, 20)
                .withDecorations(box(), shadow())
                .build()

        val numberInput = Components.horizontalNumberInput(13)
                .withInitialValue(0)
                .withMinValue(14)
                .withMaxValue(256)
                .withDecorations()
                .build()

        boundLabel.textProperty.updateFrom(numberInput.currentValueProperty) {
            it.toString()
        }

        val decrementButton = Components.button()
                .withText("${Symbols.TRIANGLE_DOWN_POINTING_BLACK}")
                .withSize(1,1)
                .withDecorations()
                .build().apply {
                    processComponentEvents(ComponentEventType.ACTIVATED) {
                        numberInput.decrementCurrentValue()
                    }
                }
        val incrementButton = Components.button()
                .withText("${Symbols.TRIANGLE_UP_POINTING_BLACK}")
                .withSize(1,1)
                .withDecorations()
                .build().apply {
                    processComponentEvents(ComponentEventType.ACTIVATED) {
                        numberInput.incrementCurrentValue()
                    }
                }
        hbox.addComponent(decrementButton)
        hbox.addComponent(numberInput)
        hbox.addComponent(incrementButton)

        screen.addComponent(hbox)
        screen.addComponent(numLabel)
        screen.addComponent(boundLabel)

        screen.display()
        screen.applyColorTheme(theme)
    }

}
