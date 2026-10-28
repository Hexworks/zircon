package org.hexworks.zircon.api.graphics.nerd

public enum class ExtraGlyphs(
    public val codePoint: Int,
) {


    /**
     *  (0xEE00)
     */
    PROGRESS_EMPTY_LEFT(0xEE00),

    /**
     *  (0xEE01)
     */
    PROGRESS_EMPTY_MID(0xEE01),

    /**
     *  (0xEE02)
     */
    PROGRESS_EMPTY_RIGHT(0xEE02),

    /**
     *  (0xEE03)
     */
    PROGRESS_FULL_LEFT(0xEE03),

    /**
     *  (0xEE04)
     */
    PROGRESS_FULL_MID(0xEE04),

    /**
     *  (0xEE05)
     */
    PROGRESS_FULL_RIGHT(0xEE05),

    /**
     *  (0xEE06)
     */
    PROGRESS_SPINNER_1(0xEE06),

    /**
     *  (0xEE07)
     */
    PROGRESS_SPINNER_2(0xEE07),

    /**
     *  (0xEE08)
     */
    PROGRESS_SPINNER_3(0xEE08),

    /**
     *  (0xEE09)
     */
    PROGRESS_SPINNER_4(0xEE09),

    /**
     *  (0xEE0A)
     */
    PROGRESS_SPINNER_5(0xEE0A),

    /**
     *  (0xEE0B)
     */
    PROGRESS_SPINNER_6(0xEE0B);


    /** The glyph as a string (a surrogate pair above U+FFFF). */
    public val glyph: String get() = codePointToString(codePoint)
}
