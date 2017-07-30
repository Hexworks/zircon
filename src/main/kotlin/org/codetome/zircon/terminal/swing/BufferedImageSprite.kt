package org.codetome.zircon.terminal.swing

import org.codetome.zircon.font.Sprite
import java.awt.image.BufferedImage

class BufferedImageSprite(private val tileset: BufferedImage) : Sprite<BufferedImage> {
    override fun getSubImage(x: Int, y: Int, width: Int, height: Int): BufferedImage {
        return tileset.getSubimage(x, y, width, height)
    }
}