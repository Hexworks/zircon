package org.hexworks.zircon.api.graphics.nerd

public enum class PowerlineGlyphs(
    public val codePoint: Int,
) {


    /**
     *  (0xE0A0)
     */
    BRANCH(0xE0A0),

    /**
     *  (0xE0A1)
     */
    CURRENT_LINE(0xE0A1),

    /**
     *  (0xE0A1)
     */
    LINE_NUMBER(0xE0A1),

    /**
     *  (0xE0A2)
     */
    HOSTNAME(0xE0A2),

    /**
     *  (0xE0A2)
     */
    READONLY(0xE0A2),

    /**
     *  (0xE0B0)
     */
    LEFT_HARD_DIVIDER(0xE0B0),

    /**
     *  (0xE0B1)
     */
    LEFT_SOFT_DIVIDER(0xE0B1),

    /**
     *  (0xE0B2)
     */
    RIGHT_HARD_DIVIDER(0xE0B2),

    /**
     *  (0xE0B3)
     */
    RIGHT_SOFT_DIVIDER(0xE0B3);


    /** The glyph as a string (a surrogate pair above U+FFFF). */
    public val glyph: String get() = codePointToString(codePoint)
}
