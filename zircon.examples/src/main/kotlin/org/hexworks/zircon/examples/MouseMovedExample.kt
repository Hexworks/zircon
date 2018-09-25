package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.util.Consumer

object MouseMovedExample {


    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid()

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel().size(Sizes.create(4, 5)).wrapWithBox(true).build()

        screen.addComponent(panel)

        panel.onMouseMoved(object : Consumer<MouseAction> {
            override fun accept(value: MouseAction) {
                println(value)
            }
        })

        screen.display()
        screen.applyColorTheme(ColorThemes.adriftInDreams())
    }

}
