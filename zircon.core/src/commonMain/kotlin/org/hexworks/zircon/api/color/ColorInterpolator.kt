package org.hexworks.zircon.api.color

/**
 * A [ColorInterpolator] represents a color space between 2 [TileColor]s.
 * [lowColor] sits at the lowest end of the color space, and will be the result of a call to [getColorAtRatio] with a ratio of 0.0.
 * [highColor] is conversely the highest value, and a ratio of 1.0 will return it.
 * Calling [getColorAtRatio] with any other ratio (between 0.0 and 1.0) will return a new [TileColor] that is the linear interpolation between
 * [lowColor] and [highColor] on their R, G, B and A values.
 *
 * A [ColorInterpolator] is created with the function [TileColor.interpolateTo]
 */
interface ColorInterpolator {

    /**
     * Represents the lowest value in the color space. A ratio of 0.0 will return this color.
     */
    val lowColor: TileColor

    /**
     * Represents the highest value in the color space. A ratio of 1.0 will return this color.
     */
    val highColor: TileColor

    /**
     * Returns a new [TileColor] which is the interpolation between the two colors of the interpolator at the given ratio (between 0.0 and 1.0).
     */
    fun getColorAtRatio(ratio: Double): TileColor
}
