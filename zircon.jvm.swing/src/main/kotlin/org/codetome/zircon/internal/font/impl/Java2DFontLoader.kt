package org.codetome.zircon.internal.font.impl

import org.codetome.zircon.api.font.CharacterMetadata
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.util.FontUtils
import org.codetome.zircon.internal.font.FontLoader
import org.codetome.zircon.internal.font.MetadataPickingStrategy
import org.codetome.zircon.internal.font.cache.NoOpCache
import org.codetome.zircon.internal.font.transformer.Java2DFontRegionCloner
import org.codetome.zircon.internal.font.transformer.Java2DFontRegionColorizer
import org.codetome.zircon.platform.factory.CacheFactory
import java.awt.GraphicsEnvironment
import java.io.InputStream
import javax.imageio.ImageIO

class Java2DFontLoader : FontLoader {

    override fun fetchPhysicalFont(size: Float,
                                   source: InputStream,
                                   cacheFonts: Boolean,
                                   withAntiAlias: Boolean): Font {
        val font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, source).deriveFont(size)
        val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
        ge.registerFont(font)

        return Java2DPhysicalFont(
                source = font,
                width = FontUtils.getFontWidth(font),
                height = FontUtils.getFontHeight(font),
                cache = if (cacheFonts) {
                    CacheFactory.create()
                } else {
                    NoOpCache()
                },
                withAntiAlias = withAntiAlias)
    }

    override fun fetchTiledFont(width: Int,
                                height: Int,
                                source: InputStream,
                                cacheFonts: Boolean,
                                metadata: Map<Char, List<CharacterMetadata>>,
                                metadataPickingStrategy: MetadataPickingStrategy): Font {
        return Java2DTiledFont(
                source = ImageIO.read(source),
                metadata = metadata,
                width = width,
                height = height,
                cache = if (cacheFonts) {
                    CacheFactory.create()
                } else {
                    NoOpCache()
                },
                regionTransformers = TILE_TRANSFORMERS)
    }

    companion object {
        private val TILE_TRANSFORMERS = listOf(
                Java2DFontRegionCloner(),
                Java2DFontRegionColorizer())
    }
}
