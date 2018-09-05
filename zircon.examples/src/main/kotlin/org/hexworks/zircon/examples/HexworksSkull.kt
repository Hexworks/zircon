package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.animation.AnimationResource

object HexworksSkull {

    private val tileset = CP437TilesetResources.wanderlust16x16()
    private val size = Sizes.create(17, 27)

    @JvmStatic
    fun main(args: Array<String>) {

        val screen = Screens.createScreenFor(SwingApplications.startTileGrid(AppConfigs.newConfig()
                .enableBetaFeatures()
                .defaultTileset(tileset)
                .defaultSize(size)
                .build()))

        screen.display()

        val first = AnimationResource.loadAnimationFromStream(
                AnimationExample::class.java.getResourceAsStream("/animations/skull.zap"),
                tileset)
        first.loopCount(0)
        for (i in 0 until first.getTotalFrameCount()) {
            first.addPosition(Positions.create(2, 2))
        }
        val leftAnim = first.build()

        screen.startAnimation(leftAnim)

    }

}
