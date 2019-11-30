package org.hexworks.zircon.examples

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.DrawSurfaces
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.resource.REXPaintResource
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.animation.DefaultAnimationFrame

object Logo {

    val size = Size.create(59, 27)

    @JvmStatic
    fun main(args: Array<String>) {

        val rex = REXPaintResource.loadREXFile(RexLoaderExample::class.java.getResourceAsStream("/rex_files/zircon_logo.xp"))

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(CP437TilesetResources.rexPaint20x20())
                .enableBetaFeatures()
                .withSize(size)
                .build())

        val screen = Screen.create(tileGrid)

        val img = DrawSurfaces.tileGraphicsBuilder().withSize(size).build()

        rex.toLayerList().forEach {
            img.draw(it)
        }

        val builder = Animation.newBuilder()

        (20 downTo 1).forEach { idx ->
            val repeat = if (idx == 1) 40 else 1
            builder.addFrame(
                    DefaultAnimationFrame(
                            size = size,
                            layers = listOf(Layer.newBuilder()
                                    .withTileGraphics(img.toTileImage()
                                            .transform { tc ->
                                                tc.withBackgroundColor(tc.backgroundColor
                                                        .darkenByPercent(idx.toDouble().div(20)))
                                                        .withForegroundColor(tc.foregroundColor
                                                                .darkenByPercent(idx.toDouble().div(20)))
                                            }.toTileGraphics())
                                    .build()),
                            repeatCount = repeat))
        }

        (0..20).forEach { idx ->
            val repeat = if (idx == 20) 20 else 1
            builder.addFrame(
                    DefaultAnimationFrame(
                            size = size,
                            layers = listOf(Layer.newBuilder()
                                    .withTileGraphics(img.toTileImage().transform { tc ->
                                        tc.withBackgroundColor(tc.backgroundColor
                                                .darkenByPercent(idx.toDouble().div(20)))
                                                .withForegroundColor(tc.foregroundColor
                                                        .darkenByPercent(idx.toDouble().div(20)))
                                    }.toTileGraphics())
                                    .build()),
                            repeatCount = repeat))
        }

        val anim = builder
                .withLoopCount(0)
                .setPositionForAll(Position.zero())
                .build()

        screen.display()

        screen.startAnimation(anim)

    }

}
