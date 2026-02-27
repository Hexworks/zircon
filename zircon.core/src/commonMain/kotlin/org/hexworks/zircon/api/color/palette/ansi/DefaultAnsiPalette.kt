package org.hexworks.zircon.api.color.palette.ansi

import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.ANSIColor.*
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.color.palette.BasePalette

object DefaultAnsiPalette : BasePalette<ANSIColor>(
    BLACK to Color.create(0, 0, 0),
    RED to Color.create(196, 0, 0),
    GREEN to Color.create(0, 196, 0),
    YELLOW to Color.create(196, 126, 0),
    BLUE to Color.create(0, 0, 196),
    MAGENTA to Color.create(196, 0, 196),
    CYAN to Color.create(0, 196, 196),
    WHITE to Color.create(196, 196, 196),
    GRAY to Color.create(78, 78, 78),
    BRIGHT_RED to Color.create(220, 78, 78),
    BRIGHT_GREEN to Color.create(78, 220, 78),
    BRIGHT_YELLOW to Color.create(243, 243, 78),
    BRIGHT_BLUE to Color.create(78, 78, 220),
    BRIGHT_MAGENTA to Color.create(243, 78, 243),
    BRIGHT_CYAN to Color.create(78, 243, 243),
    BRIGHT_WHITE to Color.create(255, 255, 255),
)