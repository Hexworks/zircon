package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.CP437TilesetResource
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.api.resource.REXPaintResource
import org.hexworks.zircon.api.sam.TileTransformer
import org.hexworks.zircon.internal.animation.DefaultAnimationFrame

object Logo {

    val size = Size.create(59, 27)

    @JvmStatic
    fun main(args: Array<String>) {

        val rex = REXPaintResource.loadREXFile(RexLoaderExample::class.java.getResourceAsStream("/rex_files/zircon_logo.xp"))

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(CP437TilesetResource.ROGUE_YUN_16X16)
                .defaultSize(size)
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        screen.applyColorTheme(ColorThemeResource.GAMEBOOKERS.getTheme())

        val img = TileGraphics.newBuilder().size(size).build()

        rex.toLayerList().forEach {
            img.draw(it)
        }

        val builder = Animations.newBuilder()

        (20 downTo 1).forEach { idx ->
            builder.addFrame(
                    DefaultAnimationFrame(
                            size = size,
                            layers = listOf(Layers.newBuilder()
                                    .tileGraphic(img.transform(object : TileTransformer {
                                        override fun transform(tc: Tile): Tile {
                                            return tc.withBackgroundColor(tc.getBackgroundColor()
                                                    .darkenByPercent(idx.toDouble().div(20)))
                                                    .withForegroundColor(tc.getForegroundColor()
                                                            .darkenByPercent(idx.toDouble().div(20)))
                                        }
                                    }))
                                    .build()),
                            repeatCount = 1))
        }

        val anim = builder
                .setPositionForAll(Positions.defaultPosition())
                .build()

        screen.display()

        screen.startAnimation(anim).onFinished {
            screen.pushLayer(LayerBuilder.newBuilder()
                    .tileGraphic(img)
                    .build())
        }

    }

}
