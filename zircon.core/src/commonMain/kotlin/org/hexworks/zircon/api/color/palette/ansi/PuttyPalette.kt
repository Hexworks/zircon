package org.hexworks.zircon.api.color.palette.ansi

import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.ANSIColor.*
import org.hexworks.zircon.api.color.palette.BasePalette
import org.hexworks.zircon.api.color.Color.Companion.create as color

object PuttyPalette : BasePalette<ANSIColor>(
    BLACK to color(0, 0, 0),
    RED to color(187, 0, 0),
    GREEN to color(0, 187, 0),
    YELLOW to color(187, 187, 0),
    BLUE to color(0, 0, 187),
    MAGENTA to color(187, 0, 187),
    CYAN to color(0, 187, 187),
    WHITE to color(187, 187, 187),
    GRAY to color(85, 85, 85),
    BRIGHT_RED to color(255, 85, 85),
    BRIGHT_GREEN to color(85, 255, 85),
    BRIGHT_YELLOW to color(255, 255, 85),
    BRIGHT_BLUE to color(85, 85, 255),
    BRIGHT_MAGENTA to color(255, 85, 255),
    BRIGHT_CYAN to color(85, 255, 255),
    BRIGHT_WHITE to color(255, 255, 255),
)