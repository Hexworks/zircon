package org.hexworks.zircon.api.graphics.nerd

public enum class PomiconsGlyphs(
    public val codePoint: Int,
) {


    /**
     *  (0xE000)
     */
    CLEAN_CODE(0xE000),

    /**
     *  (0xE001)
     */
    POMODORO_DONE(0xE001),

    /**
     *  (0xE002)
     */
    POMODORO_ESTIMATED(0xE002),

    /**
     *  (0xE003)
     */
    POMODORO_TICKING(0xE003),

    /**
     *  (0xE004)
     */
    POMODORO_SQUASHED(0xE004),

    /**
     *  (0xE005)
     */
    SHORT_PAUSE(0xE005),

    /**
     *  (0xE006)
     */
    LONG_PAUSE(0xE006),

    /**
     *  (0xE007)
     */
    AWAY(0xE007),

    /**
     *  (0xE008)
     */
    PAIR_PROGRAMMING(0xE008),

    /**
     *  (0xE009)
     */
    INTERNAL_INTERRUPTION(0xE009),

    /**
     *  (0xE00A)
     */
    EXTERNAL_INTERRUPTION(0xE00A);


    /** The glyph as a string (a surrogate pair above U+FFFF). */
    public val glyph: String get() = codePointToString(codePoint)
}
