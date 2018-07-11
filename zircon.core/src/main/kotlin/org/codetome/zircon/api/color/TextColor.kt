package org.codetome.zircon.api.color

import org.codetome.zircon.api.behavior.Cacheable
import org.codetome.zircon.internal.multiplatform.factory.TextColorFactory

interface TextColor : Cacheable {

    fun getAlpha(): Int

    fun getRed(): Int

    fun getGreen(): Int

    fun getBlue(): Int

    fun tint(): TextColor

    fun shade(): TextColor

    fun invert(): TextColor

    companion object {

        fun create(red: Int, green: Int, blue: Int, alpha: Int): TextColor = TextColorFactory.create(red, green, blue, alpha)
    }
}
