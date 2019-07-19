package org.hexworks.zircon.internal.tileset

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.hexworks.cobalt.Identifier
import org.hexworks.zircon.api.behavior.Closeable
import org.hexworks.zircon.internal.resource.TileType.*
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.resource.TilesetType.*
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.tileset.TilesetLoader

@Suppress("UNCHECKED_CAST")
class LibgdxTilesetLoader : TilesetLoader<SpriteBatch>, Closeable {

    private val tilesetCache = mutableMapOf<Identifier, Tileset<SpriteBatch>>()


    override fun loadTilesetFrom(resource: TilesetResource): Tileset<SpriteBatch> {
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

        private val LOADERS: Map<String, (TilesetResource) -> Tileset<SpriteBatch>> = mapOf(
                "$CHARACTER_TILE-$CP437_TILESET" to { resource: TilesetResource ->
                    LibgdxCP437Tileset(
                            path = resource.path,
                            width = resource.width,
                            height = resource.height)
                },
                "$GRAPHIC_TILE-$GRAPHIC_TILESET" to { resource: TilesetResource ->
                    LibgdxGraphicTileset(
                            resource = resource)
                },
                "$CHARACTER_TILE-$TRUE_TYPE_FONT" to { resource: TilesetResource ->
                    LibgdxMonospaceFontTileset(
                            resource = resource)
                }
                //TODO Support for other types of tilesets
        )
    }
}
