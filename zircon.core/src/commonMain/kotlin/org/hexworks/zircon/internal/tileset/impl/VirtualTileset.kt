package org.hexworks.zircon.internal.tileset.impl

import org.hexworks.cobalt.factory.IdentifierFactory
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.Tileset

class VirtualTileset : Tileset<Char> {

    override val width = 16
    override val height = 16
    override val targetType = Char::class
    override val id = IdentifierFactory.randomIdentifier()

    override fun drawTile(tile: Tile, surface: Char, position: Position) {
        fetchTextureForTile(tile)
    }

    private fun fetchTextureForTile(tile: Tile): TileTexture<Char> {
        require(tile is CharacterTile) {
            "A VirtualTileset only works with CharacterTile."
        }
        return DefaultTileTexture(
                width = width,
                height = height,
                texture = tile.asCharacterTile().get().character)
    }

}
