package org.hexworks.zircon.api.color.palette.ansi

import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.ANSIColor.*
import org.hexworks.zircon.api.color.palette.BasePalette
import org.hexworks.zircon.api.color.Color.Companion.create as color

object UbuntuPalette : BasePalette<ANSIColor>(
    BLACK to color(0, 0, 0),
    RED to color(222, 56, 43),
    GREEN to color(57, 181, 74),
    YELLOW to color(255, 199, 6),
    BLUE to color(0, 111, 184),
    MAGENTA to color(118, 38, 113),
    CYAN to color(44, 181, 233),
    WHITE to color(204, 204, 204),
    GRAY to color(128, 128, 128),
    BRIGHT_RED to color(255, 0, 0),
    BRIGHT_GREEN to color(0, 255, 0),
    BRIGHT_YELLOW to color(255, 255, 0),
    BRIGHT_BLUE to color(0, 0, 255),
    BRIGHT_MAGENTA to color(255, 0, 255),
    BRIGHT_CYAN to color(0, 255, 255),
    BRIGHT_WHITE to color(255, 255, 255),
)