package org.codetome.zircon.api.builder.data

import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.modifier.Modifier
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.builder.graphics.StyleSetBuilder
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.graphics.StyleSet

/**
 * Builds [Tile]s.
 * Defaults:
 * - Default character is a space
 * - Default modifiers is an empty set
 * also
 * @see [org.codetome.zircon.api.color.TextColor] to check default colors.
 */
data class TileBuilder(
        private var character: Char = ' ',
        private var styleSet: StyleSet = StyleSet.defaultStyle(),
        private var tags: Set<String> = setOf()) : Builder<Tile> {

    fun character(character: Char) = also {
        this.character = character
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

    fun modifiers(vararg modifiers: Modifier): TileBuilder = modifiers(modifiers.toSet())

    fun tag(vararg tags: String) = also {
        this.tags = tags.toSet()
    }

    fun tags(tags: Set<String>) = also {
        this.tags = tags
    }

    override fun build(): Tile = Tile.create(character, styleSet, tags)

    override fun createCopy() = copy()

    companion object {

        /**
         * Creates a new [TileBuilder] for creating [Tile]s.
         */
        fun newBuilder() = TileBuilder()

    }
}
