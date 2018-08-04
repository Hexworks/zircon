package org.codetome.zircon.gui.swing.impl

import org.codetome.zircon.api.behavior.Closeable
import org.codetome.zircon.api.data.CharacterTile
import org.codetome.zircon.api.data.ImageTile
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.tileset.TilesetLoader
import org.codetome.zircon.api.util.Identifier
import org.codetome.zircon.gui.swing.tileset.BufferedImageCP437Tileset
import org.codetome.zircon.gui.swing.tileset.BufferedImageDictionaryTileset
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class SwingTilesetLoader : TilesetLoader<BufferedImage>, Closeable {

    private val tilesetCache = mutableMapOf<Identifier, Tileset<out Tile, BufferedImage>>()

    override fun loadTilesetFrom(resource: TilesetResource<out Tile>): Tileset<out Tile, BufferedImage> {
        return tilesetCache.getOrPut(resource.id) {
            LOADERS[resource.tileType]?.invoke(resource)
                    ?: throw IllegalArgumentException("Unknown tile type '${resource.tileType}'.§")
        }
    }

    override fun close() {
        tilesetCache.clear()
    }

    companion object {

        private val LOADERS: Map<KClass<out Tile>, (TilesetResource<out Tile>) -> Tileset<out Tile, BufferedImage>> = mapOf(
                CharacterTile::class to { resource: TilesetResource<out Tile> ->
                    BufferedImageCP437Tileset(
                            resource = resource as TilesetResource<CharacterTile>,
                            source = ImageIO.read(File(resource.path)))
                },
                ImageTile::class to { resource: TilesetResource<out Tile> ->
                    BufferedImageDictionaryTileset(
                            resource = resource as TilesetResource<ImageTile>)
                })
    }
}
