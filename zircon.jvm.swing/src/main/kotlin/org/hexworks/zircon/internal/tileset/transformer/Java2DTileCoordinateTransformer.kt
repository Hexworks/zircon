package org.hexworks.zircon.internal.tileset.transformer

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.tileset.TextureTransformer
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.internal.modifier.TileCoordinate
import org.hexworks.zircon.internal.tileset.impl.DefaultTileTexture
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.RenderingHints.KEY_TEXT_ANTIALIASING
import java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON
import java.awt.image.BufferedImage


class Java2DTileCoordinateTransformer : TextureTransformer<BufferedImage> {

    override fun transform(texture: TileTexture<BufferedImage>, tile: Tile): TileTexture<BufferedImage> {
        val pos = tile.modifiers.filterIsInstance(TileCoordinate::class.java).first().position
        texture.also {
            it.texture.let { txt ->
                txt.graphics.apply {
                    this as Graphics2D
                    setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON)
                    setColor(Color.MAGENTA)
                    setFont(Font("monospace", Font.PLAIN, texture.height / 2))
                    drawString("${pos.x}", 1, texture.height / 2)
                    drawString("${pos.y}", 1, texture.height - 1)
                    return DefaultTileTexture(
                        width = texture.width,
                        height = texture.height,
                        texture = txt
                    )
                }
            }
        }
    }
}
