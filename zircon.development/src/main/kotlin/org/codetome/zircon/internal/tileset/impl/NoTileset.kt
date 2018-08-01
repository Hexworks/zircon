package org.codetome.zircon.internal.tileset.impl

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.util.Identifier

object NoTileset : Tileset<Char, Char> {

    override fun width() = signalNoOp()

    override fun height() = signalNoOp()

    override fun supportsTile(tile: Tile<out Any>) = signalNoOp()

    override fun fetchTextureForTile(tile: Tile<Char>) = signalNoOp()

    private fun signalNoOp(): Nothing = TODO("No Tileset was supplied! Try setting a Tileset!")
}
