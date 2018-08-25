package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.api.util.Consumer

object Playground {

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultSize(Size.create(60, 30))
                .build())

        val color = TileColors.fromString("#ffaadd")
        val tile = Tiles.defaultTile().withBackgroundColor(color).withCharacter('x')

        tileGrid.draw(tile, Positions.create(0, 0))
        tileGrid.draw(tile.withBackgroundColor(color.tint()), Positions.create(1, 0))
        tileGrid.draw(tile.withBackgroundColor(color.tint().tint()), Positions.create(2, 0))

    }

}
