package org.codetome.zircon.api.graphics

import org.codetome.zircon.api.behavior.Drawable
import org.codetome.zircon.api.data.CharacterTile

/**
 * A [TextCharacterString] is an aggregation of [CharacterTile]s. You can draw a
 * [TextCharacterString] onto a [org.codetome.zircon.api.behavior.DrawSurface] and you
 * can expect it to behave in a way like handwriting would (if a string does not fit in a
 * line it continues in a new line).
 *
 * Text wrapping is managed by [TextWrap] which is an enum with `NO_WRAPPING` and `WRAP`
 * options.
 *
 * If a [TextCharacterString] is too long to fit on a `DrawSurface` the parts which would
 * overflow are truncated instead.
 *
 * If there is no wrapping and the text reaches the end of the line it will also be truncated.
 *
 * [TextCharacterString] comes with its own builder and you can create them in a simple
 * way from plain Java [String]s.
 */
interface TextCharacterString<S: Any> : Drawable<Char>, Collection<CharacterTile> {

    fun getTextCharacters(): List<CharacterTile>

    fun toTextImage(): TileImage<Char, S>

    /**
     * Creates a new [TextCharacterString] which contains the contents of `this` string
     * and the `other` string. The original strings are left untouched. So if you `plus`
     * `[x, y]` to `[a, b]`, you'll get `[a, b, x, y]`
     * **Note that** the [TextWrap] form the original (`this`) string will be used in the
     * resulting string!
     */
    operator fun plus(other: TextCharacterString<S>): TextCharacterString<S>
}
