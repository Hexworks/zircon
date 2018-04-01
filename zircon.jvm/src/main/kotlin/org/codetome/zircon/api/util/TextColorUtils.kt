@file:JvmName("TextColorUtils")
package org.codetome.zircon.api.util

import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.color.TextColorFactory


fun darkenColorByPercent(color: TextColor, percentage: Double): TextColor {
    return TextColorFactory.fromRGB(
            red = (color.getRed() * (1f - percentage)).toInt(),
            green = (color.getGreen() * (1f - percentage)).toInt(),
            blue = (color.getBlue() * (1f - percentage)).toInt())
}
