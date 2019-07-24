package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.onValueChanged
import org.hexworks.zircon.api.extensions.positionalAlignment
import org.hexworks.zircon.api.extensions.shadow
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.internal.component.impl.DefaultNumberTextArea

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

        val numberTextArea = Components.numberTextArea()
                .withInitialValue(0)
                .withMaxValue(256)
                .withSize(Sizes.create(13, 5))
                .withDecorations(box(), shadow())
                .withAlignment(positionalAlignment(Positions.create(2, 20)))
                .build().apply {
                    onValueChanged {
                        boundLabel.text = "${it.newValue}"
                    }
                }

        screen.addComponent(numberTextArea)
        screen.addComponent(numLabel)
        screen.addComponent(boundLabel)

        screen.display()
        screen.applyColorTheme(theme)
    }

}
