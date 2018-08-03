package org.codetome.zircon.api.tileset

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.resource.TilesetResource

interface TilesetLoader<S: Any> {

    fun loadTilesetFrom(resource: TilesetResource<out Tile>) : Tileset<out Tile, S>

}
