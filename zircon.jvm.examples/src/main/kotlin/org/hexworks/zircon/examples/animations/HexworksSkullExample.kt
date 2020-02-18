package org.hexworks.zircon.examples.animations


import org.hexworks.zircon.api.CP437TilesetResources

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.animation.AnimationResource
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.screen.Screen

object HexworksSkullExample {

    private val tileset = CP437TilesetResources.wanderlust16x16()
    private val size = Size.create(17, 27)

    @JvmStatic
    fun main(args: Array<String>) {

        val screen = Screen.create(SwingApplications.startTileGrid(AppConfig.newBuilder()
                .enableBetaFeatures()
                .withDefaultTileset(tileset)
                .withSize(size)
                .build()))

        screen.display()

        val first = AnimationResource.loadAnimationFromStream(
                AnimationExample::class.java.getResourceAsStream("/animations/skull.zap"),
                tileset)
        first.withLoopCount(0)
        for (i in 0 until first.totalFrameCount) {
            first.addPosition(Position.create(2, 2))
        }
        val leftAnim = first.build()

        screen.start(leftAnim)

    }

}
