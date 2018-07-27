package org.codetome.zircon.poc.drawableupgrade.tileset

import org.codetome.zircon.poc.drawableupgrade.Tile

object CharTileset : Tileset<Char> {

    private val supportedTiles = mapOf(
            'a' to 'a',
            'b' to 'b',
            'c' to 'c',
            '_' to '_',
            'x' to 'x')

    override fun getWidth(): Int {
        return 1
    }

    override fun getHeight(): Int {
        return 1
    }

    override fun supportsTile(tile: Tile): Boolean {
        return supportedTiles.containsKey(tile.char)
    }

    override fun fetchTextureForTile(tile: Tile): TileTexture<Char> {
        return DefaultTileTexture(tile.char)
    }
}
