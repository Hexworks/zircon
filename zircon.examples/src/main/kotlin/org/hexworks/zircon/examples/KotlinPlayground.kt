package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.internal.component.impl.DefaultContainer

object KotlinPlayground {

    val TILESET = BuiltInCP437TilesetResource.WANDERLUST_16X16

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(TILESET)
                .defaultSize(Sizes.create(30, 20))
                .build())

        val screen = Screens.createScreenFor(tileGrid)



        screen.display()


        screen.applyColorTheme(ColorThemes.hexworks())

    }

}
