package org.hexworks.zircon.examples

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.uievent.MouseEventType

object MouseMovedExample {


    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid()

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel()
                .withSize(Sizes.create(4, 5))
                .withDecorations(box())
                .build()

        screen.addComponent(panel)

        panel.processMouseEvents(MouseEventType.MOUSE_MOVED) { event, _ ->
            println(event)
        }

        screen.display()
        screen.applyColorTheme(ColorThemes.adriftInDreams())
    }

}
