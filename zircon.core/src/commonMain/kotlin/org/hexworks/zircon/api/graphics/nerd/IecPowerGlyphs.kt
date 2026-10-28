package org.hexworks.zircon.api.graphics.nerd

public enum class IecPowerGlyphs(
    public val codePoint: Int,
) {


    /**
     * ⏻ (0x23FB)
     */
    POWER(0x23FB),

    /**
     * ⏼ (0x23FC)
     */
    TOGGLE_POWER(0x23FC),

    /**
     * ⏽ (0x23FD)
     */
    POWER_ON(0x23FD),

    /**
     * ⏾ (0x23FE)
     */
    SLEEP_MODE(0x23FE),

    /**
     * ⭘ (0x2B58)
     */
    POWER_OFF(0x2B58);


    /** The glyph as a string (a surrogate pair above U+FFFF). */
    public val glyph: String get() = codePointToString(codePoint)
}
