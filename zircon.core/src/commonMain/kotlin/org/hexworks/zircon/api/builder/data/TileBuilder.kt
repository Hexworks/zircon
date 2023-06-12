package org.hexworks.zircon.api.builder.data

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.GraphicalTile
import org.hexworks.zircon.api.data.ImageTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.data.DefaultCharacterTile
import org.hexworks.zircon.internal.data.DefaultGraphicalTile
import org.hexworks.zircon.internal.data.DefaultImageTile

/**
 * Builds [Tile]s.
 * Defaults:
 * - Default character is a space
 * - Default modifiers is an empty set
 * also
 * @see [org.hexworks.zircon.api.color.TileColor] to check default colors.
 */
@Suppress("UNCHECKED_CAST")
class TileBuilder private constructor(
    private var character: Char = ' ',
    private var name: String = " ",
    private var tags: Set<String> = setOf(),
    private var styleSet: StyleSet = StyleSet.defaultStyle(),
    private var tileset: TilesetResource = RuntimeConfig.config.defaultTileset
) : Builder<Tile> {

    fun character() = character
    fun name() = name
    fun tags() = tags
    fun styleSet() = styleSet
    fun tileset() = tileset

    fun withCharacter(character: Char) = also {
        this.character = character
    }

    fun withName(name: String) = also {
        this.name = name
    }

    fun withTags(tags: Set<String>) = also {
        this.tags = tags
    }

    /**
     * Sets the styles (colors and modifiers) from the given
     * `styleSet`.
     */
    fun withStyleSet(styleSet: StyleSet) = also {
        this.styleSet = styleSet
    }

    fun withForegroundColor(foregroundColor: TileColor) = also {
        this.styleSet = styleSet.withForegroundColor(foregroundColor)
    }

    fun withBackgroundColor(backgroundColor: TileColor) = also {
        this.styleSet = styleSet.withBackgroundColor(backgroundColor)
    }

    fun withModifiers(modifiers: Set<Modifier>) = also {
        this.styleSet = styleSet.withModifiers(modifiers)
    }

    fun withTileset(tileset: TilesetResource) = also {
        this.tileset = tileset
    }

    fun withModifiers(vararg modifiers: Modifier) = also {
        withModifiers(modifiers.toSet())
    }

    override fun build(): Tile {
        return DefaultCharacterTile(
            character = character,
            styleSet = styleSet
        )
    }

    fun buildCharacterTile(): CharacterTile {
        return DefaultCharacterTile(
            character = character,
            styleSet = styleSet,
        )
    }

    fun buildImageTile(): ImageTile {
        return DefaultImageTile(
            tileset = tileset,
            name = name
        )
    }

    fun buildGraphicalTile(): GraphicalTile {
        return DefaultGraphicalTile(
            name = name,
            tags = tags,
            tileset = tileset
        )
    }

    override fun createCopy() = TileBuilder(
        character = character,
        name = name,
        tags = tags,
        styleSet = styleSet,
        tileset = tileset
    )

    companion object {

        /**
         * Creates a new [TileBuilder] for creating [Tile]s.
         */
        fun newBuilder() = TileBuilder()

    }
}
