package org.hexworks.zircon.examples.dialogs

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.builder.component.ModalBuilder
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.internal.component.modal.EmptyModalResult

// TODO: not working
object DialogExample {

    private val tileset = CP437TilesetResources.taffer20x20()
    private val theme = ColorThemes.adriftInDreams()

    class TestFragment : Fragment {
        override val root = Components.panel().withSize(23, 13).build()
    }

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel()
                .withSize(25, 16)
                .withDecorations(box(title = "Modal"))
                .build()


        panel.addFragment(TestFragment())

        val modal = ModalBuilder.newBuilder<EmptyModalResult>()
                .withCenteredDialog(true)
                .withParentSize(screen.size)
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
        panel.applyColorTheme(theme)

        screen.display()
        screen.applyColorTheme(theme)

        screen.openModal(modal)
    }

}
