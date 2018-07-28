package org.codetome.zircon.poc.drawableupgrade.tileset

import com.github.benmanes.caffeine.cache.Caffeine
import org.codetome.zircon.poc.drawableupgrade.texture.DefaultTileTexture
import org.codetome.zircon.poc.drawableupgrade.texture.TileTexture
import org.codetome.zircon.poc.drawableupgrade.tile.Tile
import java.awt.image.BufferedImage
import java.io.File
import java.util.concurrent.TimeUnit
import javax.imageio.ImageIO

class BufferedImageDictionaryTileset(dir: File) : Tileset<String, BufferedImage> {

    private val cache = Caffeine.newBuilder()
            .initialCapacity(100)
            .maximumSize(5000)
            .expireAfterAccess(1, TimeUnit.MINUTES)
            .build<String, TileTexture<BufferedImage>>()

    private val images = dir.listFiles().map {
        it.name to it
    }.toMap()

    override fun getWidth() = 1

    override fun getHeight() = 1

    override fun supportsTile(tile: Tile<out Any>): Boolean {
        return tile.keyType() == String::class.java && images.containsKey(tile.key)
    }

    override fun fetchTextureForTile(tile: Tile<String>): TileTexture<BufferedImage> {
        val maybeRegion = cache.getIfPresent(tile.key)
        val file = images[tile.key]!!
        return if (maybeRegion != null) {
            maybeRegion
        } else {
            val texture = ImageIO.read(file)
            val image = DefaultTileTexture(
                    width = texture.width,
                    height = texture.height,
                    texture = texture)
            cache.put(tile.key, image)
            image
        }
    }

    companion object {

        fun fromResourceDir() = BufferedImageDictionaryTileset(
                File("zircon.development/src/main/resources/images"))
    }
}
