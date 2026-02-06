package org.hexworks.zircon.api.data.tile

import org.hexworks.zircon.api.behavior.Copiable
import org.hexworks.zircon.api.behavior.TilesetHolder
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.TileType
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TilesetResource

data class GraphicalTile(
    override val tileset: TilesetResource,
    override val modifiers: Set<Modifier>,
    val name: String,
    val tags: Set<String>,
) : Tile, TilesetHolder, Copiable<GraphicalTile> {

    override val tileType: TileType = TileType.GRAPHICAL_TILE

    override val cacheKey: String
        get() = "GraphicTile(n=$name,t=[${tags.asSequence().sorted().joinToString()}])"

    override fun createCopy(): GraphicalTile = copy()

    fun withName(name: String): GraphicalTile = if (this.name == name) this else copy(
        name = name
    )

    fun withTileset(tileset: TilesetResource): GraphicalTile = if (this.tileset == tileset) this else copy(
        tileset = tileset
    )

    fun withTags(tags: Set<String>): GraphicalTile = if (this.tags == tags) this else copy(tags = tags)

    override fun withModifiers(modifiers: Set<Modifier>): GraphicalTile =
        if (this.modifiers == modifiers) this else copy(
            modifiers = modifiers
        )

    override fun withModifiers(vararg modifiers: Modifier): GraphicalTile {
        return withModifiers(modifiers.toSet())
    }

}

