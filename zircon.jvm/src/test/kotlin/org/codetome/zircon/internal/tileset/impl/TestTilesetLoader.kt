package org.codetome.zircon.internal.tileset.impl

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.TileTextureMetadata
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.api.tileset.TilesetLoader
import org.codetome.zircon.api.tileset.MetadataPickingStrategy
import org.codetome.zircon.api.util.Identifier
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_ARGB

class TestTilesetLoader : TilesetLoader {

    override fun fetchTiledFont(width: Int,
                                height: Int,
                                path: String,
                                cacheFonts: Boolean,
                                metadataTile: Map<Char, List<TileTextureMetadata>>,
                                metadataPickingStrategy: MetadataPickingStrategy)  = object: Tileset {

        val uuid = Identifier.randomIdentifier()

        override fun getWidth() = width

        override fun getHeight() = height

        override fun hasDataForChar(char: Char) = false

        override fun fetchRegionForChar(tile: Tile) = object : TileTexture<BufferedImage> {

            override fun generateCacheKey() = tile.generateCacheKey()

            override fun getBackend() = BufferedImage(getWidth() * 16, getHeight() * 16, TYPE_INT_ARGB)

        }

        override fun fetchMetadataForChar(char: Char) = metadataTile[char]!!

        override fun getId() = uuid

    }
}
