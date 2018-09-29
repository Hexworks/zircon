package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.kotlin.onMouseMoved

object MouseMovedExample {


    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid()

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel().size(Sizes.create(4, 5)).wrapWithBox(true).build()

        screen.addComponent(panel)

        panel.onMouseMoved {
            println(it)
        }

        screen.display()
        screen.applyColorTheme(ColorThemes.adriftInDreams())
    }

}
