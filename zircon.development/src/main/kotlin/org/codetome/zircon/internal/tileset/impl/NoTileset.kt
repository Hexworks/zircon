package org.codetome.zircon.internal.tileset.impl

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.api.util.Identifier

object NoTileset : TilesetResource<Tile> {

    override val id = Identifier.randomIdentifier()
    override val tileType = signalNoOp()
    override val width = signalNoOp()
    override val height = signalNoOp()
    override val path = signalNoOp()

    private fun signalNoOp(): Nothing = TODO("No Tileset was supplied! Try setting a Tileset!")
}
