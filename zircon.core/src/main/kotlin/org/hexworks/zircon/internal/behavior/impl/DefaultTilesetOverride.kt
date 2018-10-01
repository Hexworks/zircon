package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.resource.TilesetResource

class DefaultTilesetOverride(
        private var tileset: TilesetResource) : TilesetOverride {

    override fun currentTileset() = tileset

    override fun useTileset(tileset: TilesetResource) {
        val current = currentTileset()
        require(current.size() == tileset.size()) {
            "Can't override the previous tileset with size: ${current.size()} with a Tileset with" +
                    " different size: ${tileset.size()}"
        }
        require(this.tileset.isCompatibleWith(tileset)) {
            "The supplied tileset (with type ${tileset.tileType.name}) is not compatible with " +
                    "the current one (with type '${currentTileset().tileType.name}')."
        }
        this.tileset = tileset
    }
}
