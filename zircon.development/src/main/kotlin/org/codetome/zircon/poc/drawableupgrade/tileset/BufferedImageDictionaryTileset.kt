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

    override fun width() = 1

    override fun height() = 1

    override fun supportsTile(tile: Tile<out Any>): Boolean {
        return tile.keyType() == String::class.java && images.containsKey(tile.type)
    }

    override fun fetchTextureForTile(tile: Tile<String>): TileTexture<BufferedImage> {
        val maybeRegion = cache.getIfPresent(tile.type)
        val file = images[tile.type]!!
        return if (maybeRegion != null) {
            maybeRegion
        } else {
            val texture = ImageIO.read(file)
            val image = DefaultTileTexture(
                    width = texture.width,
                    height = texture.height,
                    texture = texture)
            cache.put(tile.type, image)
            image
        }
    }

    companion object {

        fun fromResourceDir() = BufferedImageDictionaryTileset(
                File("src/main/resources/images"))
    }
}
