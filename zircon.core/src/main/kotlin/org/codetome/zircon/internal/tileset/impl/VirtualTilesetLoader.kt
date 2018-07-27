package org.codetome.zircon.internal.tileset.impl

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.TileTextureMetadata
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.tileset.TilesetLoader
import org.codetome.zircon.api.util.Maybe
import org.codetome.zircon.internal.tileset.MetadataPickingStrategy

class VirtualTilesetLoader : TilesetLoader {

    // TODO: this retard trick is temporary, fix it later

    override fun fetchTiledFont(width: Int,
                                height: Int,
                                path: String,
                                cacheFonts: Boolean,
                                metadataTile: Map<Char, List<TileTextureMetadata>>,
                                metadataPickingStrategy: MetadataPickingStrategy): Tileset {
        return object : Tileset {

            private var actualTileset: Maybe<Tileset> = Maybe.empty()

            override fun getWidth() =
                    tryToGetActualFont().getWidth()

            override fun getHeight() =
                    tryToGetActualFont().getHeight()

            override fun hasDataForChar(char: Char) =
                    tryToGetActualFont().hasDataForChar(char)

            override fun fetchRegionForChar(tile: Tile) =
                    tryToGetActualFont().fetchRegionForChar(tile)

            override fun fetchMetadataForChar(char: Char) =
                    tryToGetActualFont().fetchMetadataForChar(char)

            override fun getId() =
                    tryToGetActualFont().getId()

            private fun tryToGetActualFont(): Tileset {
                return actualTileset.orElseGet {
                    if (TilesetLoaderRegistry.getCurrentFontLoader() === this@VirtualTilesetLoader) {
                        throw IllegalStateException("No tileset")
                    } else {
                        actualTileset = Maybe.of(TilesetLoaderRegistry.getCurrentFontLoader().fetchTiledFont(
                                width = width,
                                height = height,
                                path = path,
                                cacheFonts = cacheFonts,
                                metadataPickingStrategy = metadataPickingStrategy,
                                metadataTile = metadataTile))
                        actualTileset.get()
                    }
                }
            }
        }
    }
}
