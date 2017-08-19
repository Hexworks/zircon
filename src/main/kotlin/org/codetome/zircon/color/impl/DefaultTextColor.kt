package org.codetome.zircon.color.impl

import org.codetome.zircon.api.TextColorFactory
import org.codetome.zircon.color.TextColor
import java.awt.Color

data class DefaultTextColor(private val red: Int,
                            private val green: Int,
                            private val blue: Int,
                            private val alpha: Int = TextColorFactory.DEFAULT_ALPHA) : TextColor {

    private val color: Color = Color(red, green, blue, alpha)

    override fun toAWTColor() = color

    override fun getRed() = red

    override fun getGreen() = green

    override fun getBlue() = blue

    override fun getAlpha() = alpha

}