package org.codetome.zircon.internal.tileset.impl

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.api.tileset.TileTextureMetadata
import org.codetome.zircon.api.modifier.Border
import org.codetome.zircon.api.modifier.RayShade
import org.codetome.zircon.api.modifier.SimpleModifiers.*
import org.codetome.zircon.api.util.Cache
import org.codetome.zircon.internal.tileset.TileTextureTransformer
import org.codetome.zircon.internal.tileset.MetadataPickingStrategy
import org.codetome.zircon.internal.tileset.transformer.*
import java.awt.image.BufferedImage

/**
 * Represents a tileset which is backed by a sprite sheet.
 */
class Java2DTiledTileset(private val source: BufferedImage,
                         private val width: Int,
                         private val height: Int,
                         private val regionTransformers: List<TileTextureTransformer<BufferedImage>>,
                         private val cache: Cache<TileTexture<BufferedImage>>,
                         metadataTile: Map<Char, List<TileTextureMetadata>>,
                         metadataPickingStrategy: MetadataPickingStrategy = PickFirstMetaStrategy())
    : TiledTilesetBase(
        metadataTile = metadataTile,
        metadataPickingStrategy = metadataPickingStrategy
) {

    override fun getWidth() = width

    override fun getHeight() = height

    override fun fetchRegionForChar(tile: Tile): TileTexture<BufferedImage> {
        val meta = fetchMetaFor(tile)
        val key = tile.generateCacheKey()
        val maybeRegion = cache.retrieveIfPresent(key)

        var region = if (maybeRegion.isNotPresent()) {
            var image: TileTexture<BufferedImage> = Java2DTileTexture(
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
