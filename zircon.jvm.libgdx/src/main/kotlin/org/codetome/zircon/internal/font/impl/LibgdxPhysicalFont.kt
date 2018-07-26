package org.codetome.zircon.internal.font.impl

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.font.TextureRegionMetadata
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.font.FontTextureRegion
import org.codetome.zircon.api.interop.Modifiers
import org.codetome.zircon.api.util.JVMFontUtils
import org.codetome.zircon.internal.font.FontRegionCache
import org.codetome.zircon.internal.font.transformer.NoOpTransformer
import org.codetome.zircon.api.util.Identifier
import org.codetome.zircon.api.util.Maybe

/**
 * Represents a physical font which is backed by [java.awt.Font].
 */
class LibgdxPhysicalFont(private val source: java.awt.Font,
                         private val width: Int,
                         private val height: Int,
                         private val cache: FontRegionCache<FontTextureRegion<TextureRegion>>,
                         private val withAntiAlias: Boolean) : Font {

    private val id = Identifier.randomIdentifier()

    init {
        require(JVMFontUtils.isFontMonospaced(source)) {
            "Font '${source.name} is not monospaced!"
        }
    }

    override fun getId() = id

    override fun getWidth() = width

    override fun getHeight() = height

    override fun hasDataForChar(char: Char) = source.canDisplay(char)

    override fun fetchRegionForChar(tile: Tile): FontTextureRegion<*> {

        val maybeRegion: Maybe<FontTextureRegion<TextureRegion>> = cache.retrieveIfPresent(tile)

        var region: FontTextureRegion<TextureRegion> = if (maybeRegion.isNotPresent()) {
            TODO()
        } else {
            maybeRegion.get()
        }
        tile.getModifiers().forEach {
            region = MODIFIER_TRANSFORMER_LOOKUP[it]?.transform(region, tile) ?: region
        }
        return region
    }

    override fun fetchMetadataForChar(char: Char) = listOf<TextureRegionMetadata>()

    companion object {
        val MODIFIER_TRANSFORMER_LOOKUP = mapOf(
                Pair(Modifiers.blink(), NoOpTransformer())
        ).toMap()
    }
}
