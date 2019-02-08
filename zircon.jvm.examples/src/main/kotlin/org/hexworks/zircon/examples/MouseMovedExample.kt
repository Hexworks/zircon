package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.extensions.onMouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.Processed

object MouseMovedExample {


    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid()

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel().withSize(Sizes.create(4, 5)).wrapWithBox(true).build()

        screen.addComponent(panel)

        panel.onMouseEvent(MouseEventType.MOUSE_MOVED) { event, _ ->
            println(event)
            Processed
        }

        screen.display()
        screen.applyColorTheme(ColorThemes.adriftInDreams())
    }

}
