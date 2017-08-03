package org.codetome.zircon

import org.codetome.zircon.builder.TextColorFactory.DEFAULT_ALPHA

enum class ANSITextColor(red: Int, green: Int, blue: Int, alpha: Int = 255) : TextColor {
    BLACK(0, 0, 0, DEFAULT_ALPHA),
    RED(170, 0, 0, DEFAULT_ALPHA),
    GREEN(0, 170, 0, DEFAULT_ALPHA),
    YELLOW(170, 85, 0, DEFAULT_ALPHA),
    BLUE(0, 0, 170, DEFAULT_ALPHA),
    MAGENTA(170, 0, 170, DEFAULT_ALPHA),
    CYAN(0, 170, 170, DEFAULT_ALPHA),
    WHITE(170, 170, 170, DEFAULT_ALPHA),
    DEFAULT(0, 0, 0, DEFAULT_ALPHA);

    private val color: java.awt.Color = java.awt.Color(red, green, blue)

    override fun toAWTColor() = color

    override fun getRed() = color.red

    override fun getGreen() = color.green

    override fun getBlue() = color.blue

    override fun getAlpha() = color.alpha
}