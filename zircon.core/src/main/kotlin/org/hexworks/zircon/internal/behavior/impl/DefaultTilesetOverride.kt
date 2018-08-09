package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource

class DefaultTilesetOverride(
        private var tileset: TilesetResource) : TilesetOverride {

    override fun tileset() = tileset

    override fun useTileset(tileset: TilesetResource) {
        val current = tileset()
        require(current.size() == tileset.size()) {
            "Can't override the previous tileset with size: ${current.size()} with a Tileset with" +
                    " different size: ${tileset.size()}"
        }
        require(this.tileset.isCompatibleWith(tileset)) {
            "The supplied tileset (with type ${tileset.tileType.simpleName}) is not compatible with " +
                    "the current one (with type '${tileset().tileType.simpleName}')."
        }
        this.tileset = tileset
    }
}
