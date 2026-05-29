package org.hexworks.zircon.api.color.palette.ansi

import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.ANSIColor.*
import org.hexworks.zircon.api.color.palette.BasePalette
import org.hexworks.zircon.api.color.Color.Companion.create as color

object WindowsConsolePalette : BasePalette<ANSIColor>(
    BLACK to color(0, 0, 0),
    RED to color(196, 0, 0),
    GREEN to color(0, 128, 0),
    YELLOW to color(128, 128, 0),
    BLUE  to color(0, 0, 128),
    MAGENTA to color(128, 0, 128),
    CYAN to color(0, 128, 128),
    WHITE to color(192, 192, 192),
    GRAY to color(128, 128, 128),
    BRIGHT_RED to color(255, 0, 0),
    BRIGHT_GREEN to color(0, 255, 0),
    BRIGHT_YELLOW to color(255, 255, 0),
    BRIGHT_BLUE to color(0, 0, 255),
    BRIGHT_MAGENTA to color(255, 0, 255),
    BRIGHT_CYAN to color(0, 255, 255),
    BRIGHT_WHITE to color(255, 255, 255),
)