package org.hexworks.zircon.internal.integration

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.extensions.handleMouseEvents
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_RELEASED
import org.hexworks.zircon.api.uievent.Processed

object ComponentRemoveTest {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.wanderlust16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel()
                .withSize(screen.size)
                .wrapWithBox()
                .build()

        screen.addComponent(panel)

        repeat(10) {
            panel.addComponent(Components.button().withPosition(5, it + 5).withText("Remove $it").build().apply {
                handleMouseEvents(MOUSE_RELEASED) { _, _ ->
                    panel.removeComponent(this)
                    Processed
                }
            })
        }

        screen.display()
        screen.applyColorTheme(theme)


    }

}
