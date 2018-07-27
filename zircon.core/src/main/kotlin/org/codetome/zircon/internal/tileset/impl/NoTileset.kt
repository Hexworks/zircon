package org.codetome.zircon.internal.tileset.impl

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.util.Identifier

object NoTileset : Tileset {

    private val id = Identifier.randomIdentifier()

    override fun getId() = id

    override fun getWidth() = signalNoOp()

    override fun getHeight() = signalNoOp()

    override fun hasDataForChar(char: Char) = signalNoOp()

    override fun fetchRegionForChar(tile: Tile) = signalNoOp()

    override fun fetchMetadataForChar(char: Char) = signalNoOp()

    private fun signalNoOp(): Nothing = TODO("No Tileset was supplied! Try setting a Tileset!")
}
