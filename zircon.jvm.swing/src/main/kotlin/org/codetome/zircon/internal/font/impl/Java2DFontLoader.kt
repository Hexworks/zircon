package org.codetome.zircon.internal.font.impl

import org.codetome.zircon.api.font.TextureRegionMetadata
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.util.JVMFontUtils
import org.codetome.zircon.api.font.FontLoader
import org.codetome.zircon.internal.font.MetadataPickingStrategy
import org.codetome.zircon.internal.font.cache.NoOpCache
import org.codetome.zircon.internal.font.transformer.Java2DFontRegionCloner
import org.codetome.zircon.internal.font.transformer.Java2DFontRegionColorizer
import org.codetome.zircon.platform.factory.CacheFactory
import java.awt.GraphicsEnvironment
import java.io.File
import javax.imageio.ImageIO

class Java2DFontLoader : FontLoader {

    override fun fetchPhysicalFont(size: Float,
                                   path: String,
                                   cacheFonts: Boolean,
                                   withAntiAlias: Boolean): Font {
        val font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, File(path)).deriveFont(size)
        val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
        ge.registerFont(font)

        return Java2DPhysicalFont(
                source = font,
                width = JVMFontUtils.getFontWidth(font),
                height = JVMFontUtils.getFontHeight(font),
                cache = if (cacheFonts) {
                    CacheFactory.create()
                } else {
                    NoOpCache()
                },
                withAntiAlias = withAntiAlias)
    }

    override fun fetchTiledFont(width: Int,
                                height: Int,
                                path: String,
                                cacheFonts: Boolean,
                                metadata: Map<Char, List<TextureRegionMetadata>>,
                                metadataPickingStrategy: MetadataPickingStrategy): Font {
        return Java2DTiledFont(
                source = ImageIO.read(File(path)),
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
