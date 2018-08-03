package org.codetome.zircon.gui.swing.impl

import com.github.benmanes.caffeine.cache.Caffeine
import org.codetome.zircon.api.data.ImageTile
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.util.Identifier
import org.codetome.zircon.internal.tileset.impl.DefaultTileTexture
import java.awt.image.BufferedImage
import java.io.File
import java.util.concurrent.TimeUnit
import javax.imageio.ImageIO

class BufferedImageDictionaryTileset(resource: TilesetResource<ImageTile>) : Tileset<ImageTile, BufferedImage> {

    override val sourceType = BufferedImage::class
    override val id = Identifier.randomIdentifier()

    private val cache = Caffeine.newBuilder()
            .initialCapacity(100)
            .maximumSize(5000)
            .expireAfterAccess(1, TimeUnit.MINUTES)
            .build<String, TileTexture<BufferedImage>>()

    private val images = File(resource.path).listFiles().map {
        it.name to it
    }.toMap()

    override fun width() = 1

    override fun height() = 1

    override fun supportsTile(tile: Tile): Boolean {
        tile as? ImageTile ?: throw IllegalArgumentException()
        return images.containsKey(tile.name)
    }

    override fun fetchTextureForTile(tile: ImageTile): TileTexture<BufferedImage> {
        val maybeRegion = cache.getIfPresent(tile.name)
        val file = images[tile.name]!!
        return if (maybeRegion != null) {
            maybeRegion
        } else {
            val texture = ImageIO.read(file)
            val image = DefaultTileTexture(
                    width = texture.width,
                    height = texture.height,
                    texture = texture)
            cache.put(tile.name, image)
            image
        }
    }

    companion object {

//        fun fromResourceDir() = BufferedImageDictionaryTileset(
//                File("src/main/resources/images"))
    }
}
