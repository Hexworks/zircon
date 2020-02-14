package org.hexworks.zircon.internal.integration


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_RELEASED

object ComponentRemoveTest {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.wanderlust16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(60, 30))
                .build())

        val screen = Screen.create(tileGrid)

        val panel = Components.panel()
                .withSize(screen.size)
                .withDecorations(box())
                .build()

        screen.addComponent(panel)

        repeat(10) {
            panel.addComponent(Components.button().withPosition(5, it + 5).withText("Remove $it").build()).apply {
                processMouseEvents(MOUSE_RELEASED) { _, _ ->
                    detach()
                }
            }
        }

        screen.display()
        screen.theme = theme


    }

}
