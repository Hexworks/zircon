package org.hexworks.zircon.api.graphics.nerd

public enum class PowerlineExtraGlyphs(
    public val codePoint: Int,
) {


    /**
     *  (0xE0A3)
     */
    COLUMN_NUMBER(0xE0A3),

    /**
     *  (0xE0A3)
     */
    CURRENT_COLUMN(0xE0A3),

    /**
     *  (0xE0B4)
     */
    RIGHT_HALF_CIRCLE_THICK(0xE0B4),

    /**
     *  (0xE0B5)
     */
    RIGHT_HALF_CIRCLE_THIN(0xE0B5),

    /**
     *  (0xE0B6)
     */
    LEFT_HALF_CIRCLE_THICK(0xE0B6),

    /**
     *  (0xE0B7)
     */
    LEFT_HALF_CIRCLE_THIN(0xE0B7),

    /**
     *  (0xE0B8)
     */
    LOWER_LEFT_TRIANGLE(0xE0B8),

    /**
     *  (0xE0B9)
     */
    BACKSLASH_SEPARATOR(0xE0B9),

    /**
     *  (0xE0BA)
     */
    LOWER_RIGHT_TRIANGLE(0xE0BA),

    /**
     *  (0xE0BB)
     */
    FORWARDSLASH_SEPARATOR(0xE0BB),

    /**
     *  (0xE0BC)
     */
    UPPER_LEFT_TRIANGLE(0xE0BC),

    /**
     *  (0xE0BD)
     */
    FORWARDSLASH_SEPARATOR_REDUNDANT(0xE0BD),

    /**
     *  (0xE0BE)
     */
    UPPER_RIGHT_TRIANGLE(0xE0BE),

    /**
     *  (0xE0BF)
     */
    BACKSLASH_SEPARATOR_REDUNDANT(0xE0BF),

    /**
     *  (0xE0C0)
     */
    FLAME_THICK(0xE0C0),

    /**
     *  (0xE0C1)
     */
    FLAME_THIN(0xE0C1),

    /**
     *  (0xE0C2)
     */
    FLAME_THICK_MIRRORED(0xE0C2),

    /**
     *  (0xE0C3)
     */
    FLAME_THIN_MIRRORED(0xE0C3),

    /**
     *  (0xE0C4)
     */
    PIXELATED_SQUARES_SMALL(0xE0C4),

    /**
     *  (0xE0C5)
     */
    PIXELATED_SQUARES_SMALL_MIRRORED(0xE0C5),

    /**
     *  (0xE0C6)
     */
    PIXELATED_SQUARES_BIG(0xE0C6),

    /**
     *  (0xE0C7)
     */
    PIXELATED_SQUARES_BIG_MIRRORED(0xE0C7),

    /**
     *  (0xE0C8)
     */
    ICE_WAVEFORM(0xE0C8),

    /**
     *  (0xE0CA)
     */
    ICE_WAVEFORM_MIRRORED(0xE0CA),

    /**
     *  (0xE0CC)
     */
    HONEYCOMB(0xE0CC),

    /**
     *  (0xE0CD)
     */
    HONEYCOMB_OUTLINE(0xE0CD),

    /**
     *  (0xE0CE)
     */
    LEGO_SEPARATOR(0xE0CE),

    /**
     *  (0xE0CF)
     */
    LEGO_SEPARATOR_THIN(0xE0CF),

    /**
     *  (0xE0D0)
     */
    LEGO_BLOCK_FACING(0xE0D0),

    /**
     *  (0xE0D1)
     */
    LEGO_BLOCK_SIDEWAYS(0xE0D1),

    /**
     *  (0xE0D2)
     */
    TRAPEZOID_TOP_BOTTOM(0xE0D2),

    /**
     *  (0xE0D4)
     */
    TRAPEZOID_TOP_BOTTOM_MIRRORED(0xE0D4),

    /**
     *  (0xE0D6)
     */
    RIGHT_HARD_DIVIDER_INVERSE(0xE0D6),

    /**
     *  (0xE0D7)
     */
    LEFT_HARD_DIVIDER_INVERSE(0xE0D7);


    /** The glyph as a string (a surrogate pair above U+FFFF). */
    public val glyph: String get() = codePointToString(codePoint)
}
