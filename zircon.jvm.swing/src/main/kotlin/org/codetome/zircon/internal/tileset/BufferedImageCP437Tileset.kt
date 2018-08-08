package org.codetome.zircon.internal.tileset

import com.github.benmanes.caffeine.cache.Caffeine
import org.codetome.zircon.api.data.CharacterTile
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.modifier.Border
import org.codetome.zircon.api.modifier.RayShade
import org.codetome.zircon.api.modifier.SimpleModifiers
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.tileset.lookup.CP437TileMetadataLoader
import org.codetome.zircon.api.util.Identifier
import org.codetome.zircon.internal.tileset.impl.DefaultTileTexture
import org.codetome.zircon.internal.tileset.transformer.*
import java.awt.image.BufferedImage
import java.util.concurrent.TimeUnit

class BufferedImageCP437Tileset(private val resource: TilesetResource<CharacterTile>,
                                private val source: BufferedImage)
    : Tileset<CharacterTile, BufferedImage> {

    override val id: Identifier = Identifier.randomIdentifier()

    private val lookup = CP437TileMetadataLoader(
            width = resource.width,
            height = resource.height)

    private val cache = Caffeine.newBuilder()
            .initialCapacity(100)
            .maximumSize(5000)
            .expireAfterAccess(1, TimeUnit.MINUTES)
            .build<String, TileTexture<BufferedImage>>()

    override val sourceType = BufferedImage::class

    override fun width(): Int {
        return resource.width
    }

    override fun height(): Int {
        return resource.height
    }

    override fun supportsTile(tile: Tile): Boolean {
        return if (tile is CharacterTile) {
            lookup.supportsTile(tile)
        } else {
            false
        }
    }

    override fun fetchTextureForTile(tile: Tile): TileTexture<BufferedImage> {
        tile as? CharacterTile ?: throw IllegalArgumentException("Wrong tile type")
        val key = tile.generateCacheKey()
        val meta = lookup.fetchMetaForTile(tile)
        val maybeRegion = cache.getIfPresent(key)
        return if (maybeRegion != null) {
            maybeRegion
        } else {
            var image: TileTexture<BufferedImage> = DefaultTileTexture(
                    width = width(),
                    height = height(),
                    texture = source.getSubimage(meta.x * width(), meta.y * height(), width(), height()))
            TILE_INITIALIZERS.forEach {
                image = it.transform(image, tile)
            }
            tile.getModifiers().forEach {
                image = MODIFIER_TRANSFORMER_LOOKUP[it::class]?.transform(image, tile) ?: image
            }
            cache.put(key, image)
            image
        }
    }

    companion object {

        private val TILE_INITIALIZERS = listOf(
                Java2DTileTextureCloner(),
                Java2DTileTextureColorizer())

        val MODIFIER_TRANSFORMER_LOOKUP = mapOf(
                Pair(SimpleModifiers.Underline::class, Java2DUnderlineTransformer()),
                Pair(SimpleModifiers.VerticalFlip::class, Java2DVerticalFlipper()),
                Pair(SimpleModifiers.HorizontalFlip::class, Java2DHorizontalFlipper()),
                Pair(SimpleModifiers.CrossedOut::class, Java2DCrossedOutTransformer()),
                Pair(SimpleModifiers.Blink::class, NoOpTransformer()),
                Pair(SimpleModifiers.Hidden::class, Java2DHiddenTransformer()),
                Pair(SimpleModifiers.Glow::class, Java2DGlowTransformer()),
                Pair(Border::class, Java2DBorderTransformer()),
                Pair(RayShade::class, Java2DRayShaderTransformer())
        ).toMap()

    }
}
