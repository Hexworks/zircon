package org.codetome.zircon.internal.tileset.impl

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.api.tileset.TileTextureMetadata
import org.codetome.zircon.api.modifier.SimpleModifiers.Blink
import org.codetome.zircon.api.util.Cache
import org.codetome.zircon.api.tileset.TileTextureTransformer
import org.codetome.zircon.api.tileset.MetadataPickingStrategy
import org.codetome.zircon.internal.tileset.transformer.NoOpTransformer
import java.io.InputStream

/**
 * Represents a tileset which is backed by a sprite sheet.
 */
class LibgdxTiledTileset(private val source: InputStream,
                         private val width: Int,
                         private val height: Int,
                         private val regionTransformers: List<TileTextureTransformer<TextureRegion>>,
                         private val cache: Cache<TileTexture<TextureRegion>>,
                         metadataTile: Map<Char, List<TileTextureMetadata>>,
                         metadataPickingStrategy: MetadataPickingStrategy = PickFirstMetaStrategy())
    : TiledTilesetBase(
        metadataTile = metadataTile,
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

    override fun fetchRegionForChar(tile: Tile): TileTexture<TextureRegion> {
        val meta = fetchMetaFor(tile)
        val cacheKey = tile.generateCacheKey()
        val maybeRegion = cache.retrieveIfPresent(cacheKey)

        var region = if (maybeRegion.isNotPresent()) {
            var image: TileTexture<TextureRegion> = LibgdxTileTexture(
                    tile.generateCacheKey(),
                    TextureRegion(texture, meta.x * width, meta.y * height, width, height))
            regionTransformers.forEach {
                image = it.transform(image, tile)
            }
            cache.store(cacheKey, image)
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
