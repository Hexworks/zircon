package org.codetome.zircon.api.font

import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.resource.CP437TilesetResource

/**
 * A font is an abstract representation of a resource which is capable of
 * representing fonts. This can be some physical font (like a .ttf file)
 * or a sprite sheet for example.
 * @param R the type of the object which represents a text character (like `BufferedImage` or `TextureRegion`)
 */
interface Font<out R> {

    /**
     * Returns the width of a character in pixels.
     */
    fun getWidth(): Int

    /**
     * Returns the height of a character in pixels.
     */
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

    /**
     * Returns the `Size` of this `Font` (width, height)
     */
    fun getSize(): Size = Size.of(getWidth(), getHeight())

    companion object {
        val DEFAULT_FONT = CP437TilesetResource.WANDERLUST_16X16.toFont()
    }
}