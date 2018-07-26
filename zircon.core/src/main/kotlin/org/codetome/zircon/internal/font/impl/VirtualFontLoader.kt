package org.codetome.zircon.internal.font.impl

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.font.TextureRegionMetadata
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.font.FontLoader
import org.codetome.zircon.api.util.Maybe
import org.codetome.zircon.internal.font.MetadataPickingStrategy

class VirtualFontLoader : FontLoader {

    // TODO: this retard trick is temporary, fix it later

    override fun fetchPhysicalFont(size: Float,
                                   path: String,
                                   cacheFonts: Boolean,
                                   withAntiAlias: Boolean): Font {
        return object : Font {

            private var actualFont: Maybe<Font> = Maybe.empty()

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

            private fun tryToGetActualFont(): Font {
                return actualFont.orElseGet {
                    if (FontLoaderRegistry.getCurrentFontLoader() === this@VirtualFontLoader) {
                        throw IllegalStateException("No font")
                    } else {
                        actualFont = Maybe.of(FontLoaderRegistry.getCurrentFontLoader().fetchPhysicalFont(
                                size = size,
                                path = path,
                                cacheFonts = cacheFonts,
                                withAntiAlias = withAntiAlias))
                        actualFont.get()
                    }
                }
            }
        }
    }

    override fun fetchTiledFont(width: Int,
                                height: Int,
                                path: String,
                                cacheFonts: Boolean,
                                metadata: Map<Char, List<TextureRegionMetadata>>,
                                metadataPickingStrategy: MetadataPickingStrategy): Font {
        return object : Font {

            private var actualFont: Maybe<Font> = Maybe.empty()

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

            private fun tryToGetActualFont(): Font {
                return actualFont.orElseGet {
                    if (FontLoaderRegistry.getCurrentFontLoader() === this@VirtualFontLoader) {
                        throw IllegalStateException("No font")
                    } else {
                        actualFont = Maybe.of(FontLoaderRegistry.getCurrentFontLoader().fetchTiledFont(
                                width = width,
                                height = height,
                                path = path,
                                cacheFonts = cacheFonts,
                                metadataPickingStrategy = metadataPickingStrategy,
                                metadata = metadata))
                        actualFont.get()
                    }
                }
            }
        }
    }
}
