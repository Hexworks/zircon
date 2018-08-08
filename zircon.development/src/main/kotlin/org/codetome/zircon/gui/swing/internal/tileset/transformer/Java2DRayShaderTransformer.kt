package org.codetome.zircon.gui.swing.internal.tileset.transformer

import com.jhlabs.image.RaysFilter
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.modifier.RayShade
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.api.tileset.TileTextureTransformer
import org.codetome.zircon.internal.tileset.impl.DefaultTileTexture
import java.awt.image.BufferedImage

class Java2DRayShaderTransformer : TileTextureTransformer<BufferedImage> {

    override fun transform(texture: TileTexture<BufferedImage>, tile: Tile): TileTexture<BufferedImage> {
        val txt = texture.getTexture()
        val rayShade: RayShade = tile.getModifiers().first { it is RayShade } as RayShade
        return texture.also {
            it.getTexture().let { txt ->
                txt.graphics.apply {
                    val filter = RaysFilter()
                    filter.opacity = rayShade.opacity
                    filter.threshold = rayShade.threshold
                    filter.strength = rayShade.strength
                    filter.raysOnly = rayShade.raysOnly
                    return DefaultTileTexture(
                            width = texture.getWidth(),
                            height = texture.getHeight(),
                            texture = filter.filter(texture.getTexture(), null))
                }
            }
        }
    }
}
