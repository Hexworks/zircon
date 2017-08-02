package org.codetome.zircon.builder

import org.codetome.zircon.font.Sprite
import org.codetome.zircon.font.TilesetFontRenderer
import org.codetome.zircon.terminal.swing.BufferedImageSprite
import org.codetome.zircon.terminal.swing.SwingCharacterImageRenderer
import org.codetome.zircon.tileset.DFTilesetResource
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class SwingFontRendererBuilder {

    private lateinit var tileset: DFTilesetResource

    /**
     * Will use the selected Dwarf Fortress tileset.
     */
    fun useDFTileset(tileset: DFTilesetResource) = also {
        this.tileset = tileset
    }

    /**
     * Will use physical fonts (loaded from the system).
     */
    fun usePhysicalFonts() = SwingPhysicalFontsBuilder()

    fun build() = TilesetFontRenderer<Sprite<BufferedImage>, BufferedImage, Graphics>(
            width = tileset.width,
            height = tileset.height,
            renderer = SwingCharacterImageRenderer(
                    fontWidth = tileset.width,
                    fontHeight = tileset.height),
            sprite = BufferedImageSprite(
                    tileset = loadDFTileset()))

    private fun loadDFTileset() = ImageIO.read(File("src/main/resources/${tileset.dir}/${tileset.fileName}"))
}