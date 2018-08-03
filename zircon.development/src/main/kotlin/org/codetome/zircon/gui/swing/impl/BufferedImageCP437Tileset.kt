package org.codetome.zircon.gui.swing.impl

import com.github.benmanes.caffeine.cache.Caffeine
import org.codetome.zircon.api.data.CharacterTile
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.tileset.lookup.CP437TileMetadataLoader
import org.codetome.zircon.api.util.Identifier
import org.codetome.zircon.internal.tileset.impl.DefaultTileTexture
import java.awt.image.BufferedImage
import java.io.File
import java.util.concurrent.TimeUnit
import javax.imageio.ImageIO
import kotlin.reflect.KClass

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
            .build<Char, TileTexture<BufferedImage>>()

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

    override fun fetchTextureForTile(tile: CharacterTile): TileTexture<BufferedImage> {
        tile as? CharacterTile ?: throw IllegalArgumentException()
        val maybeRegion = cache.getIfPresent(tile.character)
        val meta = lookup.fetchMetaForTile(tile)
        return if (maybeRegion != null) {
            maybeRegion
        } else {
            var image: TileTexture<BufferedImage> = DefaultTileTexture(
                    width = width(),
                    height = height(),
                    texture = source.getSubimage(meta.x * width(), meta.y * height(), width(), height()))
            TILE_TRANSFORMERS.forEach {
                image = it.transform(image, tile)
            }
            cache.put(tile.character, image)
            image
        }
    }

    companion object {

        private val TILE_TRANSFORMERS = listOf(
                BufferedImageTileTextureCloner())

    }
}
