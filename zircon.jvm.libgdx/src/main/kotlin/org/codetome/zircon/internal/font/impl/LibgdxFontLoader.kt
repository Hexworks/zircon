package org.codetome.zircon.internal.font.impl

import org.codetome.zircon.api.font.TextureRegionMetadata
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.font.FontLoader
import org.codetome.zircon.internal.font.MetadataPickingStrategy
import org.codetome.zircon.internal.font.cache.NoOpCache
import org.codetome.zircon.internal.font.transformer.LibgdxFontRegionCloner
import org.codetome.zircon.internal.font.transformer.LibgdxFontRegionColorizer
import org.codetome.zircon.platform.factory.CacheFactory
import java.io.File

class LibgdxFontLoader : FontLoader {

    override fun fetchPhysicalFont(size: Float,
                                   path: String,
                                   cacheFonts: Boolean,
                                   withAntiAlias: Boolean): Font {
        TODO("Implement font loading")
    }

    override fun fetchTiledFont(width: Int,
                                height: Int,
                                path: String,
                                cacheFonts: Boolean,
                                metadata: Map<Char, List<TextureRegionMetadata>>,
                                metadataPickingStrategy: MetadataPickingStrategy): Font {
        return LibgdxTiledFont(
                source = File(path).inputStream(),
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
                LibgdxFontRegionCloner(),
                LibgdxFontRegionColorizer())
    }
}
