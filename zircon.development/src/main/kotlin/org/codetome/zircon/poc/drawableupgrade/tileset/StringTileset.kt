package org.codetome.zircon.poc.drawableupgrade.tileset

import org.codetome.zircon.poc.drawableupgrade.Tile

object StringTileset : Tileset<String> {

    private val supportedTiles = mapOf(
            'a' to "foo",
            'b' to "bar",
            'c' to "baz",
            '_' to " _ ",
            'x' to " x ")

    override fun getWidth(): Int {
        return 3
    }

    override fun getHeight(): Int {
        return 1
    }

    override fun supportsTile(tile: Tile): Boolean {
        return supportedTiles.containsKey(tile.char)
    }

    override fun fetchTextureForTile(tile: Tile): TileTexture<String> {
        return DefaultTileTexture(supportedTiles[tile.char]!!)
    }
}
