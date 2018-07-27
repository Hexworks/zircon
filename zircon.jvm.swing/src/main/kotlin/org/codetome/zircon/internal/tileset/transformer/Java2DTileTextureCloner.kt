package org.codetome.zircon.internal.tileset.transformer

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.internal.tileset.TileTextureTransformer
import org.codetome.zircon.internal.tileset.impl.Java2DTileTexture
import java.awt.image.BufferedImage

class Java2DTileTextureCloner : TileTextureTransformer<BufferedImage> {

    override fun transform(texture: TileTexture<BufferedImage>, tile: Tile): TileTexture<BufferedImage> {
        val backend = texture.getBackend()
        return Java2DTileTexture(
                cacheKey = tile.generateCacheKey(),
                backend = BufferedImage(backend.width, backend.height, BufferedImage.TRANSLUCENT).let { clone ->
                    clone.graphics.apply {
                        drawImage(backend, 0, 0, null)
                        dispose()
                    }
                    clone
                })
    }
}
