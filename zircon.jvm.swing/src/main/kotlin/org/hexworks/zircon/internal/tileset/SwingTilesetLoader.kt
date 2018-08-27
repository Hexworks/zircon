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
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class SwingTilesetLoader : TilesetLoader<BufferedImage, Graphics2D>, Closeable {

    private val tilesetCache = mutableMapOf<Identifier, Tileset<BufferedImage, Graphics2D>>()

    override fun loadTilesetFrom(resource: TilesetResource): Tileset<BufferedImage, Graphics2D> {
        return tilesetCache.getOrPut(resource.id) {
            LOADERS[resource.tileType]?.invoke(resource)
                    ?: throw IllegalArgumentException("Unknown tile type '${resource.tileType}'.§")
        }
    }

    override fun close() {
        tilesetCache.clear()
    }

    companion object {

        private val LOADERS: Map<KClass<out Tile>, (TilesetResource) -> Tileset<BufferedImage, Graphics2D>> = mapOf(
                CharacterTile::class to { resource: TilesetResource ->
                    val source = ImageLoader.readImage(resource)
                    BufferedImageCP437Tileset(
                            resource = resource,
                            source = source)
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
