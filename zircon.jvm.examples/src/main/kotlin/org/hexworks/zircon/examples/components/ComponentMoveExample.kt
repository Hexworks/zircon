package org.hexworks.zircon.examples.components


import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.screen.Screen

object ComponentMoveExample {

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withSize(Size.create(60, 30))
                .build())

        val screen = Screen.create(tileGrid)

        val panel = Components.panel()
                .withSize(Size.create(20, 10))
                .withDecorations(box())
                .build()

        val innerPanel = Components.panel()
                .withSize(Size.create(10, 5))
                .withDecorations(box())
                .build()

        innerPanel.addComponent(Components.button()
                .withText("Foo")
                .withPosition(Position.offset1x1())
                .build())

        panel.addComponent(innerPanel)

        screen.addComponent(panel)

        screen.display()
        screen.theme = ColorThemes.adriftInDreams()

        Thread.sleep(2000)

        panel.moveTo(Position.create(5, 5))

        Thread.sleep(2000)

        innerPanel.moveBy(Position.create(2, 2))
    }

}
