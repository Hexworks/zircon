package org.hexworks.zircon.api.graphics.nerd

public enum class IndentationGlyphs(
    public val codePoint: Int,
) {


    /**
     *  (0xE621)
     */
    LINE(0xE621);


    /** The glyph as a string (a surrogate pair above U+FFFF). */
    public val glyph: String get() = codePointToString(codePoint)
}
