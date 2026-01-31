package org.hexworks.zircon.api.color.palette.ansi

import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.ANSIColor.*
import org.hexworks.zircon.api.color.palette.BasePalette
import org.hexworks.zircon.api.color.Color.Companion.create as color

object VisualStudioCodePalette : BasePalette<ANSIColor>(
    BLACK to color(0, 0, 0),
    RED to color(205, 49, 49),
    GREEN to color(13, 188, 121),
    YELLOW to color(229, 229, 16),
    BLUE to color(36, 114, 200),
    MAGENTA to color(188, 63, 188),
    CYAN to color(17, 168, 205),
    WHITE to color(229, 229, 229),
    GRAY to color(102, 102, 102),
    BRIGHT_RED to color(241, 76, 76),
    BRIGHT_GREEN to color(35, 209, 139),
    BRIGHT_YELLOW to color(245, 245, 67),
    BRIGHT_BLUE to color(59, 142, 234),
    BRIGHT_MAGENTA to color(214, 112, 214),
    BRIGHT_CYAN to color(41, 184, 219),
    BRIGHT_WHITE to color(229, 229, 229),
)