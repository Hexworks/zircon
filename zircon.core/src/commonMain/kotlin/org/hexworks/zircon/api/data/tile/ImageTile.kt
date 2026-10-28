package org.hexworks.zircon.api.data.tile

import org.hexworks.zircon.api.behavior.Copiable
import org.hexworks.zircon.api.behavior.TilesetHolder
import org.hexworks.zircon.api.builder.data.imageTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.TileType
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TilesetResource

public data class ImageTile(
    val name: String,
    override val tileset: TilesetResource,
    override val modifiers: Set<Modifier> = emptySet()
) : Tile, TilesetHolder, Copiable<ImageTile> {

    override val tileType: TileType = TileType.IMAGE_TILE
    override val cacheKey: String
        get() = "ImageTile(t=${tileset.path},n=$name)"

    override fun createCopy(): ImageTile = copy()

    public fun withName(name: String): ImageTile = imageTile {
        this.name = name
        tileset = this@ImageTile.tileset
    }

    public fun withTileset(tileset: TilesetResource): ImageTile = imageTile {
        name = this@ImageTile.name
        this.tileset = tileset
    }
    
    override fun withModifiers(vararg modifiers: Modifier): ImageTile =
        withModifiers(modifiers.toSet())

    override fun withModifiers(modifiers: Set<Modifier>): ImageTile {
        return if (this.modifiers == modifiers) {
            this
        } else {
            copy(modifiers = modifiers)
        }
    }

}

