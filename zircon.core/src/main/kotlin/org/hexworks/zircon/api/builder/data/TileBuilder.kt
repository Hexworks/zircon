package org.hexworks.zircon.api.builder.data

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.GraphicTile
import org.hexworks.zircon.api.data.ImageTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.config.RuntimeConfig

/**
 * Builds [Tile]s.
 * Defaults:
 * - Default character is a space
 * - Default modifiers is an empty set
 * also
 * @see [org.hexworks.zircon.api.color.TileColor] to check default colors.
 */
@Suppress("UNCHECKED_CAST")
data class TileBuilder(
        private var character: Char = ' ',
        private var name: String = " ",
        private var tags: Set<String> = setOf(),
        private var styleSet: StyleSet = StyleSet.defaultStyle(),
        private var tileset: TilesetResource = RuntimeConfig.config.defaultTileset)
    : Builder<Tile> {

    fun character(character: Char) = also {
        this.character = character
    }

    fun name(name: String) = also {
        this.name = name
    }

    fun tags(tags: Set<String>) = also {
        this.tags = tags
    }

    /**
     * Sets the styles (colors and modifiers) from the given
     * `styleSet`.
     */
    fun styleSet(styleSet: StyleSet) = also {
        this.styleSet = styleSet
    }

    fun foregroundColor(foregroundColor: TileColor) = also {
        this.styleSet = styleSet.withForegroundColor(foregroundColor)
    }

    fun backgroundColor(backgroundColor: TileColor) = also {
        this.styleSet = styleSet.withBackgroundColor(backgroundColor)
    }

    fun modifiers(modifiers: Set<Modifier>) = also {
        this.styleSet = styleSet.withModifiers(modifiers)
    }

    fun tileset(tileset: TilesetResource) = also {
        this.tileset = tileset
    }

    fun modifiers(vararg modifiers: Modifier) = also {
        modifiers(modifiers.toSet())
    }

    override fun build(): Tile {
        return CharacterTile(
                character = character,
                style = styleSet)
    }

    fun buildCharacterTile(): CharacterTile {
        return CharacterTile(
                character = character,
                style = styleSet)
    }

    fun buildImageTile(): ImageTile {
        return ImageTile(
                tileset = tileset,
                name = name,
                tags = tags)
    }

    fun buildGraphicTile(): GraphicTile {
        return GraphicTile(
                name = name,
                tags = tags)
    }

    override fun createCopy() = copy()

    companion object {

        /**
         * Creates a new [TileBuilder] for creating [Tile]s.
         */
        fun newBuilder() = TileBuilder()

    }
}
