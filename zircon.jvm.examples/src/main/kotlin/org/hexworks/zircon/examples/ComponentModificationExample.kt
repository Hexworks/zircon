package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.kotlin.onMouseReleased

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
                    onMouseReleased {
                        button.text += "x"
                    }
                }


        screen.addComponent(button)
        screen.addComponent(modifyButton)

        screen.display()
        screen.applyColorTheme(ColorThemes.adriftInDreams())
    }

}
