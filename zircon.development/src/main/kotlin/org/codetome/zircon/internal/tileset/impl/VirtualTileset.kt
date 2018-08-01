package org.codetome.zircon.internal.tileset.impl

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.api.tileset.Tileset

class VirtualTileset : Tileset<Char, Char> {

    override fun width() = 16

    override fun height() = 16

    override fun supportsTile(tile: Tile<out Any>) = true

    override fun fetchTextureForTile(tile: Tile<Char>): TileTexture<Char> {
        return DefaultTileTexture(
                width = width(),
                height = height(),
                texture = tile.key)
    }
}
