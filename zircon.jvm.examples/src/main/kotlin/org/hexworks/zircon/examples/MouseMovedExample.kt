package org.hexworks.zircon.examples

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.MouseEventType

object MouseMovedExample {


    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid()

        val screen = Screen.create(tileGrid)

        val panel = Components.panel()
                .withSize(Size.create(4, 5))
                .withDecorations(box())
                .build()

        screen.addComponent(panel)

        panel.processMouseEvents(MouseEventType.MOUSE_MOVED) { event, _ ->
            println(event)
        }

        screen.display()
        screen.theme = ColorThemes.adriftInDreams()
    }

}
