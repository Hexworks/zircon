package org.codetome.zircon.internal.tileset.impl

import org.codetome.zircon.api.tileset.TileTextureMetadata
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.tileset.TilesetLoader
import org.codetome.zircon.internal.tileset.MetadataPickingStrategy
import org.codetome.zircon.internal.tileset.cache.NoOpCache
import org.codetome.zircon.internal.tileset.transformer.LibgdxTileTextureCloner
import org.codetome.zircon.internal.tileset.transformer.LibgdxTileTextureColorizer
import org.codetome.zircon.platform.factory.CacheFactory
import java.io.File

class LibgdxTilesetLoader : TilesetLoader {

    override fun fetchTiledFont(width: Int,
                                height: Int,
                                path: String,
                                cacheFonts: Boolean,
                                metadataTile: Map<Char, List<TileTextureMetadata>>,
                                metadataPickingStrategy: MetadataPickingStrategy): Tileset {
        return LibgdxTiledTileset(
                source = File(path).inputStream(),
                metadataTile = metadataTile,
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
                LibgdxTileTextureCloner(),
                LibgdxTileTextureColorizer())
    }
}
