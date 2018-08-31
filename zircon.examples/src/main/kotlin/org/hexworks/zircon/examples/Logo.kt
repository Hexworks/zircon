package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.api.resource.REXPaintResource
import org.hexworks.zircon.api.util.TileTransformer
import org.hexworks.zircon.internal.animation.DefaultAnimationFrame

object Logo {

    val size = Size.create(59, 27)

    @JvmStatic
    fun main(args: Array<String>) {

        val rex = REXPaintResource.loadREXFile(RexLoaderExample::class.java.getResourceAsStream("/rex_files/zircon_logo.xp"))

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(BuiltInCP437TilesetResource.REX_PAINT_20X20)
                .defaultSize(size)
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val img = TileGraphics.newBuilder().size(size).build()

        rex.toLayerList().forEach {
            img.draw(it)
        }

        val builder = Animations.newBuilder()

        (20 downTo 1).forEach { idx ->
            val repeat = if(idx == 1) 40 else 1
            builder.addFrame(
                    DefaultAnimationFrame(
                            size = size,
                            layers = listOf(Layers.newBuilder()
                                    .tileGraphic(img.toTileImage().transform(object : TileTransformer {
                                        override fun transform(tc: Tile): Tile {
                                            return tc.withBackgroundColor(tc.getBackgroundColor()
                                                    .darkenByPercent(idx.toDouble().div(20)))
                                                    .withForegroundColor(tc.getForegroundColor()
                                                            .darkenByPercent(idx.toDouble().div(20)))
                                        }
                                    }).toTileGraphic())
                                    .build()),
                            repeatCount = repeat))
        }

        (0 .. 20).forEach { idx ->
            val repeat = if(idx == 20) 20 else 1
            builder.addFrame(
                    DefaultAnimationFrame(
                            size = size,
                            layers = listOf(Layers.newBuilder()
                                    .tileGraphic(img.toTileImage().transform(object : TileTransformer {
                                        override fun transform(tc: Tile): Tile {
                                            return tc.withBackgroundColor(tc.getBackgroundColor()
                                                    .darkenByPercent(idx.toDouble().div(20)))
                                                    .withForegroundColor(tc.getForegroundColor()
                                                            .darkenByPercent(idx.toDouble().div(20)))
                                        }
                                    }).toTileGraphic())
                                    .build()),
                            repeatCount = repeat))
        }

        val anim = builder
                .loopCount(0)
                .setPositionForAll(Positions.defaultPosition())
                .build()

        screen.display()

        screen.startAnimation(anim)

    }

}
