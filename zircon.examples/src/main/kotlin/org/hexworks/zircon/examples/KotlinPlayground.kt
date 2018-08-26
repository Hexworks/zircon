package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.resource.BuiltInCP437Tileset
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.api.util.Consumer

object KotlinPlayground {

    private val SIZE = Sizes.create(50, 30)
    private val TILESET = BuiltInCP437Tileset.TAFFER_20X20

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(TILESET)
                .defaultSize(SIZE)
                .debugMode(true)
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val btn = Components.button().text("foo").build()

        screen.addComponent(btn)

        btn.onMouseMoved { mouseAction: MouseAction -> println(mouseAction) }

        screen.display()

    }

}
