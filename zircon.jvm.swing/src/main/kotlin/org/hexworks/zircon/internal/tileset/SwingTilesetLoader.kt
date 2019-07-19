package org.hexworks.zircon.internal.tileset

import org.hexworks.cobalt.Identifier
import org.hexworks.zircon.api.behavior.Closeable
import org.hexworks.zircon.internal.resource.TileType.*
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.resource.TilesetType.*
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.tileset.TilesetLoader
import java.awt.Graphics2D

@Suppress("UNCHECKED_CAST")
class SwingTilesetLoader : TilesetLoader<Graphics2D>, Closeable {

    private val tilesetCache = mutableMapOf<Identifier, Tileset<Graphics2D>>()

    override fun loadTilesetFrom(resource: TilesetResource): Tileset<Graphics2D> {
        return tilesetCache.getOrPut(resource.id) {
            LOADERS[resource.getLoaderKey()]?.invoke(resource)
                    ?: throw IllegalArgumentException("Unknown tile type '${resource.tileType}'.§")
        }
    }

    override fun close() {
        tilesetCache.clear()
    }

    companion object {

        fun TilesetResource.getLoaderKey() = "${this.tileType.name}-${this.tilesetType.name}"

        private val LOADERS: Map<String, (TilesetResource) -> Tileset<Graphics2D>> = mapOf(
                "$CHARACTER_TILE-$CP437_TILESET" to { resource: TilesetResource ->
                    val source = ImageLoader.readImage(resource)
                    Java2DCP437Tileset(
                            resource = resource,
                            source = source)
                },
                "$GRAPHIC_TILE-$GRAPHIC_TILESET" to { resource: TilesetResource ->
                    Java2DGraphicTileset(
                            resource = resource)
                },
                "$CHARACTER_TILE-$TRUE_TYPE_FONT" to { resource: TilesetResource ->
                    MonospaceAwtFontTileset(
                            resource = resource)
                },
                "$IMAGE_TILE-$GRAPHIC_TILESET" to { resource: TilesetResource ->
                    Java2DImageDictionaryTileset(
                            resource = resource)
                })
    }
}
