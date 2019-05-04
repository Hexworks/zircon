package org.hexworks.zircon.examples

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.extensions.onMouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.Processed

object ComponentModificationExample {


    @JvmStatic
    fun main(args: Array<String>) {

        val screen = Screens.createScreenFor(SwingApplications.startTileGrid())

        val button = Components.label()
                .withText("Label ")
                .withSize(10, 1)
                .build()
        val modifyButton = Components.button()
                .withText("Modify")
                .withAlignmentAround(button, ComponentAlignment.TOP_RIGHT)
                .build().apply {
                    onMouseEvent(MouseEventType.MOUSE_RELEASED) { _, _ ->
                        button.text += "x"
                        Processed
                    }
                }


        screen.addComponent(button)
        screen.addComponent(modifyButton)

        screen.display()
        screen.applyColorTheme(ColorThemes.adriftInDreams())
    }

}
