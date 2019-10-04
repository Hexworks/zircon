@file:Suppress("UNCHECKED_CAST")

package org.hexworks.zircon.internal.integration

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.color.ANSITileColor

object LayerMergeTest {

    private val tileset = CP437TilesetResources.rexPaint20x20()


    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 30))
                .build())

        val tempLayer = Layers.newBuilder()
                .withOffset(Positions.create(4, 4))
                .withSize(Sizes.create(2, 2))
                .withFiller(Tiles.empty().withCharacter('x').withBackgroundColor(ANSITileColor.GREEN))
                .build()

        tileGrid.addLayer(tempLayer)

        tileGrid.draw(tempLayer, Positions.create(10, 10))

        tileGrid.removeLayer(tempLayer)


    }

}
