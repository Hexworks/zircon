package org.hexworks.zircon.internal.tileset

import org.hexworks.zircon.api.behavior.Closeable
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.GraphicTile
import org.hexworks.zircon.api.data.ImageTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.api.util.Identifier
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class SwingTilesetLoader : TilesetLoader<BufferedImage>, Closeable {

    private val tilesetCache = mutableMapOf<Identifier, Tileset<BufferedImage>>()

    override fun loadTilesetFrom(resource: TilesetResource): Tileset<BufferedImage> {
        return tilesetCache.getOrPut(resource.id) {
            LOADERS[resource.tileType]?.invoke(resource)
                    ?: throw IllegalArgumentException("Unknown tile type '${resource.tileType}'.§")
        }
    }

    override fun close() {
        tilesetCache.clear()
    }

    companion object {

        private val LOADERS: Map<KClass<out Tile>, (TilesetResource) -> Tileset<BufferedImage>> = mapOf(
                CharacterTile::class to { resource: TilesetResource ->
                    BufferedImageCP437Tileset(
                            resource = resource,
                            source = ImageIO.read(File(resource.path)))
                },
                ImageTile::class to { resource: TilesetResource ->
                    BufferedImageDictionaryTileset(
                            resource = resource)
                },
                GraphicTile::class to { resource: TilesetResource ->
                    BufferedImageGraphicTileset(
                            resource = resource)
                })
    }
}
