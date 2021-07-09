package org.hexworks.zircon.examples.dialogs


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.component.ModalBuilder
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.internal.component.modal.EmptyModalResult

object DialogExample {

    private val tileset = CP437TilesetResources.taffer20x20()
    private val theme = ColorThemes.adriftInDreams()

    class TestFragment : Fragment {
        override val root = Components.panel()
            .withPreferredSize(23, 13)
            .withColorTheme(theme)
            .build()
    }

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(
            AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(60, 30))
                .build()
        )

        val screen = Screen.create(tileGrid)

        val panel = Components.panel()
            .withPreferredSize(25, 16)
            .withDecorations(box(title = "Modal"))
            .build()


        panel.addFragment(TestFragment())

        val modal = ModalBuilder.newBuilder<EmptyModalResult>()
            .withCenteredDialog(true)
            .withParentSize(screen.size)
            .withColorTheme(theme)
            .withComponent(panel)
            .build()

        panel.addComponent(Components.button()
            .withText("Close")
            .withAlignmentWithin(panel, ComponentAlignment.BOTTOM_CENTER)
            .build().apply {
                processComponentEvents(ComponentEventType.ACTIVATED) {
                    modal.close(EmptyModalResult)
                }
            })

        screen.display()
        screen.theme = theme

        screen.openModal(modal)
    }

}
