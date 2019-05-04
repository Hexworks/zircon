package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.transform
import org.hexworks.zircon.api.resource.REXPaintResource
import org.hexworks.zircon.internal.animation.DefaultAnimationFrame

object Logo {

    val size = Size.create(59, 27)

    @JvmStatic
    fun main(args: Array<String>) {

        val rex = REXPaintResource.loadREXFile(RexLoaderExample::class.java.getResourceAsStream("/rex_files/zircon_logo.xp"))

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(CP437TilesetResources.rexPaint20x20())
                .enableBetaFeatures()
                .withSize(size)
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val img = DrawSurfaces.tileGraphicsBuilder().withSize(size).build()

        rex.toLayerList().forEach {
            img.draw(it)
        }

        val builder = Animations.newBuilder()

        (20 downTo 1).forEach { idx ->
            val repeat = if (idx == 1) 40 else 1
            builder.addFrame(
                    DefaultAnimationFrame(
                            size = size,
                            layers = listOf(Layers.newBuilder()
                                    .withTileGraphics(img.toTileImage()
                                            .transform { tc ->
                                                tc.withBackgroundColor(tc.backgroundColor
                                                        .darkenByPercent(idx.toDouble().div(20)))
                                                        .withForegroundColor(tc.foregroundColor
                                                                .darkenByPercent(idx.toDouble().div(20)))
                                            }.toTileGraphic())
                                    .build()),
                            repeatCount = repeat))
        }

        (0..20).forEach { idx ->
            val repeat = if (idx == 20) 20 else 1
            builder.addFrame(
                    DefaultAnimationFrame(
                            size = size,
                            layers = listOf(Layers.newBuilder()
                                    .withTileGraphics(img.toTileImage().transform { tc ->
                                        tc.withBackgroundColor(tc.backgroundColor
                                                .darkenByPercent(idx.toDouble().div(20)))
                                                .withForegroundColor(tc.foregroundColor
                                                        .darkenByPercent(idx.toDouble().div(20)))
                                    }.toTileGraphic())
                                    .build()),
                            repeatCount = repeat))
        }

        val anim = builder
                .withLoopCount(0)
                .setPositionForAll(Positions.zero())
                .build()

        screen.display()

        screen.startAnimation(anim)

    }

}
