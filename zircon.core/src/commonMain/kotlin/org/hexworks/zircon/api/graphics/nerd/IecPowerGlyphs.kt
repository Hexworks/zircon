package org.hexworks.zircon.api.graphics.nerd

enum class IecPowerGlyphs(
    val glyph: Char,
) {

    /**
     * ⏻ (0x23FB)
     */
    POWER(0x23FB.toChar()),

    /**
     * ⏼ (0x23FC)
     */
    TOGGLE_POWER(0x23FC.toChar()),

    /**
     * ⏽ (0x23FD)
     */
    POWER_ON(0x23FD.toChar()),

    /**
     * ⏾ (0x23FE)
     */
    SLEEP_MODE(0x23FE.toChar()),

    /**
     * ⭘ (0x2B58)
     */
    POWER_OFF(0x2B58.toChar());

}