package org.hexworks.zircon.examples.playground

import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.TrueTypeFontResources
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.uievent.ComponentEventType
import java.awt.Dimension


object KotlinPlayground {

    @JvmStatic
    fun main(args: Array<String>) {

        val resolution = Dimension(1600, 900)
        val gridSize = Dimension(80, 45)
        val tileHeight = resolution.height / gridSize.height

        val screen = SwingApplications.startTileGrid(
                AppConfig.newBuilder()
                        .withDefaultTileset(TrueTypeFontResources.ibmBios(tileHeight))
                        .withSize(gridSize.width, gridSize.height)
                        .build()).toScreen()

        screen.addComponent(
                Components.panel()
                        .withSize(30, 20)
                        .withDecorations(box(title = "hey"))
                        .withAlignmentWithin(screen, ComponentAlignment.CENTER)
                        .build())

        screen.display()
        screen.theme = ColorThemes.amigaOs()
    }

    class ConfirmButton : Fragment {

        override val root = Components.button()
                .withText("Confirm")
                .build()

        fun onConfirm(fn: () -> Unit): Subscription {
            return root.processComponentEvents(ComponentEventType.ACTIVATED) {
                fn()
            }
        }
    }

}

