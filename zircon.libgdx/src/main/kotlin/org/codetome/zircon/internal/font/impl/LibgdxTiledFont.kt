package org.codetome.zircon.internal.font.impl

import com.badlogic.gdx.graphics.Pixmap
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
import java.io.InputStream
import java.util.*

/**
 * Represents a font which is backed by a sprite sheet.
 */
class LibgdxTiledFont(private val source: InputStream,
                      private val width: Int,
                      private val height: Int,
                      private val regionTransformers: List<FontRegionTransformer<TextureRegion>>,
                      private val cache: FontRegionCache<FontTextureRegion<TextureRegion>>,
                      metadata: Map<Char, List<CharacterMetadata>>,
                      metadataPickingStrategy: MetadataPickingStrategy = PickFirstMetaStrategy())
    : AbstractTiledFont(
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

    override fun fetchRegionForChar(textCharacter: TextCharacter): FontTextureRegion<TextureRegion> {
        val meta = fetchMetaFor(textCharacter)
//        val maybeRegion = cache.retrieveIfPresent(textCharacter)
//
//        var region = if (maybeRegion.isNotPresent()) {
//            var image: FontTextureRegion<TextureRegion> = LibgdxFontTextureRegion(
//                    TextureRegion(texture, meta.x * width, meta.y * height, width, height))
//            regionTransformers.forEach {
//                image = it.transform(image, textCharacter)
//            }
//            cache.store(textCharacter, image)
//            image
//        } else {
//            maybeRegion.get()
//        }
//        textCharacter.getModifiers().forEach {
//            region = MODIFIER_TRANSFORMER_LOOKUP[it::class]?.transform(region, textCharacter) ?: region
//        }
        return LibgdxFontTextureRegion(TextureRegion(texture, meta.x * width, meta.y * height, width, height))
    }

    companion object {
        val MODIFIER_TRANSFORMER_LOOKUP = mapOf(
                Pair(Blink::class, NoOpTransformer())
        ).toMap()
    }
}
