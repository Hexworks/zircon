package org.hexworks.zircon.api.graphics.nerd

/**
 * Turns a Unicode code point into the string that renders it. Nerd Font glyphs above
 * U+FFFF need a surrogate pair, which a single [Char] cannot hold.
 */
public fun codePointToString(codePoint: Int): String {
    require(codePoint in 0..0x10FFFF) { "Invalid code point: $codePoint" }
    if (codePoint < 0x10000) return codePoint.toChar().toString()
    val offset = codePoint - 0x10000
    val high = (0xD800 + (offset shr 10)).toChar()
    val low = (0xDC00 + (offset and 0x3FF)).toChar()
    return "$high$low"
}
