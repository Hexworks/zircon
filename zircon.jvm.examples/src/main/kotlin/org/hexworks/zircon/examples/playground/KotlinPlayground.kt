package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.internal.resource.ColorThemeResource


object KotlinPlayground {

    @JvmStatic
    fun main(args: Array<String>) {

        val screen = SwingApplications.startTileGrid().toScreen()

        val layer0 = LayerBuilder.newBuilder()
                .withSize(Size.create(3, 4))
                .build().apply {
                    draw(Tile.defaultTile().withCharacter('x'), Position.offset1x1())
                }

        val button = Components.button()
                .withText("y")
                .withPosition(Position.create(2, 3))
                .withColorTheme(ColorThemes.adriftInDreams())
                .build()

        screen.addLayer(layer0)
        screen.addComponent(button)

        screen.display()
    }

}

