package org.hexworks.zircon.examples

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.component.ComponentAlignment.TOP_RIGHT
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_RELEASED

object ComponentModificationExample {


    @JvmStatic
    fun main(args: Array<String>) {

        val screen = Screen.create(SwingApplications.startTileGrid())

        val button = Components.label()
                .withText("Label ")
                .withSize(10, 1)
                .build()
        val modifyButton = Components.button()
                .withText("Modify")
                .withAlignmentAround(button, TOP_RIGHT)
                .build().apply {
                    processMouseEvents(MOUSE_RELEASED) { _, _ ->
                        button.text += "x"
                    }
                }


        screen.addComponent(button)
        screen.addComponent(modifyButton)

        screen.display()
        screen.theme = ColorThemes.adriftInDreams()
    }

}
