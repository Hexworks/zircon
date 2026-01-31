package org.hexworks.zircon.api.color.palette.ansi

import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.ANSIColor.*
import org.hexworks.zircon.api.color.palette.BasePalette
import org.hexworks.zircon.api.color.Color.Companion.create as color

object TerminalAppPalette : BasePalette<ANSIColor>(
    BLACK to color(0, 0, 0),
    RED to color(153, 0, 0),
    GREEN to color(0, 166, 0),
    YELLOW to color(153, 153, 0),
    BLUE to color(0, 0, 178),
    MAGENTA to color(178, 0, 178),
    CYAN to color(0, 166, 178),
    WHITE to color(191, 191, 191),
    GRAY to color(102, 102, 102),
    BRIGHT_RED to color(230, 0, 0),
    BRIGHT_GREEN to color(0, 217, 0),
    BRIGHT_YELLOW to color(230, 230, 0),
    BRIGHT_BLUE to color(0, 0, 255),
    BRIGHT_MAGENTA to color(230, 0, 230),
    BRIGHT_CYAN to color(0, 230, 230),
    BRIGHT_WHITE to color(230, 230, 230),
)