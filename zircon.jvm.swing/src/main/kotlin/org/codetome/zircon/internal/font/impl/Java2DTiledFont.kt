package org.codetome.zircon.internal.font.impl

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.font.FontTextureRegion
import org.codetome.zircon.api.font.TextureRegionMetadata
import org.codetome.zircon.api.modifier.Border
import org.codetome.zircon.api.modifier.RayShade
import org.codetome.zircon.api.modifier.SimpleModifiers.*
import org.codetome.zircon.api.util.Cache
import org.codetome.zircon.internal.font.FontRegionTransformer
import org.codetome.zircon.internal.font.MetadataPickingStrategy
import org.codetome.zircon.internal.font.transformer.*
import java.awt.image.BufferedImage

/**
 * Represents a font which is backed by a sprite sheet.
 */
class Java2DTiledFont(private val source: BufferedImage,
                      private val width: Int,
                      private val height: Int,
                      private val regionTransformers: List<FontRegionTransformer<BufferedImage>>,
                      private val cache: Cache<FontTextureRegion<BufferedImage>>,
                      metadata: Map<Char, List<TextureRegionMetadata>>,
                      metadataPickingStrategy: MetadataPickingStrategy = PickFirstMetaStrategy())
    : TiledFontBase(
        metadata = metadata,
        metadataPickingStrategy = metadataPickingStrategy
) {

    override fun getWidth() = width

    override fun getHeight() = height

    override fun fetchRegionForChar(tile: Tile): FontTextureRegion<BufferedImage> {
        val meta = fetchMetaFor(tile)
        val key = tile.generateCacheKey()
        val maybeRegion = cache.retrieveIfPresent(key)

        var region = if (maybeRegion.isNotPresent()) {
            var image: FontTextureRegion<BufferedImage> = Java2DFontTextureRegion(
                    cacheKey = key,
                    backend = source.getSubimage(meta.x * width, meta.y * height, width, height))
            regionTransformers.forEach {
                image = it.transform(image, tile)
            }
            cache.store(key, image)

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
                Pair(Underline::class, Java2DUnderlineTransformer()),
                Pair(VerticalFlip::class, Java2DVerticalFlipper()),
                Pair(HorizontalFlip::class, Java2DHorizontalFlipper()),
                Pair(CrossedOut::class, Java2DCrossedOutTransformer()),
                Pair(Border::class, Java2DBorderTransformer()),
                Pair(Blink::class, NoOpTransformer()),
                Pair(Hidden::class, Java2DHiddenTransformer()),
                Pair(RayShade::class, Java2DRayShaderTransformer()),
                Pair(Glow::class, Java2DGlowTransformer())
        ).toMap()
    }
}
