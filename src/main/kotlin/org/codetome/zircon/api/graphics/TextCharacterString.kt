package org.codetome.zircon.api.graphics

import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.behavior.Drawable

/**
 * [TextCharacterString] is an aggregation of [TextCharacter]s. You can draw a [TextCharacterString] onto any
 * [org.codetome.zircon.api.behavior.DrawSurface] and you can expect it to behave in a way like handwriting would (if a string does not fit in a
 * line it continues in a new line).
 *
 * Text wrapping is managed by [TextWrap] which is an enum with `NO_WRAPPING` and `WRAP` options.
 *
 * If a [TextCharacterString] is too long to fit on a `DrawSurface` the parts which would overflow are truncated instead.
 *
 * If there is no wrapping and the text reaches the end of the line it will also be truncated.
 *
 * [TextCharacterString] comes with its own builder and you can create them in a simple way from plain Java [String]s.
 */
interface TextCharacterString : Drawable {

    fun getTextCharacters(): List<TextCharacter>
}