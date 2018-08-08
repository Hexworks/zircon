package org.codetome.zircon.internal.tileset.impl

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.util.Identifier

class VirtualTileset : Tileset<Tile, Char> {

    override val sourceType = Char::class
    override val id = Identifier.randomIdentifier()

    override fun supportsTile(tile: Tile) = true

    override fun fetchTextureForTile(tile: Tile): TileTexture<Char> {
        return DefaultTileTexture(
                width = width(),
                height = height(),
                texture = ' ')
    }


    override fun width() = 16

    override fun height() = 16

}
