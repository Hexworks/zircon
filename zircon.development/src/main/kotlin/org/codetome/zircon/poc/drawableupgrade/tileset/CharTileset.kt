package org.codetome.zircon.poc.drawableupgrade.tileset

import org.codetome.zircon.poc.drawableupgrade.texture.DefaultTileTexture
import org.codetome.zircon.poc.drawableupgrade.texture.TileTexture
import org.codetome.zircon.poc.drawableupgrade.tile.Tile

object CharTileset : Tileset<Char, Char> {

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

    override fun supportsTile(tile: Tile<out Any>): Boolean {
        return tile.keyType() == Char::class.java && supportedTiles.containsKey(tile.key)
    }

    override fun fetchTextureForTile(tile: Tile<Char>): TileTexture<Char> {
        return DefaultTileTexture(1, 1, tile.key)
    }
}
