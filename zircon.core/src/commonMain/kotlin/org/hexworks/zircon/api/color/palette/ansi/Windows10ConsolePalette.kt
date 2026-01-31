package org.hexworks.zircon.api.color.palette.ansi

import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.ANSIColor.*
import org.hexworks.zircon.api.color.palette.BasePalette
import org.hexworks.zircon.api.color.Color.Companion.create as color

object Windows10ConsolePalette : BasePalette<ANSIColor>(
    BLACK to color(12, 12, 12),
    RED to color(197, 15, 31),
    GREEN to color(19, 161, 14),
    YELLOW to color(193, 156, 0),
    BLUE to color(0, 55, 218),
    MAGENTA to color(136, 23, 152),
    CYAN to color(58, 150, 221),
    WHITE to color(204, 204, 204),
    GRAY to color(118, 118, 118),
    BRIGHT_RED to color(231, 72, 86),
    BRIGHT_GREEN to color(22, 198, 12),
    BRIGHT_YELLOW to color(249, 241, 165),
    BRIGHT_BLUE to color(59, 120, 255),
    BRIGHT_MAGENTA to color(180, 0, 158),
    BRIGHT_CYAN to color(97, 214, 214),
    BRIGHT_WHITE to color(242, 242, 242),
)