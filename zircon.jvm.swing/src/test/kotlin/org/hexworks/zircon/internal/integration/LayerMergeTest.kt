@file:Suppress("UNCHECKED_CAST")

package org.hexworks.zircon.internal.integration


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Layer

object LayerMergeTest {

    private val tileset = CP437TilesetResources.rexPaint20x20()


    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(60, 30))
                .build())

        val tempLayer = Layer.newBuilder()
                .withOffset(Position.create(4, 4))
                .withSize(Size.create(2, 2))
                .withFiller(Tile.empty().withCharacter('x').withBackgroundColor(ANSITileColor.GREEN))
                .build()

        tileGrid.addLayer(tempLayer)

        tileGrid.draw(tempLayer, Position.create(10, 10))

        tileGrid.removeLayer(tempLayer)


    }

}
