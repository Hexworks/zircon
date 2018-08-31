package org.hexworks.zircon.internal.tileset.impl

import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.util.Identifier

class VirtualTileset : Tileset<Char> {

    override val targetType = Char::class
    override val id = Identifier.randomIdentifier()

    override fun drawTile(tile: Tile, surface: Char, position: Position) {
        TODO("not implemented")
    }
    
    private fun fetchTextureForTile(tile: Tile): TileTexture<Char> {
        require(tile is CharacterTile) {
            "A VirtualTileset only works with CharacterTiles."
        }
        return DefaultTileTexture(
                width = width(),
                height = height(),
                texture = tile.asCharacterTile().get().character)
    }

    override fun width() = 16

    override fun height() = 16

}
