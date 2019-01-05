@file:Suppress("UNUSED_VARIABLE")

package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.SwingApplications

object KotlinPlayground {

    @JvmStatic
    fun main(args: Array<String>) {
        val screen = Screens.createScreenFor(SwingApplications.startTileGrid())

        val panel = Components.panel()
                .withSize(10, 10)
                .build()

        panel.addComponent(Components.header().withText("header"))
        panel.addComponent(Components.label().withPosition(0, 1).withText("Label"))

        screen.addComponent(panel)
        screen.addComponent(Components.textArea().withText("Text area").withPosition(11, 0).withSize(10, 10))
        screen.addComponent(Components.textBox().withContentWidth(10).addHeader("Text box").withPosition(0, 11))


        screen.applyColorTheme(ColorThemes.zenburnVanilla())
        screen.display()


    }

}
