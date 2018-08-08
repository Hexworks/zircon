package org.codetome.zircon.gui.swing.internal.tileset.transformer

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.api.tileset.TileTextureTransformer
import java.awt.image.BufferedImage

class NoOpTransformer : TileTextureTransformer<BufferedImage> {
    override fun transform(texture: TileTexture<BufferedImage>, tile: Tile) = texture
}
