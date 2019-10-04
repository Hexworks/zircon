/**
 * WIP
 */
/*package org.hexworks.zircon.internal.tileset

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.github.benmanes.caffeine.cache.Caffeine
import org.hexworks.cobalt.factory.IdentifierFactory
import org.hexworks.zircon.api.data.ImageTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.resource.TileType.IMAGE_TILE
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.internal.tileset.impl.DefaultTileTexture
import java.util.concurrent.TimeUnit

class LibgdxImageDictionaryTileset(resource: TilesetResource)
    : Tileset<SpriteBatch> {

    override val id = IdentifierFactory.randomIdentifier()
    override val targetType = SpriteBatch::class
    override val width = 1
    override val height = 1

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

    override fun drawTile(tile: Tile, surface: Graphics2D, position: Position) {
        val texture = fetchTextureForTile(tile)
        val x = position.x * width
        val y = position.y * height
        surface.drawImage(texture.texture, x, y, null)
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
}*/
