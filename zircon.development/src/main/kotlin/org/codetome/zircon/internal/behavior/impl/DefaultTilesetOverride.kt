package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.api.behavior.TilesetOverride
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.internal.tileset.impl.FontSettings

class DefaultTilesetOverride(
        private var tileset: TilesetResource<out Tile>) : TilesetOverride {

    override fun tileset() = tileset

    override fun useTileset(tileset: TilesetResource<out Tile>) {
        val current = tileset()
        if (current !== FontSettings.NO_FONT) {
            require(current.size() == tileset.size()) {
                "Can't override previous tileset with size: ${current.size()} with a Tileset with" +
                        " different size: ${tileset.size()}"
            }
        }
        require(this.tileset.isCompatibleWith(tileset)) {
            "The supplied tileset is not compatible with the current one."
        }
        this.tileset = tileset
    }
}
