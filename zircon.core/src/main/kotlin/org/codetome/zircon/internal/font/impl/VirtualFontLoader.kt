package org.codetome.zircon.internal.font.impl

import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.font.CharacterMetadata
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.font.FontTextureRegion
import org.codetome.zircon.internal.font.FontLoader
import org.codetome.zircon.internal.font.FontLoaderRegistry
import org.codetome.zircon.internal.font.MetadataPickingStrategy
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_ARGB
import java.io.InputStream
import java.util.*

class VirtualFontLoader : FontLoader {

    // TODO: this retard trick is temporary, fix it later

    override fun fetchPhysicalFont(size: Float,
                                   source: InputStream,
                                   cacheFonts: Boolean,
                                   withAntiAlias: Boolean): Font {
        return object : Font {

            private var actualFont: Optional<Font> = Optional.empty()

            override fun getWidth() =
                    tryToGetActualFont().getWidth()

            override fun getHeight() =
                    tryToGetActualFont().getHeight()

            override fun hasDataForChar(char: Char) =
                    tryToGetActualFont().hasDataForChar(char)

            override fun fetchRegionForChar(textCharacter: TextCharacter) =
                    tryToGetActualFont().fetchRegionForChar(textCharacter)

            override fun fetchMetadataForChar(char: Char) =
                    tryToGetActualFont().fetchMetadataForChar(char)

            override fun getId() =
                    tryToGetActualFont().getId()

            private fun tryToGetActualFont(): Font {
                return actualFont.orElseGet {
                    if (FontLoaderRegistry.getCurrentFontLoader() === this@VirtualFontLoader) {
                        throw IllegalStateException("No font")
                    } else {
                        actualFont = Optional.of(FontLoaderRegistry.getCurrentFontLoader().fetchPhysicalFont(
                                size = size,
                                source = source,
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
                                source: InputStream,
                                cacheFonts: Boolean,
                                metadata: Map<Char, List<CharacterMetadata>>,
                                metadataPickingStrategy: MetadataPickingStrategy): Font {
        return object : Font {

            private var actualFont: Optional<Font> = Optional.empty()

            override fun getWidth() =
                    tryToGetActualFont().getWidth()

            override fun getHeight() =
                    tryToGetActualFont().getHeight()

            override fun hasDataForChar(char: Char) =
                    tryToGetActualFont().hasDataForChar(char)

            override fun fetchRegionForChar(textCharacter: TextCharacter) =
                    tryToGetActualFont().fetchRegionForChar(textCharacter)

            override fun fetchMetadataForChar(char: Char) =
                    tryToGetActualFont().fetchMetadataForChar(char)

            override fun getId() =
                    tryToGetActualFont().getId()

            private fun tryToGetActualFont(): Font {
                return actualFont.orElseGet {
                    if (FontLoaderRegistry.getCurrentFontLoader() === this@VirtualFontLoader) {
                        throw IllegalStateException("No font")
                    } else {
                        actualFont = Optional.of(FontLoaderRegistry.getCurrentFontLoader().fetchTiledFont(
                                width = width,
                                height = height,
                                source = source,
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
