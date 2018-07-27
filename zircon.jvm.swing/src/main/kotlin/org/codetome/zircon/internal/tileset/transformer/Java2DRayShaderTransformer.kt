package org.codetome.zircon.internal.tileset.transformer

import com.jhlabs.image.RaysFilter
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.api.modifier.RayShade
import org.codetome.zircon.api.tileset.TileTextureTransformer
import org.codetome.zircon.internal.tileset.impl.Java2DTileTexture
import java.awt.image.BufferedImage

class Java2DRayShaderTransformer : TileTextureTransformer<BufferedImage> {

    override fun transform(texture: TileTexture<BufferedImage>, tile: Tile): TileTexture<BufferedImage> {
        val rayShade: RayShade = tile.getModifiers().first{ it is RayShade } as RayShade
        return texture.also {
            it.getBackend().let { backend ->
                backend.graphics.apply {
                    val filter = RaysFilter()
                    filter.opacity = rayShade.opacity
                    filter.threshold = rayShade.threshold
                    filter.strength = rayShade.strength
                    filter.raysOnly = rayShade.raysOnly
                    return Java2DTileTexture(
                            cacheKey = tile.generateCacheKey(),
                            backend = filter.filter(texture.getBackend(), null))
                }
            }
        }
    }
}
