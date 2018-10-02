package org.hexworks.zircon.internal.tileset.transformer

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.Crop
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.TextureTransformer
import org.hexworks.zircon.internal.tileset.impl.DefaultTileTexture
import java.awt.image.BufferedImage


class Java2DCropTransformer : TextureTransformer<BufferedImage> {

    override fun transform(texture: TileTexture<BufferedImage>, tile: Tile): TileTexture<BufferedImage> {
        val (x, y, width, height) = tile.modifiers.first { it is Crop } as Crop
        val txt = texture.texture()
        val newImage = BufferedImage(texture.width(), texture.width(), BufferedImage.TRANSLUCENT)
        newImage.createGraphics()
                .drawImage(txt.getSubimage(x, y, width, height), x, y, null)
        return DefaultTileTexture(
                width = txt.width,
                height = txt.height,
                texture = newImage)
    }
}
