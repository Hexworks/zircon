package org.codetome.zircon.internal.tileset.transformer

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.interop.toAWTColor
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.internal.tileset.TileTextureTransformer
import java.awt.image.BufferedImage

class Java2DHiddenTransformer : TileTextureTransformer<BufferedImage> {

    override fun transform(texture: TileTexture<BufferedImage>, tile: Tile): TileTexture<BufferedImage> {
        return texture.also {
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
