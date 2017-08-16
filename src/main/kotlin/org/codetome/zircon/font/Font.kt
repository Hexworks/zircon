package org.codetome.zircon.font

import org.codetome.zircon.TextCharacter

/**
 * A font is an abstract representation of a resource which is capable of
 * representing fonts. This can be some physical font (like a .ttf file)
 * or a sprite sheet.
 */
interface Font<out R> {

    fun getWidth(): Int

    fun getHeight(): Int

    /**
     * Tells whether this [Font] knows about the given [Char] or not.
     */
    fun hasDataForChar(char: Char): Boolean

    /**
     * Returns a region (graphical representation of a [TextCharacter]) for a character.
     * If `tags` are supplied, than this method will try to filter for them.
     * *Note that* this is only useful for graphical tilesets which have multiple
     * regions for a given [TextCharacter]!
     */
    fun fetchRegionForChar(textCharacter: TextCharacter, vararg tags: String): R

    /**
     * Returns all the [CharacterMetadata] for a [Char] which is known by this [Font].
     */
    fun fetchMetadataForChar(char: Char): List<CharacterMetadata>
}