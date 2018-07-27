package org.codetome.zircon.internal.tileset.transformer

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.interop.toAWTColor
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.internal.tileset.TileTextureTransformer
import java.awt.image.BufferedImage

class Java2DCrossedOutTransformer : TileTextureTransformer<BufferedImage> {

    override fun transform(texture: TileTexture<BufferedImage>, tile: Tile): TileTexture<BufferedImage> {
        return texture.also {
            it.getBackend().let { backend ->
                backend.graphics.apply {
                    color = tile.getForegroundColor().toAWTColor()
                    fillRect(0, backend.height / 2, backend.width, 2)
                    dispose()
                }
            }
        }
    }
}
