package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.positionalAlignment
import org.hexworks.zircon.api.extensions.shadow
import org.hexworks.zircon.api.graphics.BoxType

object CheckBoxesExample {

    private val theme = ColorThemes.gamebookers()
    private val tileset = CP437TilesetResources.taffer20x20()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(60, 30)
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel()
                .withDecorations(box())
                .withSize(Sizes.create(28, 28))
                .withAlignment(positionalAlignment(31, 1))
                .build()
        screen.addComponent(panel)

        val simpleCheckBox = Components.checkBox()
                .withText("Check me")
                .withAlignment(positionalAlignment(2, 2))

        screen.addComponent(simpleCheckBox)
        panel.addComponent(simpleCheckBox)

        val decoratedCheckBox = Components.checkBox()
                .withText("Check me")
                .withDecorations(box(boxType = BoxType.DOUBLE), shadow())
                .withAlignment(positionalAlignment(2, 4))

        screen.addComponent(decoratedCheckBox)
        panel.addComponent(decoratedCheckBox)

        val shadowedCheckBox = Components.checkBox()
                .withText("Check me")
                .withDecorations(shadow())
                .withAlignment(positionalAlignment(2, 9))

        screen.addComponent(shadowedCheckBox)
        panel.addComponent(shadowedCheckBox)

        val tooLongCheckBox = Components.checkBox()
                .withText("Too long text")
                .withSize(12, 1)
                .withAlignment(positionalAlignment(2, 13))

        screen.addComponent(tooLongCheckBox)
        panel.addComponent(tooLongCheckBox)

        val overTheTopCheckBox = Components.checkBox()
                .withText("Over the top")
                .withDecorations(
                        box(boxType = BoxType.DOUBLE),
                        box(boxType = BoxType.SINGLE),
                        box(boxType = BoxType.LEFT_RIGHT_DOUBLE),
                        shadow())
                .withAlignment(positionalAlignment(2, 16))

        screen.addComponent(overTheTopCheckBox)
        panel.addComponent(overTheTopCheckBox)


        screen.applyColorTheme(theme)
        screen.display()
    }

}
