package org.codetome.zircon.font

import org.codetome.zircon.TextCharacter
import org.codetome.zircon.extensions.isNotPresent
import org.codetome.zircon.font.cache.DefaultFontRegionCache
import org.codetome.zircon.util.FontUtils
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage

/**
 * Represents a physical font which is backed by [java.awt.Font].
 */
class PhysicalFont(private val source: java.awt.Font,
                   private val width: Int,
                   private val height: Int,
                   private val cache: DefaultFontRegionCache<BufferedImage>,
                   private val withAntiAlias: Boolean) : Font<BufferedImage> {

    init {
        require(FontUtils.isFontMonospaced(source)) {
            "Font '${source.name} is not monospaced!"
        }
    }

    override fun getWidth() = width

    override fun getHeight() = height

    override fun hasDataForChar(char: Char) = source.canDisplay(char)

    override fun fetchRegionForChar(textCharacter: TextCharacter, vararg tags: String): BufferedImage {

        val maybeRegion = cache.retrieveIfPresent(textCharacter)

        return if(maybeRegion.isNotPresent()) {
            val image = BufferedImage(width, height, BufferedImage.TRANSLUCENT)
            val g = image.graphics as Graphics2D
            g.color = textCharacter.getBackgroundColor().toAWTColor()
            g.fillRect(0, 0, width, height)
            g.color = textCharacter.getForegroundColor().toAWTColor()
            g.font = source
            if(withAntiAlias) {
                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            }

            val str = textCharacter.getCharacter().toString()

            val fm = g.getFontMetrics(source)
            val rect = fm.getStringBounds(str, g)

            val textHeight = rect.height.toInt()
            val textWidth = rect.width.toInt()
            val panelHeight = this.getHeight()
            val panelWidth = this.getWidth()

            val x = (panelWidth - textWidth) / 2
            val y = (panelHeight - textHeight) / 2 + fm.ascent

            g.drawString(str, x, y)
            g.dispose()
            cache.store(textCharacter, image)
            return image
        } else {
            maybeRegion.get()
        }
    }

    override fun fetchMetadataForChar(char: Char) = listOf<CharacterMetadata>()
}