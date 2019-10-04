package org.hexworks.zircon.api.extensions

import org.hexworks.zircon.api.color.TileColor
import java.awt.Color

/**
 * Extension for easy conversion between [TileColor] and awt [Color].
 */
fun TileColor.toAWTColor(): java.awt.Color = Color(red, green, blue, alpha)
