package org.codetome.zircon.internal.color

import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.internal.multiplatform.factory.TextColorFactory
import java.awt.Color

data class DefaultTextColor(private val red: Int,
                            private val green: Int,
                            private val blue: Int,
                            private val alpha: Int = TextColorFactory.defaultAlpha()) : TextColor {

    private val cacheKey = "$red$green$blue$alpha"

    override fun generateCacheKey() = cacheKey

    private val color: Color = Color(red, green, blue, alpha)

    override fun getRed() = red

    override fun getGreen() = green

    override fun getBlue() = blue

    override fun getAlpha() = alpha

    override fun tint(): TextColor {
        val c = color.brighter()
        return DefaultTextColor(c.red, c.green, c.blue, c.alpha)
    }

    override fun shade(): TextColor {
        val c = color.darker()
        return DefaultTextColor(c.red, c.green, c.blue, c.alpha)
    }

    override fun invert(): TextColor {
        return DefaultTextColor(255 - color.red, 255 - color.green, 255 - color.blue, color.alpha)
    }
}
