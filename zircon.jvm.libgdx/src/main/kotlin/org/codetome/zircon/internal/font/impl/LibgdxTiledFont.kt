package org.codetome.zircon.internal.font.impl

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.font.TextureRegionMetadata
import org.codetome.zircon.api.font.FontTextureRegion
import org.codetome.zircon.api.modifier.SimpleModifiers.Blink
import org.codetome.zircon.internal.font.FontRegionTransformer
import org.codetome.zircon.internal.font.MetadataPickingStrategy
import org.codetome.zircon.internal.font.transformer.NoOpTransformer
import org.codetome.zircon.api.util.Cache
import java.io.InputStream

/**
 * Represents a font which is backed by a sprite sheet.
 */
class LibgdxTiledFont(private val source: InputStream,
                      private val width: Int,
                      private val height: Int,
                      private val regionTransformers: List<FontRegionTransformer<TextureRegion>>,
                      private val cache: Cache<FontTextureRegion<TextureRegion>>,
                      metadata: Map<Char, List<TextureRegionMetadata>>,
                      metadataPickingStrategy: MetadataPickingStrategy = PickFirstMetaStrategy())
    : TiledFontBase(
        metadata = metadata,
        metadataPickingStrategy = metadataPickingStrategy) {

    private val texture: Texture by lazy {
        val bytes = source.readBytes()
        val tex = Texture(Pixmap(bytes, 0, bytes.size))
        if (!tex.textureData.isPrepared) {
            tex.textureData.prepare()
        }
        tex
    }

    override fun getWidth() = width

    override fun getHeight() = height

    override fun fetchRegionForChar(tile: Tile): FontTextureRegion<TextureRegion> {
        val meta = fetchMetaFor(tile)
        val maybeRegion = cache.retrieveIfPresent(tile.generateCacheKey())

        var region = if (maybeRegion.isNotPresent()) {
            var image: FontTextureRegion<TextureRegion> = LibgdxFontTextureRegion(
                    tile.generateCacheKey(),
                    TextureRegion(texture, meta.x * width, meta.y * height, width, height))
            regionTransformers.forEach {
                image = it.transform(image, tile)
            }
            cache.store(image)
            image
        } else {
            maybeRegion.get()
        }
        tile.getModifiers().forEach {
            region = MODIFIER_TRANSFORMER_LOOKUP[it::class]?.transform(region, tile) ?: region
        }
        return region
    }

    companion object {
        val MODIFIER_TRANSFORMER_LOOKUP = mapOf(
                Pair(Blink::class, NoOpTransformer())
        ).toMap()
    }
}
