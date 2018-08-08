package org.codetome.zircon.api.builder.data

import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.data.CharacterTile
import org.codetome.zircon.api.data.ImageTile
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.modifier.Modifier
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.internal.config.RuntimeConfig

/**
 * Builds [Tile]s.
 * Defaults:
 * - Default character is a space
 * - Default modifiers is an empty set
 * also
 * @see [org.codetome.zircon.api.color.TextColor] to check default colors.
 */
@Suppress("UNCHECKED_CAST")
data class TileBuilder(
        private var character: Char = ' ',
        private var name: String = " ",
        private var tags: Set<String> = setOf(),
        private var styleSet: StyleSet = StyleSet.defaultStyle(),
        private var tileset: TilesetResource<out Tile> = RuntimeConfig.config.defaultTileset)
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

    fun foregroundColor(foregroundColor: TextColor) = also {
        this.styleSet = styleSet.withForegroundColor(foregroundColor)
    }

    fun backgroundColor(backgroundColor: TextColor) = also {
        this.styleSet = styleSet.withBackgroundColor(backgroundColor)
    }

    fun modifiers(modifiers: Set<Modifier>) = also {
        this.styleSet = styleSet.withModifiers(modifiers)
    }

    fun tileset(tileset: TilesetResource<out Tile>) = also {
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
                tileset = tileset as? TilesetResource<ImageTile> ?: throw IllegalArgumentException("Wrong tileset."),
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
