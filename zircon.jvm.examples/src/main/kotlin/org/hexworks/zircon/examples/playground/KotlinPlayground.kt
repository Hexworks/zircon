package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.LayerHandle
import org.hexworks.zircon.examples.base.displayScreen

object KotlinPlayground {

    @JvmStatic
    fun main(args: Array<String>) {

        val screen = displayScreen()

        val layerHandle: LayerHandle = screen.addLayer(LayerBuilder.newBuilder()
                .withSize(1, 1)
                .withFiller(Tile.defaultTile().withCharacter('x'))
                .withOffset(1, 1)
                .build())

        Thread.sleep(1000)

        layerHandle.moveTo(Position.create(4, 4))

    }


}

