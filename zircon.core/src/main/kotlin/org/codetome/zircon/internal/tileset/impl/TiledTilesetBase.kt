package org.codetome.zircon.internal.tileset.impl

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.TileTextureMetadata
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.util.TextUtils
import org.codetome.zircon.internal.tileset.MetadataPickingStrategy
import org.codetome.zircon.api.util.Identifier

abstract class TiledTilesetBase(private val metadataTile: Map<Char, List<TileTextureMetadata>>,
                                private val metadataPickingStrategy: MetadataPickingStrategy = PickFirstMetaStrategy()) : Tileset {

    private val id = Identifier.randomIdentifier()

    override fun getId() = id

    override fun hasDataForChar(char: Char) = metadataTile.containsKey(char)

    override fun fetchMetadataForChar(char: Char): List<TileTextureMetadata> = metadataTile[char] ?: listOf()

    protected fun fetchMetaFor(tile: Tile): TileTextureMetadata {
        if (!hasDataForChar(tile.getCharacter()))
            if (TextUtils.isPrintableCharacter(tile.getCharacter()))
                throw IllegalArgumentException("No texture region exists for printable character: '${tile.getCharacter().toInt()}'!")
            else
                throw IllegalArgumentException("No texture region exists for non-printable character: '${tile.getCharacter().toInt()}'!")

        val tags = tile.getTags()
        val filtered = metadataTile[tile.getCharacter()]!!.filter { it.tags.containsAll(tags.toList()) }


        require(filtered.isNotEmpty()) {
            "Can't find tileset texture region for tag(s): ${tags.joinToString()}"
        }
        return metadataPickingStrategy.pickMetadata(filtered)
    }
}
