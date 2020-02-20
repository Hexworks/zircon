package org.hexworks.zircon.internal.color

import org.hexworks.zircon.api.color.ColorInterpolator
import org.hexworks.zircon.api.color.TileColor

class DefaultColorInterpolator(override val lowColor: TileColor,
                               override val highColor: TileColor) : ColorInterpolator {

    override fun getColorAtRatio(ratio: Double): TileColor {
        require(ratio in 0.0..1.0) { "Ratio must be comprised between 0.0 and 1.0" }

        val red = lowColor.red + ((highColor.red - lowColor.red) * ratio).toInt()
        val green = lowColor.green + ((highColor.green - lowColor.green) * ratio).toInt()
        val blue = lowColor.blue + ((highColor.blue - lowColor.blue) * ratio).toInt()
        val alpha = lowColor.alpha + ((highColor.alpha - lowColor.alpha) * ratio).toInt()

        return TileColor.create(red, green, blue, alpha)
    }
}