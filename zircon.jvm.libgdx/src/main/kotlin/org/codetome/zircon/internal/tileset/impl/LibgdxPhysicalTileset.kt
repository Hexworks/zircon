package org.codetome.zircon.internal.tileset.impl

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.TileTextureMetadata
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.api.interop.Modifiers
import org.codetome.zircon.api.util.JVMFontUtils
import org.codetome.zircon.internal.tileset.FontRegionCache
import org.codetome.zircon.internal.tileset.transformer.NoOpTransformer
import org.codetome.zircon.api.util.Identifier
import org.codetome.zircon.api.util.Maybe

/**
 * Represents a physical tileset which is backed by [java.awt.Font].
 */
class LibgdxPhysicalTileset(private val source: java.awt.Font,
                            private val width: Int,
                            private val height: Int,
                            private val cache: FontRegionCache<TileTexture<TextureRegion>>,
                            private val withAntiAlias: Boolean) : Tileset {

    private val id = Identifier.randomIdentifier()

    init {
        require(JVMFontUtils.isFontMonospaced(source)) {
            "Tileset '${source.name} is not monospaced!"
        }
    }

    override fun getId() = id

    override fun getWidth() = width

    override fun getHeight() = height

    override fun hasDataForChar(char: Char) = source.canDisplay(char)

    override fun fetchRegionForChar(tile: Tile): TileTexture<*> {

        val maybe: Maybe<TileTexture<TextureRegion>> = cache.retrieveIfPresent(tile)

        var region: TileTexture<TextureRegion> = if (maybe.isNotPresent()) {
            TODO()
        } else {
            maybe.get()
        }
        tile.getModifiers().forEach {
            region = MODIFIER_TRANSFORMER_LOOKUP[it]?.transform(region, tile) ?: region
        }
        return region
    }

    override fun fetchMetadataForChar(char: Char) = listOf<TileTextureMetadata>()

    companion object {
        val MODIFIER_TRANSFORMER_LOOKUP = mapOf(
                Pair(Modifiers.blink(), NoOpTransformer())
        ).toMap()
    }
}
