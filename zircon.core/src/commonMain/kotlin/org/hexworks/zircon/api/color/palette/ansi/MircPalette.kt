package org.hexworks.zircon.api.color.palette.ansi

import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.ANSIColor.*
import org.hexworks.zircon.api.color.palette.BasePalette
import org.hexworks.zircon.api.color.Color.Companion.create as color

object MircPalette : BasePalette<ANSIColor>(
    BLACK to color(0, 0, 0),
    RED to color(127, 0, 0),
    GREEN to color(0, 147, 0),
    YELLOW to color(252, 127, 0),
    BLUE to color(0, 0, 127),
    MAGENTA to color(156, 0, 156),
    CYAN to color(0, 147, 147),
    WHITE to color(210, 210, 210),
    GRAY to color(127, 127, 127),
    BRIGHT_RED to color(255, 0, 0),
    BRIGHT_GREEN to color(0, 252, 0),
    BRIGHT_YELLOW to color(255, 255, 0),
    BRIGHT_BLUE to color(0, 0, 252),
    BRIGHT_MAGENTA to color(255, 0, 255),
    BRIGHT_CYAN to color(0, 255, 255),
    BRIGHT_WHITE to color(255, 255, 255),
)