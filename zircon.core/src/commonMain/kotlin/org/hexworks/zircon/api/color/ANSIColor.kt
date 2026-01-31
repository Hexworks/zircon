package org.hexworks.zircon.api.color

/**
 * Default ANSI color names and their respective fg/bg codes.
 * See https://en.wikipedia.org/wiki/ANSI_escape_code#Colors
 * for more info.
 */
enum class ANSIColor(
    val fgCode: Int,
    val bgCode: Int
) {
    BLACK(30, 40),
    RED(31, 41),
    GREEN(32, 42),
    YELLOW(33, 43),
    BLUE(34, 44),
    MAGENTA(35, 45),
    CYAN(36, 46),
    WHITE(37, 47),
    /**
     * AKA: Bright Black
     */
    GRAY(90, 100),
    BRIGHT_RED(91, 101),
    BRIGHT_GREEN(92, 102),
    BRIGHT_YELLOW(93, 103),
    BRIGHT_BLUE(94, 104),
    BRIGHT_MAGENTA(95, 105),
    BRIGHT_CYAN(96, 106),
    BRIGHT_WHITE(97, 107);
}
