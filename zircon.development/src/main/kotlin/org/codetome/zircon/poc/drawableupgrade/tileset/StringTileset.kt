package org.codetome.zircon.poc.drawableupgrade.tileset

import org.codetome.zircon.poc.drawableupgrade.texture.DefaultTileTexture
import org.codetome.zircon.poc.drawableupgrade.texture.TileTexture
import org.codetome.zircon.poc.drawableupgrade.tile.Tile

object StringTileset : Tileset<Char, String> {

    private val supportedTiles = mapOf(
            'a' to "foo",
            'b' to "bar",
            'c' to "baz",
            '_' to " _ ",
            'x' to " x ")

    override fun width(): Int {
        return 3
    }

    override fun height(): Int {
        return 1
    }

    override fun supportsTile(tile: Tile<out Any>): Boolean {
        return tile.keyType() == Char::class.java && supportedTiles.containsKey(tile.type)
    }

    override fun fetchTextureForTile(tile: Tile<Char>): TileTexture<String> {
        return DefaultTileTexture(3, 1, supportedTiles[tile.type]!!)
    }
}
