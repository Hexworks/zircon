@file:Suppress("UNCHECKED_CAST")

package org.hexworks.zircon.internal.integration

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.Layers
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile

object LayerMergeTest {

    private val tileset = CP437TilesetResources.rexPaint20x20()


    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Size.create(60, 30))
                .build())

        val tempLayer = Layers.newBuilder()
                .withOffset(Positions.create(4, 4))
                .withSize(Sizes.create(2, 2))
                .withFiller(Tile.empty().withCharacter('x').withBackgroundColor(ANSITileColor.GREEN))
                .build()

        tileGrid.addLayer(tempLayer)

        tileGrid.draw(tempLayer, Positions.create(10, 10))

        tileGrid.removeLayer(tempLayer)


    }

}
