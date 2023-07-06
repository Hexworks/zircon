package org.hexworks.zircon.renderer.virtual

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.internal.tileset.impl.DefaultTileTexture

class VirtualTileset : Tileset<Char> {

    override val width = 16
    override val height = 16
    override val targetType = Char::class
    override val id = UUID.randomUUID()

    override fun drawTile(tile: Tile, context: Char, position: Position) {
        fetchTextureForTile(tile)
    }

    private fun fetchTextureForTile(tile: Tile): TileTexture<Char> {
        require(tile is CharacterTile) {
            "A VirtualTileset only works with CharacterTiles."
        }
        return DefaultTileTexture(
            width = width,
            height = height,
            texture = tile.asCharacterTileOrNull()?.character!!,
            cacheKey = tile.cacheKey
        )
    }

}
