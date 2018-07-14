package org.codetome.zircon.api.color

import org.codetome.zircon.api.behavior.Cacheable
import org.codetome.zircon.platform.factory.TextColorFactory

interface TextColor : Cacheable {

    fun getAlpha(): Int

    fun getRed(): Int

    fun getGreen(): Int

    fun getBlue(): Int

    fun tint(): TextColor

    fun shade(): TextColor

    fun invert(): TextColor

    fun darkenByPercent(percentage: Double): TextColor

    companion object {

        fun create(red: Int, green: Int, blue: Int, alpha: Int = 255): TextColor = TextColorFactory.create(red, green, blue, alpha)
    }
}
