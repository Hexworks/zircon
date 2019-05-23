package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.LibgdxApplications
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.halfBlock
import org.hexworks.zircon.api.extensions.positionalAlignment
import org.hexworks.zircon.api.extensions.shadow
import org.hexworks.zircon.api.graphics.BoxType.DOUBLE
import org.hexworks.zircon.api.graphics.BoxType.SINGLE

object LabelsExample {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.taffer20x20()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = LibgdxApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        screen.addComponent(Components.label()
                .withText("Foobar")
                .withDecorations(shadow())
                .withAlignment(positionalAlignment(2, 2))
                .build())

        screen.addComponent(Components.label()
                .withText("Barbaz wombat")
                .withSize(5, 2)
                .withAlignment(positionalAlignment(2, 6))
                .build())

        screen.addComponent(Components.label()
                .withText("Qux")
                .withDecorations(box(), shadow())
                .withAlignment(positionalAlignment(2, 10))
                .build())

        screen.addComponent(Components.label()
                .withText("Qux")
                .withDecorations(box(boxType = DOUBLE), shadow())
                .withAlignment(positionalAlignment(15, 2))
                .build())

        screen.addComponent(Components.label()
                .withText("Wtf")
                .withDecorations(box(boxType = DOUBLE), box(boxType = SINGLE), shadow())
                .withAlignment(positionalAlignment(15, 7))
                .build())

        screen.addComponent(Components.label()
                .withText("Wtf")
                .withDecorations(halfBlock(), shadow())
                .withAlignment(positionalAlignment(15, 14))
                .build())

        screen.display()
        screen.applyColorTheme(theme)
    }

}
