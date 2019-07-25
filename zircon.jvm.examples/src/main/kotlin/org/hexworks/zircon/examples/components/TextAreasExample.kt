package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.extensions.*
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.internal.component.impl.DefaultNumberInput

object TextAreasExample {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.rogueYun16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel()
                .withDecorations(box())
                .withSize(Sizes.create(28, 28))
                .withAlignment(positionalAlignment(31, 1))
                .build()
        screen.addComponent(panel)

        screen.addComponent(Components.textArea()
                .withText("Some text")
                .withSize(Sizes.create(13, 5))
                .withAlignment(positionalAlignment(2, 2)))
        panel.addComponent(Components.textArea()
                .withText("Some text")
                .withSize(Sizes.create(13, 5))
                .withAlignment(positionalAlignment(2, 2)))

        screen.addComponent(Components.textArea()
                .withText("Some other text")
                .withDecorations(box(boxType = BoxType.DOUBLE), shadow())
                .withSize(Sizes.create(13, 7))
                .withAlignment(positionalAlignment(Positions.create(2, 8))))
        panel.addComponent(Components.textArea()
                .withText("Some other text")
                .withDecorations(box(boxType = BoxType.DOUBLE), shadow())
                .withSize(Sizes.create(13, 7))
                .withAlignment(positionalAlignment(Positions.create(2, 8))))

        screen.addComponent(Components.label()
                .withText("Numbers only!")
                .withDecorations(box())
                .withAlignment(positionalAlignment(Positions.create(2, 17))))

        val boundLabel = Components.label()
                .withText("")
                .withSize(Sizes.create(13,3))
                .withDecorations(box())
                .withAlignment(positionalAlignment(Positions.create(2, 27)))
                .build()

        val numLabel = Components.label()
                .withText("Also 256 Max!")
                .withDecorations()
                .withAlignment(positionalAlignment(Positions.create(2, 25)))
                .build()

        val hbox = Components.hbox()
                .withSize(18, 4)
                .withSpacing(0)
                .withAlignment(positionalAlignment(Positions.create(2, 20)))
                .withDecorations(box(), shadow())
                .build()

        val numberInput = Components.horizontalNumberInput(13)
                .withInitialValue(0)
                .withMaxValue(256)
                .withDecorations()
                .build().apply {
                    onValueChanged {
                        boundLabel.text = "${it.newValue}"
                    }
                }

        val decrementButton = Components.button()
                .withText("${Symbols.TRIANGLE_DOWN_POINTING_BLACK}")
                .withSize(1,1)
                .withDecorations()
                .build().apply {
                    processComponentEvents(ComponentEventType.ACTIVATED) {
                        (numberInput as? DefaultNumberInput)?.let {
                            it.decrementCurrentValue()
                        }
                    }
                }
        val incrementButton = Components.button()
                .withText("${Symbols.TRIANGLE_UP_POINTING_BLACK}")
                .withSize(1,1)
                .withDecorations()
                .build().apply {
                    processComponentEvents(ComponentEventType.ACTIVATED) {
                        (numberInput as? DefaultNumberInput)?.let {
                            it.incrementCurrentValue()
                        }
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
