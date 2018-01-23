package org.codetome.zircon.internal.font.impl

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.font.CharacterMetadata
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.font.FontTextureRegion
import org.codetome.zircon.api.util.TextUtils
import org.codetome.zircon.internal.SimpleModifiers.Blink
import org.codetome.zircon.internal.extensions.isNotPresent
import org.codetome.zircon.internal.font.FontRegionCache
import org.codetome.zircon.internal.font.FontRegionTransformer
import org.codetome.zircon.internal.font.MetadataPickingStrategy
import org.codetome.zircon.internal.font.transformer.NoOpTransformer
import java.util.*

/**
 * Represents a font which is backed by a sprite sheet.
 */
class LibgdxTiledFont(private val source: Texture,
                      private val width: Int,
                      private val height: Int,
                      private val regionTransformers: List<FontRegionTransformer<TextureRegion>>,
                      private val cache: FontRegionCache<FontTextureRegion<TextureRegion>>,
                      metadata: Map<Char, List<CharacterMetadata>>,
                      metadataPickingStrategy: MetadataPickingStrategy = PickFirstMetaStrategy())
    : AbstractTiledFont(
        metadata = metadata,
        metadataPickingStrategy = metadataPickingStrategy) {

    init {
        if (!source.textureData.isPrepared) {
            source.textureData.prepare()
        }
    }

    override fun getWidth() = width

    override fun getHeight() = height

    override fun fetchRegionForChar(textCharacter: TextCharacter): FontTextureRegion<TextureRegion> {
        val meta = fetchMetaFor(textCharacter)
        val maybeRegion = cache.retrieveIfPresent(textCharacter)

        var region = if (maybeRegion.isNotPresent()) {
            var image: FontTextureRegion<TextureRegion> = LibgdxFontTextureRegion(TextureRegion(source, meta.x * width, meta.y * height, width, height))
            regionTransformers.forEach {
                image = it.transform(image, textCharacter)
            }
            cache.store(textCharacter, image)
            image
        } else {
            maybeRegion.get()
        }
        textCharacter.getModifiers().forEach {
            region = MODIFIER_TRANSFORMER_LOOKUP[it::class]?.transform(region, textCharacter) ?: region
        }
        return region
    }

    companion object {
        val MODIFIER_TRANSFORMER_LOOKUP = mapOf(
                Pair(Blink::class, NoOpTransformer())
        ).toMap()
    }
}
