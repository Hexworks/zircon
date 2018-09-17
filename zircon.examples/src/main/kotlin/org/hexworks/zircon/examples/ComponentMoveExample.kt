package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.util.Consumer

object ComponentMoveExample {


    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel()
                .size(Sizes.create(20, 10))
                .wrapWithBox(true)
                .build()

        val innerPanel = Components.panel()
                .size(Sizes.create(10, 5))
                .wrapWithBox(true)
                .build()

        innerPanel.addComponent(Components.button()
                .text("Foo")
                .position(Positions.offset1x1())
                .build())

        panel.addComponent(innerPanel)

        screen.addComponent(panel)

        screen.display()
        screen.applyColorTheme(ColorThemes.adriftInDreams())

        Thread.sleep(2000)

        panel.moveTo(Positions.create(5, 5))

        Thread.sleep(2000)

        panel.moveTo(Positions.create(2, 2))
    }

}
