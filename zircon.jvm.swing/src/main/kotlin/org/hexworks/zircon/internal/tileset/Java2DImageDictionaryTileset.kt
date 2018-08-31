package org.hexworks.zircon.internal.tileset

import com.github.benmanes.caffeine.cache.Caffeine
import org.hexworks.zircon.api.data.ImageTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TileType.IMAGE_TILE
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.util.Identifier
import org.hexworks.zircon.internal.tileset.impl.DefaultTileTexture
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.File
import java.util.concurrent.TimeUnit
import javax.imageio.ImageIO

class Java2DImageDictionaryTileset(resource: TilesetResource)
    : Tileset<Graphics2D> {

    override val id = Identifier.randomIdentifier()
    override val targetType = Graphics2D::class

    private val cache = Caffeine.newBuilder()
            .initialCapacity(100)
            .maximumSize(5000)
            .expireAfterAccess(1, TimeUnit.MINUTES)
            .build<String, TileTexture<BufferedImage>>()

    private val images = File(resource.path).listFiles().map {
        it.name to it
    }.toMap()

    init {
        require(resource.tileType == IMAGE_TILE) {
            "Can't use a ${resource.tileType.name}-based TilesetResource for" +
                    " an ImageTile-based tileset."
        }
    }

    override fun width() = 1

    override fun height() = 1

    override fun drawTile(tile: Tile, surface: Graphics2D, position: Position) {
        val texture = fetchTextureForTile(tile)
        val x = position.x * width()
        val y = position.y * height()
        surface.drawImage(texture.getTexture(), x, y, null)
    }

    private fun fetchTextureForTile(tile: Tile): TileTexture<BufferedImage> {
        tile as? ImageTile ?: throw IllegalArgumentException("Wrong tile type")
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

//        fun fromResourceDir() = Java2DImageDictionaryTileset(
//                File("src/main/resources/images"))
    }
}
