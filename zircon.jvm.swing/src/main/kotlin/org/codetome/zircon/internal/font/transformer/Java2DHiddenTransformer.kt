package org.codetome.zircon.internal.font.transformer

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.interop.toAWTColor
import org.codetome.zircon.api.font.FontTextureRegion
import org.codetome.zircon.internal.font.FontRegionTransformer
import java.awt.image.BufferedImage

class Java2DHiddenTransformer : FontRegionTransformer<BufferedImage> {

    override fun transform(region: FontTextureRegion<BufferedImage>, tile: Tile): FontTextureRegion<BufferedImage> {
        return region.also {
            it.getBackend().let { backend ->
                backend.graphics.apply {
                    color = tile.getBackgroundColor().toAWTColor()
                    fillRect(0, 0, backend.width, backend.height)
                    dispose()
                }
            }
        }
    }
}
