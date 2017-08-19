package org.codetome.zircon.font.impl

import org.codetome.zircon.Modifier.*
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.extensions.isNotPresent
import org.codetome.zircon.font.CharacterMetadata
import org.codetome.zircon.font.Font
import org.codetome.zircon.font.impl.cache.DefaultFontRegionCache
import org.codetome.zircon.font.impl.transformer.*
import org.codetome.zircon.util.FontUtils
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

        var region = if (maybeRegion.isNotPresent()) {
            val image = BufferedImage(width, height, BufferedImage.TRANSLUCENT)
            val g = image.graphics as Graphics2D
            g.color = textCharacter.getBackgroundColor().toAWTColor()
            g.fillRect(0, 0, width, height)
            g.color = textCharacter.getForegroundColor().toAWTColor()
            g.font = source
            if (withAntiAlias) {
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
            image
        } else {
            maybeRegion.get()
        }
        textCharacter.getModifiers().forEach {
            region = MODIFIER_TRANSFORMER_LOOKUP[it]!!.transform(region, textCharacter)
        }
        return region
    }

    override fun fetchMetadataForChar(char: Char) = listOf<CharacterMetadata>()

    companion object {
        val MODIFIER_TRANSFORMER_LOOKUP = mapOf(
                Pair(UNDERLINE, Java2DUnderlineTransformer()),
                Pair(VERTICAL_FLIP, Java2DVerticalFlipper()),
                Pair(HORIZONTAL_FLIP, Java2DHorizontalFlipper()),
                Pair(CROSSED_OUT, Java2DCrossedOutTransformer()),
                Pair(BLINK, NoOpTransformer()),
                Pair(HIDDEN, Java2DHiddenTransformer())
        ).toMap()
    }
}