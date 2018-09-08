package org.hexworks.zircon.internal.tileset.transformer

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.Crop
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.TileTextureTransformer
import org.hexworks.zircon.internal.tileset.impl.DefaultTileTexture
import java.awt.image.BufferedImage


class Java2DCropTransformer : TileTextureTransformer<BufferedImage> {

    override fun transform(texture: TileTexture<BufferedImage>, tile: Tile): TileTexture<BufferedImage> {
        val (x, y, width, height) = tile.getModifiers().first { it is Crop } as Crop
        val txt = texture.getTexture()
        val newImage = BufferedImage(texture.getWidth(), texture.getWidth(), BufferedImage.TRANSLUCENT)
        newImage.createGraphics()
                .drawImage(txt.getSubimage(x, y, width, height), x, y, null)
        return DefaultTileTexture(
                width = txt.width,
                height = txt.height,
                texture = newImage)
    }
}
