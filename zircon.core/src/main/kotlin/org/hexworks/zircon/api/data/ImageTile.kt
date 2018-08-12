package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.behavior.impl.DefaultTilesetOverride

data class ImageTile(
        val tileset: TilesetResource,
        val name: String,
        private val style: StyleSet = StyleSet.defaultStyle(),
        private val tilesetOverride: TilesetOverride = DefaultTilesetOverride(tileset))
    : Tile,
        TilesetOverride by tilesetOverride {

    private val cacheKey = "ImageTile(t=${tileset.path},n=$name)"

    override fun tileType() = ImageTile::class.simpleName!!

    override fun generateCacheKey() = cacheKey

    override fun styleSet() = style

    fun withName(name: String) = ImageTile(
            name = name,
            tileset = tileset,
            style = style)

    override fun withForegroundColor(foregroundColor: TileColor) = this

    override fun withBackgroundColor(backgroundColor: TileColor) = this

    override fun withStyle(style: StyleSet) = this

    override fun withModifiers(modifiers: Set<Modifier>) = this

    override fun withoutModifiers(modifiers: Set<Modifier>) = this

}
