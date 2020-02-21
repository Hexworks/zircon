package org.hexworks.zircon.api.color

interface ColorInterpolator {

    val lowColor: TileColor

    val highColor: TileColor

    /**
     * Returns a new [TileColor] which is the interpolation between the two colors of the interpolator at the given ratio.
     */
    fun getColorAtRatio(ratio: Double): TileColor
}