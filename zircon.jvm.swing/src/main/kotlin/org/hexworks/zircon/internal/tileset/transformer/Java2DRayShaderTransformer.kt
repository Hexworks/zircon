package org.hexworks.zircon.internal.tileset.transformer

import com.jhlabs.image.RaysFilter
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.RayShade
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.TextureTransformer
import org.hexworks.zircon.internal.tileset.impl.DefaultTileTexture
import java.awt.image.BufferedImage

class Java2DRayShaderTransformer : TextureTransformer<BufferedImage> {

    override fun transform(texture: TileTexture<BufferedImage>, tile: Tile): TileTexture<BufferedImage> {
        val rayShade: RayShade = tile.modifiers().first { it is RayShade } as RayShade
        return texture.also {
            it.texture().let { txt ->
                txt.graphics.apply {
                    val filter = RaysFilter()
                    filter.opacity = rayShade.opacity
                    filter.threshold = rayShade.threshold
                    filter.strength = rayShade.strength
                    filter.raysOnly = rayShade.raysOnly
                    return DefaultTileTexture(
                            width = texture.width(),
                            height = texture.height(),
                            texture = filter.filter(texture.texture(), null))
                }
            }
        }
    }
}
