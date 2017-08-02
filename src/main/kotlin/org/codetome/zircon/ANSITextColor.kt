package org.codetome.zircon

enum class ANSITextColor(red: Int, green: Int, blue: Int) : TextColor {
    BLACK(0, 0, 0),
    RED(170, 0, 0),
    GREEN(0, 170, 0),
    YELLOW(170, 85, 0),
    BLUE(0, 0, 170),
    MAGENTA(170, 0, 170),
    CYAN(0, 170, 170),
    WHITE(170, 170, 170),
    DEFAULT(0, 0, 0);

    private val color: java.awt.Color = java.awt.Color(red, green, blue)

    override fun toColor() = color

    override fun getRed() = color.red

    override fun getGreen() = color.green

    override fun getBlue() = color.blue
}