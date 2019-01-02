package org.hexworks.zircon.internal.tileset

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.hexworks.cobalt.datatypes.Identifier
import org.hexworks.zircon.api.behavior.Closeable
import org.hexworks.zircon.api.resource.TileType.*
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.resource.TilesetType.*
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
        private val fileHandlePrefix = "zircon.jvm.libgdx/src/main/resources"
        fun TilesetResource.getLoaderKey() = "${this.tilesetType.name}-${this.tileType.name}"

        private val LOADERS: Map<String, (TilesetResource) -> Tileset<SpriteBatch>> = mapOf(
                "$CP437_TILESET-$CHARACTER_TILE" to { resource: TilesetResource ->
                    LibgdxTileset(
                            path = resource.path,
                            width = resource.width,
                            height = resource.height
                    )
                }
                //TODO Support for other types of tilesets
        )
    }
}
