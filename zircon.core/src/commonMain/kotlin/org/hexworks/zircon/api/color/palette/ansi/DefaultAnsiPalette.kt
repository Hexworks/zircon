package org.hexworks.zircon.api.color.palette.ansi

import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.color.palette.BasePalette

object DefaultAnsiPalette : BasePalette<ANSIColor>(
    ANSIColor.BLACK to Color.Companion.create(0, 0, 0),
    ANSIColor.RED to Color.Companion.create(196, 0, 0),
    ANSIColor.GREEN to Color.Companion.create(0, 196, 0),
    ANSIColor.YELLOW to Color.Companion.create(196, 126, 0),
    ANSIColor.BLUE to Color.Companion.create(0, 0, 196),
    ANSIColor.MAGENTA to Color.Companion.create(196, 0, 196),
    ANSIColor.CYAN to Color.Companion.create(0, 196, 196),
    ANSIColor.WHITE to Color.Companion.create(196, 196, 196),
    ANSIColor.GRAY to Color.Companion.create(78, 78, 78),
    ANSIColor.BRIGHT_RED to Color.Companion.create(220, 78, 78),
    ANSIColor.BRIGHT_GREEN to Color.Companion.create(78, 220, 78),
    ANSIColor.BRIGHT_YELLOW to Color.Companion.create(243, 243, 78),
    ANSIColor.BRIGHT_BLUE to Color.Companion.create(78, 78, 220),
    ANSIColor.BRIGHT_MAGENTA to Color.Companion.create(243, 78, 243),
    ANSIColor.BRIGHT_CYAN to Color.Companion.create(78, 243, 243),
    ANSIColor.BRIGHT_WHITE to Color.Companion.create(255, 255, 255),
)