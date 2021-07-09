package org.hexworks.zircon.examples.components


import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.TrueTypeFontResources
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentAlignments.positionalAlignment
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.ComponentEventType

object TextAreasExample {

    private val theme = ColorThemes.arc()
    private val tileset = TrueTypeFontResources.kaypro(16)

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(
            AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(60, 30))
                .build()
        )

        val screen = Screen.create(tileGrid)

        val panel = Components.panel()
            .withDecorations(box())
            .withPreferredSize(28, 28)
            .withPosition(31, 1)
            .build()
        screen.addComponent(panel)

        screen.addComponent(
            Components.textArea()
                .withText("Some text")
                .withPreferredSize(13, 5)
                .withPosition(2, 2)
        )
        panel.addComponent(
            Components.textArea()
                .withText("Some text")
                .withPreferredSize(13, 5)
                .withPosition(2, 2)
        )

        screen.addComponent(
            Components.textArea()
                .withText("Some other text")
                .withDecorations(box(boxType = BoxType.DOUBLE), shadow())
                .withPreferredSize(13, 7)
                .withPosition(2, 8)
        )
        panel.addComponent(
            Components.textArea()
                .withText("Some other text")
                .withDecorations(box(boxType = BoxType.DOUBLE), shadow())
                .withPreferredSize(13, 7)
                .withPosition(2, 8)
        )

        screen.addComponent(
            Components.label()
                .withText("Numbers only!")
                .withDecorations(box())
                .withAlignment(positionalAlignment(Position.create(2, 17)))
        )

        val boundLabel = Components.label()
            .withText("")
            .withSize(Size.create(13, 3))
            .withDecorations(box())
            .withPosition(2, 27)
            .build()

        val numLabel = Components.label()
            .withText("Also 256 Max!")
            .withDecorations()
            .withPosition(2, 25)
            .build()

        val hbox = Components.hbox()
            .withPreferredSize(18, 4)
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
            .withPreferredSize(1, 1)
            .withDecorations()
            .build().apply {
                processComponentEvents(ComponentEventType.ACTIVATED) {
                    numberInput.decrementCurrentValue()
                }
            }
        val incrementButton = Components.button()
            .withText("${Symbols.TRIANGLE_UP_POINTING_BLACK}")
            .withPreferredSize(1, 1)
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
        screen.theme = theme
    }

}
