package org.codetome.zircon.api.extension

import org.codetome.zircon.api.color.TextColor
import java.awt.Color

/**
 * Extension for easy conversion between [TextColor] and awt [Color].
 */
fun TextColor.toAWTColor(): java.awt.Color = Color(getRed(), getGreen(), getBlue(), getAlpha())
