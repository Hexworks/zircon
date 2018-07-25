package org.codetome.zircon.api.font

import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.internal.behavior.Identifiable

/**
 * A font is an abstract representation of a resource which is capable of
 * representing fonts. This can be some physical font (like a .ttf file)
 * or a sprite sheet for example.
 */
interface Font : Identifiable {

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
     * If `tags` are supplied in `textCharacter`, than this method will try to filter for them.
     * *Note that* this is only useful for graphical tilesets which have multiple
     * regions for a given [TextCharacter]!
     */
    fun fetchRegionForChar(textCharacter: TextCharacter): FontTextureRegion<*>

    /**
     * Returns all the [TextureRegionMetadata] for a [Char] which is known by this [Font].
     */
    fun fetchMetadataForChar(char: Char): List<TextureRegionMetadata>

    /**
     * Returns the `Size` of this `Font` (width, height)
     */
    fun getSize(): Size = Size.create(getWidth(), getHeight())
}
